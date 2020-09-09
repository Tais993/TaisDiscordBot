package Commands.Fun;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Date;

public class Ping {
    public void Ping(GuildMessageReceivedEvent e) {
        Date date = new Date();

        int ping = date.getTime() - e.getMessage().getTimeCreated().toLocalDateTime();
    }
}
