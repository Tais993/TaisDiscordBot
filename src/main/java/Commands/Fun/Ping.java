package Commands.Fun;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Date;

public class Ping {
    public void ping(GuildMessageReceivedEvent e) {
        Date date = new Date();

        int ping = 1;

        e.getChannel().sendMessage("Ping? " + ping + "ms").queue();
    }

//    public EmbedBuilder returnHelp() {
//        return eb;
//    }
}
