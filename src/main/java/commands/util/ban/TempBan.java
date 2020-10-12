package commands.util.ban;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.Permissions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class TempBan implements ICommand {
    CommandEnum commandEnum = new CommandEnum();

    Member memberToBan;

    CommandReceivedEvent e;
    String command = "tempban";
    String commandAlias = "tempban";
    String category = "util";
    String exampleCommand = "`!tempban <@user> <time in days>`";
    String shortCommandDescription = "Temporarily ban a user.";
    String fullCommandDescription = "Temporarily ban a user.\n" +
            "Requires both for the bot and user ban members permission";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        String[] args = e.getArgs();

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("You cannot ban people in DM's.").queue();
            return;
        }

        if (mentionsUser()) {
            memberToBan = e.getMessage().getMentionedMembers().get(0);
            if (checkAllPerms()) {
                if (args.length > 2){
                    banUser(args);
                } else {
                    e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: requires at least 2 arguments.").build()).queue();
                }
            }
        } else {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: requires a mentioned user.").build()).queue();
        }
    }

    public boolean checkAllPerms() {
        Permissions permissions = new Permissions(e.getGuild());

        if (!(permissions.botHasPermission(Permission.BAN_MEMBERS))) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: Bot requires the ban members permission.").build()).queue();
            return false;
        } else if (!(permissions.userHasPermission(e.getAuthor(), Permission.BAN_MEMBERS))) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: User requires the ban members permission to run the command.").build()).queue();
            return false;
        } else if (!(permissions.botCanInteract(memberToBan))) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: Bot is a lower or the same level as the user given.").build()).queue();
            return false;
        } else if (!permissions.userCanInteract(e.getAuthor(), memberToBan)) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: User is a lower or the same level as the user given.").build()).queue();
            return false;
        }
        return true;
    }

    public void banUser(String[] args) {
        e.getMessageChannel().sendMessage(memberToBan.getAsMention() + " has been banned for a total of " + args[3] + " days").queue();
    }

    public boolean mentionsUser() {
        return e.getMessage().getMentionedUsers().size() > 0;
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
