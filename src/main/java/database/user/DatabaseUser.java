package database.user;

import com.mongodb.*;
import commands.CommandReceivedEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bson.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import static commands.CommandEnum.bot;
import static utilities.Colors.getCurrentColor;

public class DatabaseUser {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection user;

    static HashMap<String, UserDB> cachedUsers = new HashMap<>();

    public DatabaseUser() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("discordbot");
        user = database.getCollection("user");

    }

    public UserDB getUserFromDBToUserDB(String userID) {

        UserDB cachedUserDB = cachedUsers.get(userID);

        if (cachedUserDB != null) {
            return cachedUserDB;
        } else {
            DBObject query = new BasicDBObject("userID", userID);
            DBCursor cursor = user.find(query);
            if (cursor.one() == null) {
                UserDB userDB = new UserDB(userID);
                user.insert(userToDBObject(userDB));

                cachedUsers.put(userDB.getUserID(), userDB);

                return new UserDB(userID);
            }
            UserDB userDB = dbObjectToUser(cursor.one());
            cachedUsers.put(userDB.getUserID(), userDB);
            return userDB;
        }
    }

    public DBObject userToDBObject(UserDB userDB) {
        return new BasicDBObject("userID", userDB.getUserID()).append("level", userDB.getLevel()).append("xp", userDB.getXp()).append("reps", userDB.getReps()).append("isBotModerator", userDB.isBotModerator()).append("isBlackListed", userDB.isBlackListed()).append("prefix", userDB.getPrefix()).append("playlists", playlistsToDocument(userDB.getPlaylists()));
    }

    public UserDB dbObjectToUser(DBObject dbObject) {
        String userID = dbObject.get("userID").toString();
        int level = Integer.parseInt(dbObject.get("level").toString());
        int xp = Integer.parseInt(dbObject.get("xp").toString());
        int reps = 0;
        boolean isBotModerator = false;
        boolean isBlackListed = false;
        String prefix = "";
        HashMap<String, ArrayList<Song>> playlists = new HashMap<>();

        if (dbObject.get("reps") != null) reps = Integer.parseInt(dbObject.get("reps").toString());

        if (dbObject.get("isBotModerator") != null) isBlackListed = Boolean.parseBoolean(dbObject.get("isBotModerator").toString());
        if (dbObject.get("isBlackListed") != null) isBotModerator = Boolean.parseBoolean(dbObject.get("isBlackListed").toString());
        if (dbObject.get("prefix") != null) prefix = dbObject.get("prefix").toString();
        if (dbObject.get("playlists") != null) playlists = documentToPlaylists(Document.parse(dbObject.get("playlists").toString()));
//        if (dbObject.get("playlists") != null) playlists = documentToPlaylists(Document.parse(dbObject.get("playlists").toString()));

        UserDB userDB = new UserDB(userID, level, xp, reps, isBlackListed, isBotModerator, prefix, playlists);
        userDB.calculateXpForLevelUp();

        return userDB;
    }

    public void updateUserInDB(UserDB userDB) {
        String userId = userDB.getUserID();

        Thread thread = new Thread(() -> {
            DBObject query = new BasicDBObject("userID", userId);
            user.findAndModify(query, userToDBObject(userDB));
        });

        thread.start();

        UserDB cachedUserDB = cachedUsers.get(userDB.getUserID());

        if (cachedUserDB != null) {
            cachedUsers.replace(userId, userDB);
        } else {
            cachedUsers.put(userId, userDB);
        }
    }

    public String addRandomXPToUserInDB(String userID) {
        String returnValue;

        UserDB userDB = getUserFromDBToUserDB(userID);
        userDB.addRandomXp();

        DBObject query = new BasicDBObject("userID", userID);

        returnValue = userDB.calculateLevel() ? userDB.getLevel() + "" : "";

        user.findAndModify(query, userToDBObject(userDB));

        return returnValue;
    }

    public EmbedBuilder topTenLeaderboard(CommandReceivedEvent e) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(getCurrentColor());
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

    public void setBotModerator(String userId, boolean botModerator) {
        UserDB userDB = getUserFromDBToUserDB(userId);

        userDB.setBotModerator(botModerator);

        updateUserInDB(userDB);
    }

    public void setBlacklisted(String userId, boolean blacklisted) {
        UserDB userDB = getUserFromDBToUserDB(userId);

        userDB.setBlackListed(blacklisted);

        updateUserInDB(userDB);
    }

    public void addRep(String userId) {
        UserDB userDB = getUserFromDBToUserDB(userId);

        userDB.addRep();

        updateUserInDB(userDB);
    }

    public HashMap<String, ArrayList<Song>> documentToPlaylists(Document document) {
        HashMap<String, ArrayList<Song>> playlists = new HashMap<>();

        document.forEach((key, value) -> {
            ArrayList<Document> playlistArray = (ArrayList<Document>) value;


            ArrayList<Song> playlist = new ArrayList<>();

            playlistArray.forEach((songObject -> playlist.add(new Song((String) songObject.get("songUrl"), (String) songObject.get("author"), (String) songObject.get("title")))));
            playlists.put(key, playlist);
        });

        return playlists;
    }

    public Document playlistsToDocument(HashMap<String, ArrayList<Song>> playlists) {
        Document document = new Document();
        playlists.forEach((playlistName, playlist) -> {
            BasicDBList basicDBList = new BasicDBList();
            playlist.forEach((song -> {
                BasicDBObject basicDBObject = new BasicDBObject();
                basicDBObject.append("songUrl", song.getSongUrl()).append("author", song.getAuthor()).append("title", song.getTitle());
                basicDBList.add(basicDBObject);
            }));

            document.append(playlistName, basicDBList);
        });

        return document;
    }
}