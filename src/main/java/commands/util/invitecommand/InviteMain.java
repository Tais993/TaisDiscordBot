package commands.util.invitecommand;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;

public class InviteMain implements ICommand {
    CommandEnum commandEnum = new CommandEnum();

    InviteTime inviteTime = new InviteTime();
    InviteUses inviteUses = new InviteUses();

    CommandReceivedEvent e;
    String category = "util";
    String command = "invite";
    String commandAlias = "invite";
    String exampleCommand = "`!invite time <time>s/m/h/d`\n`!invite uses <uses>`";
    String shortCommandDescription = "Create server invites";
    String fullCommandDescription = "Create server invites for a period of time or uses the link has\n" +
            "<time> is amount of seconds, minutes, hours or days. (1 day is max, longer then a day is permanent)\n" +
            "<uses> is amount of uses the invite has.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        String[] args = e.getArgs();

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        if (args.length > 2) {
            if (args[1].equals("time")) {
                inviteTime.inviteTime(event, args[2]);
            } else if (args[1].equals("uses")) {
                inviteUses.inviteUses(event, args[2]);
            } else {
                e.getMessageChannel().sendMessage(getFullHelp("invite")).queue();
            }
        } else {
            e.getMessageChannel().sendMessage(getFullHelp("invite")).queue();
        }
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
