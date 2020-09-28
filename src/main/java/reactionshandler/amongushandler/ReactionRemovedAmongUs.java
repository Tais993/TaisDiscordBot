package reactionshandler.amongushandler;

import database.reactions.DatabaseReactions;
import database.reactions.ReactionDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

public class ReactionRemovedAmongUs {

    GuildMessageReactionRemoveEvent e;

    String userAsTag;
    String textChannelID;
    TextChannel textChannel;

    DatabaseReactions databaseReactions = new DatabaseReactions();

    public ReactionRemovedAmongUs(GuildMessageReactionRemoveEvent event) {
        e = event;

        if (!e.getReactionEmote().toString().equals("RE:U+2705")) return;

        String messageID = event.getMessageId();

        if (e.getUser().isBot()) return;
        if (!e.getReactionEmote().toString().equals("RE:U+2705")) return;

        ReactionDB reactionDB = databaseReactions.getReactionDBFromDB(messageID);

        userAsTag = e.getUser().getAsTag();

        textChannelID = reactionDB.getTextChannelID();
        textChannel = e.getGuild().getTextChannelById(textChannelID);

        MessageEmbed oldMessage = textChannel.retrieveMessageById(messageID).complete().getEmbeds().get(0);

        EmbedBuilder eb = new EmbedBuilder(oldMessage);

        String newFooter = oldMessage.getFooter().getText().replaceAll("- " + userAsTag, "");

        eb.setFooter(newFooter);

        textChannel.editMessageById(messageID, eb.build()).queue();
    }
}
