package Commands.Util.InviteCommand;

import Commands.CommandEnum;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class InviteUser {
    GuildMessageReceivedEvent e;

    public InviteUser(GuildMessageReceivedEvent event, String args) {
        e = event;
        CommandEnum commandEnum = new CommandEnum();

        if (isNumber(args)) {
            String invite = e.getChannel().createInvite().setMaxUses(Integer.valueOf(args)).complete().getUrl();
            e.getChannel().sendMessage("A temporary invite has been created for " + args + " uses! \n" + invite).queue();
        } else {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("invite").build()).queue();
        }
    }

    public boolean isNumber(String args) {
        return args.matches("\\d+");
    }
}
