package database.remindme;

import com.mongodb.*;

import java.util.ArrayList;

public class DatabaseRemindMe {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection remindme;

    int i = 0;

    public DatabaseRemindMe() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("discordbot");
        remindme = database.getCollection("remindme");
    }

    public DBObject getDBObjectFromDB(String userId) {
        DBObject query = new BasicDBObject("userId", userId);
        DBCursor cursor = remindme.find(query);
        return cursor.one();
    }

    public UserRemindMeDB[] getAllUserRemindMesFromDB() {
        UserRemindMeDB[] userRemindMeDBS = new UserRemindMeDB[0];
        
        DBObject query = new BasicDBObject();
        DBCursor cursor = remindme.find(query);
        
        cursor.forEach((dbObject -> {
            i++;
            userRemindMeDBS[i] = dbObjectToUserRemindMe(dbObject);
        }));
        return userRemindMeDBS;
    }

    public boolean userRemindMeExistsInDB(String userId) {
        DBObject query = new BasicDBObject("userId", userId);
        DBCursor cursor = remindme.find(query);
        return cursor.one() != null;
    }

    public DBObject userRemindMeDBToDBObject(UserRemindMeDB userRemindMeDB) {
        return new BasicDBObject("userId", userRemindMeDB.getUserId()).append("remindMeDBArrayList", remindMeDBArrayToDBObjectList(userRemindMeDB.getRemindMeDBArrayList()));
    }

    public UserRemindMeDB dbObjectToUserRemindMe(DBObject dbObject) {
        return new UserRemindMeDB(dbObject.get("userId").toString(), dbObjectListToRemindMeDBArray((BasicDBList) dbObject.get("remindMeDBArrayList")));
    }

    public void addUserRemindMeToDB(UserRemindMeDB userRemindMeDB) {
        remindme.insert(userRemindMeDBToDBObject(userRemindMeDB));
    }

    public UserRemindMeDB getUserRemindMeFromDB(String userId) {
        return dbObjectToUserRemindMe(getDBObjectFromDB(userId));
    }


    public BasicDBList remindMeDBArrayToDBObjectList(ArrayList<RemindMeDB> remindMeDBArrayList) {
        BasicDBList basicDBList = new BasicDBList();
        remindMeDBArrayList.forEach((remindMeDB -> {
            basicDBList.add(new BasicDBObject("remindMeId", remindMeDB.getRemindMeId()).append("contentRemindMe", remindMeDB.getContentRemindMe()).append("timeInMilliSeconds", remindMeDB.getTimeInMilliSeconds()));
        }));
        return basicDBList;
    }

    public ArrayList<RemindMeDB> dbObjectListToRemindMeDBArray(BasicDBList basicDBList) {
        ArrayList<RemindMeDB> remindMeDBArrayList = new ArrayList<>();
        basicDBList.forEach((value -> {
            BasicDBObject basicDBObject = (BasicDBObject) value;
            remindMeDBArrayList.add(new RemindMeDB(basicDBObject.get("remindMeId").toString(), basicDBObject.get("contentRemindMe").toString(), Integer.parseInt(basicDBObject.get("timeInMilliSeconds").toString())));
        }));
        return remindMeDBArrayList;
    }
    
    public UserRemindMeDB checkRemindMe() {

        for (UserRemindMeDB userRemindMeDB : getAllUserRemindMesFromDB()) {
            userRemindMeDB.getRemindMeDBArrayList().forEach((remindMeDB -> {
                if (System.currentTimeMillis() >= remindMeDB.getTimeInMilliSeconds()) {
                }
            }));
        }
        return null;
    }
}
