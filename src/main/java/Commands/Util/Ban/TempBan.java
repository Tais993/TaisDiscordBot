package Commands.Util.Ban;

import Commands.CommandEnum;
import Commands.ICommand;
import Functions.Permissions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class TempBan implements ICommand {
    Permissions permissions = new Permissions();
    CommandEnum commandEnum = new CommandEnum();

    Member memberToBan;

    GuildMessageReceivedEvent e;
    String command = "tempban";
    String commandAlias = "tempban";
    String category = "util";
    String exampleCommand = "`!tempban <@user> <time in days>`";
    String shortCommandDescription = "Temporarily ban a user.";
    String fullCommandDescription = "Temporarily ban a user.\n" +
            "Requires both for the bot and user ban members permission";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        if (mentionsUser()) {
            memberToBan = e.getMessage().getMentionedMembers().get(0);
            if (checkAllPerms()) {
                if (args.length > 2){
                    banUser(args);
                } else {
                    e.getChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: requires at least 2 arguments.").build()).queue();
                }
            }
        } else {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: requires a mentioned user.").build()).queue();
        }
    }

    public boolean checkAllPerms() {
        if (!(permissions.botHasPermission(e.getGuild(), Permission.BAN_MEMBERS))) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: Bot requires the ban members permission.").build()).queue();
            return false;
        } else if (!(permissions.userHasPermission(e.getAuthor(), e.getGuild(), Permission.BAN_MEMBERS))) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: User requires the ban members permission to run the command.").build()).queue();
            return false;
        } else if (!(permissions.botCanInteract(memberToBan, e.getGuild()))) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: Bot is a lower or the same level as the user given.").build()).queue();
            return false;
        } else if (!permissions.userCanInteract(e.getAuthor(), memberToBan, e.getGuild())) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("tempban").setDescription("Error: User is a lower or the same level as the user given.").build()).queue();
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
