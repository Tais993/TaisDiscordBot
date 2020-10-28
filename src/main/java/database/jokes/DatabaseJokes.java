package database.jokes;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        return dbObjectToJokeDB(cursor.one());
    }

    public JokeDB getRandomJokeFromDatabase() {
        DBObject query = new BasicDBObject();
        DBCursor cursor = jokes.find(query);
        Random r = new Random();
        cursor.skip(r.nextInt((int) jokes.getCount()));
        return dbObjectToJokeDB(cursor.one());
    }

    public boolean jokeExists(String jokeId) {
        DBObject query = new BasicDBObject("jokeId", jokeId);
        DBCursor cursor = jokes.find(query);
        return cursor.one() != null;
    }

    public DBObject jokeDBToDBObject(JokeDB jokeDB) {
        return new BasicDBObject("jokeId", jokeDB.getJokeId()).append("setup", jokeDB.getSetup()).append("punchline", jokeDB.getPunchline());
    }

    public JokeDB dbObjectToJokeDB(DBObject dbObject) {
        String setup = dbObject.get("setup").toString();
        String punchline = dbObject.get("punchline").toString();
        String jokeId = dbObject.get("jokeId").toString();

        JokeDB jokeDB = new JokeDB(setup, punchline);
        return jokeDB;
    }

    public void createIdList() {
        BasicDBObject dbObject = new BasicDBObject();

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(0);

        dbObject.put("ids", ids);
    }

    public void getIdList() {
        DBObject query = new BasicDBObject("ids", -1);
        DBCursor cursor = jokes.find(query);

        BasicDBList basicDBList =(BasicDBList)cursor.one().get("ids");

        ArrayList<Integer> ids = new ArrayList<Integer>();

        basicDBList.forEach((id -> {
            System.out.println(id.toString());
        }));
    }

    public boolean isIdList() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(0);
        DBObject query = new BasicDBObject("ids", ids);
        DBCursor cursor = jokes.find(query);
        return cursor.one() != null;
    }

    public boolean isValidId() {

        return true;
    }

    public void addJoke(JokeDB jokeDB) {
        jokes.insert(jokeDBToDBObject(jokeDB));
    }
}
