package commands.util.invitecommand;

import commands.CommandEnum;
import commands.CommandReceivedEvent;

public class InviteUses {
    CommandReceivedEvent e;

    public void inviteUses(CommandReceivedEvent event, String args) {
        e = event;
        CommandEnum commandEnum = new CommandEnum();

        if (isNumber(args)) {
            String invite = e.getTextChannel().createInvite().setMaxUses(Integer.valueOf(args)).complete().getUrl();
            e.getMessageChannel().sendMessage("A temporary invite has been created for " + args + " uses! \n" + invite).queue();
        } else {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("invite", e.getPrefix()).build()).queue();
        }
    }

    public boolean isNumber(String args) {
        return args.matches("\\d+");
    }
}
