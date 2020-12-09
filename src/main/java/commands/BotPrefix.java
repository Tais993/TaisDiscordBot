package commands;

import database.guild.GuildDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utilities.EventWaiter;

import static utilities.Colors.getCurrentColor;

public class BotPrefix {

    public static void sendBotPrefixGuild (MessageReceivedEvent e, GuildDB guildDB) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setAuthor(e.getGuild().getName(), e.getGuild().getIconUrl(), e.getGuild().getIconUrl());
        eb.setTitle("Bot prefix in this guild is:");
        eb.setDescription("`" + guildDB.getPrefix() + "`");
        eb.setColor(getCurrentColor());

        e.getChannel().sendMessage(eb.build()).queue();

        EventWaiter.waitForEvent(MessageReceivedEvent.class, event -> {
            System.out.println(e.getAuthor().getId() + event.getAuthor().getId());
            return e.getAuthor().getId().equals(event.getAuthor().getId());
            }, event -> {
            event.getChannel().sendMessage(event.getMessage().getContentRaw()).queue();
        },
                10000);
    }
}
