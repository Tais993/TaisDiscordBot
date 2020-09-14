package Commands.Util;

import Commands.CommandEnum;
import Commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Rename implements ICommand {
    GuildMessageReceivedEvent e;
    Member memberGiven;
    CommandEnum commandEnum = new CommandEnum();

    String command = "rename";
    String commandAlias = "rename";
    String category = "util";
    String exampleCommand = "!rename <@user> nickname";
    String shortCommandDescription = "Rename user to the nickname given";
    String fullCommandDescription = "Rename users to the nickname given, requires manage usernames permission.\n" +
            "Username should be below or the same as 32 chars.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        long idTijs = 257500867568205824L;

        if (e.getMessage().getMentionedMembers().size() > 0 && args.length > 2) {
            memberGiven = e.getMessage().getMentionedMembers().get(0);
            System.err.println(e.getMember().getId() + memberGiven.getId());

            if (e.getMember().getId().equals(memberGiven.getId())) {
                runRenameCommand(args);
            } else if (e.getMember().hasPermission(Permission.NICKNAME_MANAGE)) {
                runRenameCommand(args);
            } else if (e.getMember().getIdLong() == idTijs) {
                runRenameCommand(args);
            } else {
                e.getChannel().sendMessage(commandEnum.getFullHelpItem("rename").setDescription("Error: Manage nicknames permission required to run this command..").build()).queue();
            }

        }
    }

    public void runRenameCommand(String[] args) {
        if (hasNicknameManagePermission() && canInteractMember(memberGiven)) {
            String nickname = arrayToString(args);
            e.getChannel().sendMessage(nickname).queue();

            if (!(nickname.length() > 32)) {
                memberGiven.modifyNickname(nickname).complete();
            } else {
                e.getChannel().sendMessage(commandEnum.getFullHelpItem("rename").setDescription("Error: nickname must be below 32 chars!").build()).queue();
            }
        } else if (!hasNicknameManagePermission()) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("rename").setDescription("Error: bot does not have manage nickname permission.").build()).queue();
        } else if (!canInteractMember(memberGiven)) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("rename").setDescription("Error: member is a higher role or the same as the bot.").build()).queue();
        } else {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("rename").setDescription("Error: member is a higher role as the bot, and bot does not have manage nickname permission").build()).queue();
        }
    }

    public boolean hasNicknameManagePermission() {
        return e.getGuild().getSelfMember().hasPermission(Permission.NICKNAME_MANAGE);
    }

    public boolean canInteractMember(Member memberGiven) {
        return e.getGuild().getSelfMember().canInteract(memberGiven);
    }

    public String arrayToString(String[] args) {
        String output = "";
        int run = 0;

        for (String arg : args) {
            if (!(run < 2)) {
                if (output.equals("")) {
                    output = arg;
                } else {
                    output = output + " " + arg;
                }
            } else {
                run++;
            }
        }
        return output;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getCommandAlias() {
        return commandAlias;
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
}
