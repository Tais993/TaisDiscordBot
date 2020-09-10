package Commands.Util;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Invite {
    GuildMessageReceivedEvent e;

    public Invite(GuildMessageReceivedEvent event) {
        e = event;
        String messageReceived = event.getMessage().getContentRaw().replace("!help", "");
        messageReceived = messageReceived.replace(" ", "");
        switch (messageReceived) {
            case "":
                break;
            case "fun":
                break;
        }
    }
}
