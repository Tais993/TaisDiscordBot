package reactionshandler.amongushandler;

import database.reactions.DatabaseReactions;
import database.reactions.ReactionDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class ReactionAddedAmongUs {

    GuildMessageReactionAddEvent e;

    String userAsTag;
    String textChannelID;
    TextChannel textChannel;

    DatabaseReactions databaseReactions = new DatabaseReactions();

    public ReactionAddedAmongUs(GuildMessageReactionAddEvent event) {
        e = event;

        if (!e.getReactionEmote().toString().equals("RE:U+2705")) return;

        String messageId = event.getMessageId();

        ReactionDB reactionDB = databaseReactions.getReactionDBFromDB(messageId);

        if (reactionDB.getPlayersJoining() == 10) return;

        userAsTag = e.getUser().getAsTag();

        textChannelID = reactionDB.getTextChannelID();
        textChannel = e.getGuild().getTextChannelById(textChannelID);

        MessageEmbed oldMessage = textChannel.retrieveMessageById(messageId).complete().getEmbeds().get(0);

        EmbedBuilder eb = new EmbedBuilder(oldMessage);

        if (oldMessage.getFooter() == null){
            eb.setFooter("- " + userAsTag);
        } else {
            eb.setFooter(oldMessage.getFooter().getText() + "\n- " + userAsTag);
        }

        textChannel.editMessageById(messageId, eb.build()).queue();

        databaseReactions.addPlayerToPlayersJoining(messageId);
    }
}
