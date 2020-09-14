package Commands.Util.InviteCommand;

import Commands.CommandEnum;
import Commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class InviteMain implements ICommand {
    CommandEnum commandEnum = new CommandEnum();

    GuildMessageReceivedEvent e;
    String category = "util";
    String command = "invite";
    String commandAlias = "invite";
    String exampleCommand = "`!invite time <time>s/m/h/d`\n`!invite uses <uses>`";
    String shortCommandDescription = "Create server invites";
    String fullCommandDescription = "Create server invites for a period of time or uses the link has\n" +
            "<time> is amount of seconds, minutes, hours or days. (1 day is max, longer then a day is permanent)\n" +
            "<uses> is amount of uses the invite has.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        if (args.length > 2) {
            if (args[1].equals("time")) {
                InviteTime inviteTime = new InviteTime(event, args[2]);
            } else if (args[1].equals("uses")) {
                InviteUser inviteUser = new InviteUser(event, args[2]);
            } else {
                e.getChannel().sendMessage(commandEnum.getFullHelpItem("invite").build()).queue();
            }
        } else {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("invite").build()).queue();
        }
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getCommandAlias() {
        return null;
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
