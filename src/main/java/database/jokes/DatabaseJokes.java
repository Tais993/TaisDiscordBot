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

    public void removeJokeById(String jokeId) {
        DBObject query = new BasicDBObject("jokeId", jokeId);
        jokes.remove(query);
        removeId(jokeId);
    }

    public JokeDB getJokeAsJokeDB(String jokeId) {
        DBObject query = new BasicDBObject("jokeId", jokeId);
        DBCursor cursor = jokes.find(query);
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

        return new JokeDB(jokeId, setup, punchline);
    }

    public void createIdList() {
        BasicDBObject dbObject = new BasicDBObject();

        ArrayList<String> ids = new ArrayList<>();
        ids.add("0");

        dbObject.put("isList", true);
        dbObject.put("ids", ids);

        jokes.insert(dbObject);
    }

    public ArrayList<String> getIdList() {
        DBObject query = new BasicDBObject("isList", true);
        DBCursor cursor = jokes.find(query);

        return (ArrayList<String>) cursor.one().get("ids");
    }

    public JokeDB getRandomJoke() {
        ArrayList<String> list = getIdList();
        int itemToGet = r.nextInt(list.size());

        String id = list.get(itemToGet);
        return getJokeAsJokeDB(id);
    }

    public String getAndAddId() {
        DBObject query = new BasicDBObject("isList", true);
        DBCursor cursor = jokes.find(query);
        DBObject dbObject = cursor.one();

        ArrayList<String> list = (ArrayList<String>) dbObject.get("ids");

        String id = Integer.toString(r.nextInt(1000));

        while (list.contains(id)) {
            id = Integer.toString(r.nextInt(1000));
        }

        list.add(id);

        dbObject.put("ids", list);

        jokes.update(query, dbObject);

        return id;
    }

    public void removeId(String id) {
        DBObject query = new BasicDBObject("isList", true);
        DBCursor cursor = jokes.find(query);
        DBObject dbObject = cursor.one();

        ArrayList<String> list = (ArrayList<String>) dbObject.get("ids");

        list.remove(id);
        dbObject.put("ids", list);

        jokes.update(query, dbObject);
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
