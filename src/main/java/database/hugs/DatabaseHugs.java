package database.hugs;

import com.mongodb.*;

public class DatabaseHugs {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection guild;

    public DatabaseHugs() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("discordbot");
        guild = database.getCollection("hugs");
    }

    public String getGifFromDB(int gifIndex) {
        DBObject query = new BasicDBObject("gifIndex", gifIndex);
        DBCursor cursor = guild.find(query);
        return cursor.one().get("gifUrl").toString();
    }

    public int numberItemsInDB() {
        return (int) guild.count();
    }

    public void addGifToDB(String gifUrl) {
        guild.insert(new BasicDBObject("gifIndex", numberItemsInDB() + 1).append("gifUrl", gifUrl));
    }
}
