package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import util.Permissions;

import java.util.ArrayList;
import java.util.Arrays;

public class Rename implements ICommand {
    CommandReceivedEvent e;
    Member memberGiven;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("rename"));
    String category = "util";
    String exampleCommand = "rename <@user>/<userID> <nickname>";
    String shortCommandDescription = "Rename user to the nickname given";
    String fullCommandDescription = "Rename users to the nickname given, requires manage usernames permission.\n" +
            "Username should be below or the same as 32 chars.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        long idTijs = 257500867568205824L;

        if (!e.hasArgs() || !(e.getArgs().length > 2)) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: requires at least 2 arguments.", e.getPrefix())).queue();
            return;
        }

        String[] args = e.getArgs();

        if (e.getMessage().getMentionedMembers().size() > 0) {
            memberGiven = e.getMessage().getMentionedMembers().get(0);
        } else if (e.getGuild().getMemberById(args[1]) != null) {
            memberGiven = e.getGuild().getMemberById(args[1]);
        } else {
            e.getMessageChannel().sendMessage(getFullHelp("Error: ID given isn't valid.", e.getPrefix())).queue();
            return;
        }

        if (e.getMember().getId().equals(memberGiven.getId())) {
            runRenameCommand(args);
        } else if (e.getMember().hasPermission(Permission.NICKNAME_MANAGE) || e.getMember().getIdLong() == idTijs) {
            runRenameCommand(args);
        } else {
            e.getMessageChannel().sendMessage(getFullHelp("Error: Manage nicknames permission required to run this command..", e.getPrefix())).queue();
        }
    }

    public void runRenameCommand(String[] args) {
        Permissions permissions = new Permissions(e.getGuild());

        if (permissions.botHasPermission(Permission.NICKNAME_MANAGE) && permissions.botCanInteract(memberGiven)) {
            String nickname = arrayToString(args);

            if (!(nickname.length() > 32)) {
                memberGiven.modifyNickname(nickname).complete();
            } else {
                e.getMessageChannel().sendMessage(getFullHelp("Error: nickname must be below 32 chars!", e.getPrefix())).queue();
            }
        } else if (!permissions.botHasPermission(Permission.NICKNAME_MANAGE) && !permissions.botCanInteract(memberGiven)) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: member is a higher role as the bot, and bot does not have manage nickname permission", e.getPrefix())).queue();
        } else if (!permissions.botHasPermission(Permission.NICKNAME_MANAGE)) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: bot does not have manage nickname permission.", e.getPrefix())).queue();
        } else if (!permissions.botCanInteract(memberGiven)) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: member is a higher role or the same as the bot.", e.getPrefix())).queue();
        }
    }

    public String arrayToString(String[] args) {
        StringBuilder output = new StringBuilder();
        int run = 0;

        for (String arg : args) {
            if (!(run < 2)) {
                if (output.toString().equals("")) {
                    output = new StringBuilder(arg);
                } else {
                    output.append(" ").append(arg);
                }
            } else {
                run++;
            }
        }
        return output.toString();
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getExampleCommand() {
        return exampleCommand;
    }

    @Override
    public String getShortCommandDescription() {
        return shortCommandDescription;
    }

    @Override
    public String getFullCommandDescription() {
        return fullCommandDescription;
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return commandAliases;
    }
}
