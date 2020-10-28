package database.jokes;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.Random;

public class DatabaseJokes {
    Random r = new Random();

    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection jokes;

    public DatabaseJokes() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("discordbot");
        jokes = database.getCollection("jokes");
    }

    public DBObject getJoke(int jokeId) {
        DBObject query = new BasicDBObject("jokeId", jokeId);
        DBCursor cursor = jokes.find(query);
        return cursor.one();
    }

    public JokeDB getJokeAsJokeDB(int jokeId) {
        DBObject query = new BasicDBObject("jokeId", jokeId);
        DBCursor cursor = jokes.find(query);
        return dbObjectToJokeDB(cursor.one());
    }

    public JokeDB getRandomJokeFromDatabase() {
        DBObject query = new BasicDBObject();
        DBCursor cursor = jokes.find(query);
        cursor.skip(1);
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

        dbObject.put("isList", true);
        dbObject.put("ids", ids);

        jokes.insert(dbObject);
    }

    public ArrayList<Integer> getIdList() {
        DBObject query = new BasicDBObject("isList", true);
        DBCursor cursor = jokes.find(query);

        return (ArrayList<Integer>) cursor.one().get("ids");
    }

    public JokeDB getRandomJoke() {
        ArrayList<Integer> list = getIdList();
        int id = list.get(r.nextInt(list.size()));
        return getJokeAsJokeDB(id);
    }

    public int getAndAddId() {
        DBObject query = new BasicDBObject("isList", true);
        DBCursor cursor = jokes.find(query);
        DBObject dbObject = cursor.one();

        ArrayList<Integer> list = (ArrayList<Integer>) dbObject.get("ids");

        int id = r.nextInt(1000);

        while (list.contains(id)) {
            id = r.nextInt(1000);
        }

        list.add(id);

        dbObject.put("ids", list);

        jokes.update(query, dbObject);

        return id;
    }

    public boolean containsIdList() {
        DBObject query = new BasicDBObject("isList", true);
        DBCursor cursor = jokes.find(query);
        return cursor.one() != null;
    }

    public void addJoke(JokeDB jokeDB) {
        jokeDB.setJokeId(getAndAddId());
        jokes.insert(jokeDBToDBObject(jokeDB));
    }
}
