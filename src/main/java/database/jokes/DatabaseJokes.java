package database.jokes;

import com.mongodb.*;

public class DatabaseJokes {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection jokes;

    public DatabaseJokes() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("discordbot");
        jokes = database.getCollection("jokes");
    }

    public DBObject getJoke(String jokeId) {
        DBObject query = new BasicDBObject("jokeId", jokeId);
        DBCursor cursor = jokes.find(query);
        return cursor.one();
    }

    public JokeDB getJokeAsJokeDB(String jokeId) {
        DBObject query = new BasicDBObject("jokeId", jokeId);
        DBCursor cursor = jokes.find(query);
        if (cursor.one() == null){
            return createStandardGuildDB(jokeId);
        }
        return dbObjectToJokeDB(cursor.one());
    }

    public boolean jokeExists(String jokeId) {
        DBObject query = new BasicDBObject("jokeId", jokeId);
        DBCursor cursor = jokes.find(query);
        return cursor.one() != null;
    }

    public DBObject jokeDBToDBObject(JokeDB jokeDB) {
        return new BasicDBObject("guildID", jokeDB.getGuildID()).append("prefix", jokeDB.getPrefix());
    }

    public JokeDB dbObjectToJokeDB(DBObject dbObject) {
        String guildID = dbObject.get("guildID").toString();
        String prefix = dbObject.get("prefix").toString();

        JokeDB guildDB = new JokeDB(guildID);
        guildDB.setPrefix(prefix);
        return guildDB;
    }

    public void addGuild(JokeDB jokeDB) {
        jokes.insert(jokeDBToDBObject(jokeDB));
    }

    public String getPrefixGuildInDB(String jokeId) {
        JokeDB GuildDB = getJokeAsJokeDB(jokeId);
        return GuildDB.getPrefix();
    }

    public void setPrefixGuildInDB(String jokeId, String prefix) {
        JokeDB jokeDB = getJokeAsJokeDB(jokeId);
        jokeDB.setPrefix(prefix);

        DBObject query = new BasicDBObject("guildID", jokeId);
        jokes.findAndModify(query, jokeDBToDBObject(jokeDB));
    }

    public static JokeDB createStandardGuildDB(String guildID) {
        return new JokeDB(guildID);
    }
}
