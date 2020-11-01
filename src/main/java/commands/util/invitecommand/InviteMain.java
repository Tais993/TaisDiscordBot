package commands.util.invitecommand;

import commands.CommandReceivedEvent;
import commands.ICommand;

import java.util.ArrayList;
import java.util.Arrays;

public class InviteMain implements ICommand {
    CommandReceivedEvent e;

    InviteTime inviteTime = new InviteTime();
    InviteUses inviteUses = new InviteUses();

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("invite"));
    String category = "util";
    String exampleCommand = "invite time <time>s/m/h/d";
    String shortCommandDescription = "Create server invites";
    String fullCommandDescription = "Create server invites for a period of time or uses the link has\n" +
            "<time> is amount of seconds, minutes, hours or days. (1 day is max, longer then a day is permanent)\n" +
            "<uses> is amount of uses the invite has.\n" +
            "`!invite uses <uses>` works if you want it to last for a specific amount of uses.";

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
                e.getMessageChannel().sendMessage(getFullHelp("invite", e.getPrefix())).queue();
            }
        } else {
            e.getMessageChannel().sendMessage(getFullHelp("invite", e.getPrefix())).queue();
        }
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
