package database.guild;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GuildHandler {
    DatabaseGuild databaseGuild = new DatabaseGuild();
    MessageReceivedEvent e;
    String guildID;

    public void checkGuild(MessageReceivedEvent event) {
        e = event;
        guildID = e.getGuild().getId();

        databaseGuild.getGuildFromDBToGuildDB(guildID);
    }
}
