package database.user;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UserHandler {
    DatabaseUser databaseUser = new DatabaseUser();
    MessageReceivedEvent e;
    String userID;

    public void checkUser(MessageReceivedEvent event) {
        e = event;
        userID = e.getAuthor().getId();

        if (e.getAuthor().isBot()) return;

        String level = databaseUser.addXpToUser(userID);
        if (!level.isEmpty()) {
            // e.getChannel().sendMessage(e.getMember().getEffectiveName() + " has reached level " + level).queue();
        }
    }
}
