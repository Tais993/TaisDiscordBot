package database.reactions;

import com.mongodb.*;

import java.util.ArrayList;

public class DatabaseReactions {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection reactions;

    public DatabaseReactions() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("discordbot");
        reactions = database.getCollection("reactions");
    }

    public DBObject getDBObjectFromDB(String messageId) {
        DBObject query = new BasicDBObject("messageId", messageId);
        DBCursor cursor = reactions.find(query);
        return cursor.one();
    }

    public ArrayList<String> allReactionsInDb() {
        DBObject query = new BasicDBObject();
        DBCursor cursor = reactions.find(query);

        ArrayList<String> messageIds = new ArrayList<>();

        cursor.forEach((databaseObject -> {
            messageIds.add(databaseObject.get("messageId").toString());
        }));

        return messageIds;
    }

    public boolean reactionDBExistsInDB(String messageId) {
        DBObject query = new BasicDBObject("messageId", messageId);
        DBCursor cursor = reactions.find(query);
        return cursor.one() != null;
    }

    public DBObject reactionDBToDBObject(ReactionDB reactionDB) {
        return new BasicDBObject("messageId", reactionDB.getMessageID()).append("TextChannelId", reactionDB.getTextChannelID()).append("playersJoining", reactionDB.getPlayersJoining());
    }

    public ReactionDB dbObjectToReactionDB(DBObject dbObject) {
        ReactionDB reactionDB = new ReactionDB(dbObject.get("messageId").toString(), dbObject.get("TextChannelId").toString());
        reactionDB.setPlayersJoining(Integer.parseInt(dbObject.get("playersJoining").toString()));

        return reactionDB;
    }

    public void addReactionToDB(ReactionDB reactionDB) {
        reactions.insert(reactionDBToDBObject(reactionDB));
    }

    public String getTextChannelIDFromDB(String messageID) {
        DBObject query = new BasicDBObject("messageId", messageID);
        DBCursor cursor = reactions.find(query);
        return cursor.one().get("TextChannelId").toString();
    }

    public int getPlayersJoiningFromDB(String messageID) {
        DBObject query = new BasicDBObject("messageId", messageID);
        DBCursor cursor = reactions.find(query);
        return Integer.parseInt(cursor.one().get("playersJoining").toString());
    }

    public void addPlayerToPlayersJoining(String messageId) {
        ReactionDB reactionDB = getReactionDBFromDB(messageId);
        reactionDB.addPlayer();

        DBObject query = new BasicDBObject("messageId", messageId);
        reactions.findAndModify(query, reactionDBToDBObject(reactionDB));
    }

    public ReactionDB getReactionDBFromDB(String messageId) {
        return dbObjectToReactionDB(getDBObjectFromDB(messageId));
    }
}
