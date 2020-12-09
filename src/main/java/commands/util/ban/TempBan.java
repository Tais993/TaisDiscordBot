package commands.util.ban;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import utilities.Permissions;

import java.util.ArrayList;
import java.util.Arrays;

public class TempBan implements ICommand {
    CommandReceivedEvent e;

    Member memberToBan;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("tempban"));
    String category = "utilities";
    String exampleCommand = "`!tempban <@user> <time in days>`";
    String shortCommandDescription = "Temporarily ban a user.";
    String fullCommandDescription = "Temporarily ban a user.\n" +
            "Requires both for the bot and user ban members permission";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        String[] args = e.getArgs();

        if (!e.isFromGuild()) {
            e.getChannel().sendMessage("You cannot ban people in DM's.").queue();
            return;
        }

        if (mentionsUser()) {
            memberToBan = e.getMessage().getMentionedMembers().get(0);
            if (checkAllPerms()) {
                if (args.length > 2){
                    banUser(args);
                } else {
                    e.getChannel().sendMessage(getFullHelp("Error: requires at least 2 arguments.", e.getPrefix())).queue();
                }
            }
        } else {
            e.getChannel().sendMessage(getFullHelp("Error: requires a mentioned user.", e.getPrefix())).queue();
        }
    }

    public boolean checkAllPerms() {
        Permissions permissions = new Permissions(e.getGuild());

        if (!(permissions.botHasPermission(Permission.BAN_MEMBERS))) {
            e.getChannel().sendMessage(getFullHelp("Error: Bot requires the ban members permission.", e.getPrefix())).queue();
            return false;
        } else if (!(permissions.userHasPermission(e.getAuthor(), Permission.BAN_MEMBERS))) {
            e.getChannel().sendMessage(getFullHelp("Error: User requires the ban members permission to run the command.", e.getPrefix())).queue();
            return false;
        } else if (!(permissions.botCanInteract(memberToBan))) {
            e.getChannel().sendMessage(getFullHelp("Error: Bot is a lower or the same level as the user given.", e.getPrefix())).queue();
            return false;
        } else if (!permissions.userCanInteract(e.getAuthor(), memberToBan)) {
            e.getChannel().sendMessage(getFullHelp("Error: User is a lower or the same level as the user given.", e.getPrefix())).queue();
            return false;
        }
        return true;
    }

    public void banUser(String[] args) {
        e.getChannel().sendMessage(memberToBan.getAsMention() + " has been banned for a total of " + args[3] + " days").queue();
    }

    public boolean mentionsUser() {
        return e.getMessage().getMentionedUsers().size() > 0;
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
