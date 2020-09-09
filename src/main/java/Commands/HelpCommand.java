package Commands;

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
        Color orange = new Color(219, 65, 5);
        Color purple = new Color(148, 0, 211);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Help fun");
        eb.addField("!ping", "A totally real ping!", true);
        eb.setFooter("Made by Tijs");
        eb.setColor(purple);
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
