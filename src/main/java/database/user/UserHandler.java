package database.user;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class UserHandler {
    DatabaseUser databaseUser = new DatabaseUser();
    GuildMessageReceivedEvent e;
    String userID;

    public void checkUser(GuildMessageReceivedEvent e) {
        this.e = e;
        userID = e.getAuthor().getId();

        if (e.getAuthor().isBot()) return;

        if (databaseUser.userExistsInDB(userID)) {
            databaseUser.addRandomXPToUserInDB(userID);
            if (databaseUser.checkLevelUserInDB(userID)) {
                e.getChannel().sendMessage(e.getMember().getEffectiveName() + " has reached level " + databaseUser.getLevelUserInDB(userID)).queue();
            }
        } else {
            UserDB userDB = new UserDB(userID);
            userDB.addRandomXp();
            databaseUser.addUserToDB(userDB);
        }
    }
}
