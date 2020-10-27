package commands.util.invitecommand;

import commands.CommandEnum;
import commands.CommandReceivedEvent;

public class InviteUser {
    CommandReceivedEvent e;

    public InviteUser(CommandReceivedEvent event, String args) {
        e = event;
        CommandEnum commandEnum = new CommandEnum();

        if (isNumber(args)) {
            String invite = e.getTextChannel().createInvite().setMaxUses(Integer.valueOf(args)).complete().getUrl();
            e.getMessageChannel().sendMessage("A temporary invite has been created for " + args + " uses! \n" + invite).queue();
        } else {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("invite").build()).queue();
        }
    }

    public boolean isNumber(String args) {
        return args.matches("\\d+");
    }
}
