package database.user;

import com.mongodb.*;
import commands.CommandReceivedEvent;
import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.Instant;

import static commands.CommandEnum.bot;

public class DatabaseUser {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection user;

    public DatabaseUser() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("discordbot");
        user = database.getCollection("user");

    }

    public DBObject getUserFromDB(String userID) {
        DBObject query = new BasicDBObject("userID", userID);
        DBCursor cursor = user.find(query);
        return cursor.one();
    }

    public void allUsersInDb(GuildMessageReceivedEvent e, String userID) {
        DBObject query = new BasicDBObject("userID", userID);
        DBCursor cursor = user.find(query);
        cursor.forEach((value -> e.getChannel().sendMessage(value + "").queue()));
    }

    public UserDB getUserFromDBToUserDB(String userID) {
        DBObject query = new BasicDBObject("userID", userID);
        DBCursor cursor = user.find(query);
        if (cursor.one() == null){
            return createStanderdUserDB(userID);
        }
        return dbObjectToUser(cursor.one());
    }

    public boolean userExistsInDB(String userID) {
        DBObject query = new BasicDBObject("userID", userID);
        DBCursor cursor = user.find(query);
        return cursor.one() != null;
    }

    public DBObject userToDBObject(UserDB userDB) {
        return new BasicDBObject("userID", userDB.getUserID()).append("level", userDB.getLevel()).append("xp", userDB.getXp());
    }

    public UserDB dbObjectToUser(DBObject dbObject) {
        String userID = dbObject.get("userID").toString();
        int level = Integer.parseInt(dbObject.get("level").toString());
        int xp = Integer.parseInt(dbObject.get("xp").toString());

        UserDB userDB = new UserDB(userID);
        userDB.setLevel(level);
        userDB.setXp(xp);
        userDB.calculateXpForLevelUp();
        return userDB;
    }

    public void addUserToDB(UserDB userDB) {
        user.insert(userToDBObject(userDB));
    }

    public void addRandomXPToUserInDB(String userID) {
        UserDB userDB = getUserFromDBToUserDB(userID);
        userDB.addRandomXp();
        DBObject query = new BasicDBObject("userID", userID);
        user.findAndModify(query, userToDBObject(userDB));
    }

    public boolean checkLevelUserInDB(String userID) {
        UserDB userDB = getUserFromDBToUserDB(userID);
        userDB.calculateXpForLevelUp();
        DBObject query = new BasicDBObject("userID", userID);
        if (userDB.calculateLevel()) {
            user.findAndModify(query, userToDBObject(userDB));
            return true;
        }
        user.findAndModify(query, userToDBObject(userDB));
        return false;
    }

    public int getLevelUserInDB(String userID) {
        UserDB userDB = getUserFromDBToUserDB(userID);
        return userDB.getLevel();
    }

    public UserDB createStanderdUserDB(String userID) {
        return new UserDB(userID);
    }

    public EmbedBuilder topTenLeaderboard(CommandReceivedEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        Colors colors = new Colors();

        eb.setColor(colors.getCurrentColor());
        eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
        eb.setFooter("Made by Tijs ");
        eb.setTimestamp(Instant.now());

        DBCursor cursor = user.find();
        cursor.sort(new BasicDBObject("level", -1).append("xp", -1)).limit(10).forEach(basicDBObject -> {
            UserDB userDB = dbObjectToUser(basicDBObject);
            eb.addField(e.getJDA().getUserById(userDB.getUserID()).getName(), "Level: " + userDB.getLevel() + "\nXP: " + userDB.getXp() + " out of " + userDB.getXpForLevelUp(), false);
        });

        return eb;
    }

    public String addXpToUser(String userId) {
        UserDB userDB = new UserDB(userId);
        userDB.addRandomXp();

        if (!userExistsInDB(userId)) {
            user.insert(userToDBObject(userDB));
        }

        userDB = getUserFromDBToUserDB(userId);
        userDB.addRandomXp();
        if (userDB.calculateLevel()) {
            addUserToDB(userDB);
            return userDB.getLevel() + "";
        }
        return "";
    }
}
