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
//        return dbObjectToJokeDB(cursor.one());
    }

    public boolean jokeExists(String jokeId) {
        DBObject query = new BasicDBObject("jokeId", jokeId);
        DBCursor cursor = jokes.find(query);
        return cursor.one() != null;
    }

    public DBObject jokeDBToDBObject(JokeDB jokeDB) {
        return new BasicDBObject("jokeId", jokeDB.getJokeId()).append("setup", jokeDB.getSetup()).append("punchline", jokeDB.getPunchline());
    }

//    public JokeDB dbObjectToJokeDB(DBObject dbObject) {
//        String guildID = dbObject.get("guildID").toString();
//        String prefix = dbObject.get("prefix").toString();
//
////        JokeDB guildDB = new JokeDB(guildID);
//        guildDB.setPrefix(prefix);
//        return guildDB;
//    }

    public void addGuild(JokeDB jokeDB) {
        jokes.insert(jokeDBToDBObject(jokeDB));
    }
}
