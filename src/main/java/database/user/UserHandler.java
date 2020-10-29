package database.user;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UserHandler {
    DatabaseUser databaseUser = new DatabaseUser();
    MessageReceivedEvent e;
    String userId;

    public void checkUser(MessageReceivedEvent event) {
        e = event;
        userId = e.getAuthor().getId();

        if (e.getAuthor().isBot()) return;

        String level = databaseUser.addRandomXPToUserInDB(userId);
        if (!level.isEmpty()) {
            System.out.println(e.getMember().getEffectiveName() + " has reached level " + level);
            // e.getChannel().sendMessage(e.getMember().getEffectiveName() + " has reached level " + level).queue();
        }
    }
}
