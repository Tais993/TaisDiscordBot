package Commands;

import Commands.Fun.Ping;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class HelpCommand {
    GuildMessageReceivedEvent e;

    public HelpCommand(GuildMessageReceivedEvent event) {
        e = event;
        String messageReceived = event.getMessage().getContentRaw().replace("!help", "");
        messageReceived = messageReceived.replace(" ", "");
        switch (messageReceived) {
            case "":
                helpAll();
                break;
            case "fun":
                helpFun();
                break;
        }
    }

    public void helpAll() {
        helpFun();
    }

    public void helpFun() {
        EmbedBuilder eb = new EmbedBuilder();
        Color purple = new Color(148, 0, 211);

        eb.setTitle("Help fun");
        eb.addField("!ping", "A totally real ping!", true);
        eb.addField("!test", "A totally pointless test!", true);
        eb.setFooter("Made by Tijs");

        eb.setColor(purple);
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
