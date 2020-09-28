package database.reactionroles;

import com.mongodb.*;

import java.util.List;

public class DatabaseReactionRoles {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection reactionRole;

    int i = 0;

    public DatabaseReactionRoles() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("discordbot");
        reactionRole = database.getCollection("reactionroles");
    }

    public DBObject getReactionRole(String messageId) {
        DBObject query = new BasicDBObject("messageId", messageId);
        DBCursor cursor = reactionRole.find(query);
        return cursor.one();
    }

    public boolean reactionRoleExistsInDb(String messageId) {
        DBObject query = new BasicDBObject("messageId", messageId);
        DBCursor cursor = reactionRole.find(query);
        return cursor.one() != null;
    }

    public DBObject reactionRoleDBToDBObject(ReactionRoleDB reactionRolesDB) {
        return new BasicDBObject("messageId", reactionRolesDB.getMessageId()).append("textChannelId", reactionRolesDB.getTextChannelId()).append("roleEmojiArray", roleEmojiObjectsToDBObjectList(reactionRolesDB.getRoleEmojiObject()));
    }

    public ReactionRoleDB DBObjectToReactionRoleDB(DBObject dbObject) {
        return new ReactionRoleDB(dbObject.get("messageId").toString(), dbObject.get("textChannelId").toString(), dbObjectListToRoleEmojiObjects((BasicDBList) dbObject.get("roleEmojiArray")));
    }

    public void addReactionRoleToDB(ReactionRoleDB reactionRoleDB) {
        reactionRole.insert(reactionRoleDBToDBObject(reactionRoleDB));
    }

    public ReactionRoleDB getReactionRoleToReactionRoleDB(String messageId) {
        return DBObjectToReactionRoleDB(getReactionRole(messageId));
    }

    public BasicDBList roleEmojiObjectsToDBObjectList(RoleEmojiObject[] roleEmojiObjects) {
        BasicDBList basicDBList = new BasicDBList();
        for (RoleEmojiObject roleEmojiObject : roleEmojiObjects) {
            basicDBList.add(new BasicDBObject("emojiId", roleEmojiObject.getEmojiId()).append("roleId", roleEmojiObject.getRoleId()));
        }
        return basicDBList;
    }

    public RoleEmojiObject[] dbObjectListToRoleEmojiObjects(BasicDBList basicDBList) {
        RoleEmojiObject[] roleEmojiObjects = new RoleEmojiObject[basicDBList.size()];
        basicDBList.forEach((value -> {
            BasicDBObject basicDBObject = (BasicDBObject) value;
            roleEmojiObjects[i] = new RoleEmojiObject(basicDBObject.get("roleId").toString(), basicDBObject.get("emojiId").toString());
            i++;
        }));
        return roleEmojiObjects;
    }
}
