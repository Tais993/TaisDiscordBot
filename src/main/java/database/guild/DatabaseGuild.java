package database.guild;

import com.mongodb.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;

public class DatabaseGuild {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection guild;

    static HashMap<String, GuildDB> cachedGuilds = new HashMap<>();

    public DatabaseGuild() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("discordbot");
        guild = database.getCollection("guild");
    }

    public DBObject getGuildFromDB(String guildID) {
        DBObject query = new BasicDBObject("guildID", guildID);
        DBCursor cursor = guild.find(query);
        return cursor.one();
    }

    public void allGuildsInDb(GuildMessageReceivedEvent e, String guildID) {
        DBObject query = new BasicDBObject("guildID", guildID);
        DBCursor cursor = guild.find(query);
        cursor.forEach((value -> e.getChannel().sendMessage(value + "").queue()));
    }

    public GuildDB getGuildFromDBToGuildDB(String guildID) {

        GuildDB cachedGuildDB = cachedGuilds.get(guildID);

        if (cachedGuildDB != null) {
            return cachedGuildDB;
        } else {
            DBObject query = new BasicDBObject("guildID", guildID);
            DBCursor cursor = guild.find(query);
            if (cursor.one() == null) {
                GuildDB guildDB = new GuildDB(guildID);
                guild.insert(guildDBToDBObject(guildDB));

                cachedGuilds.put(guildID, guildDB);

                return new GuildDB(guildID);
            }
            GuildDB guildDB = dbObjectToGuildDB(cursor.one());
            cachedGuilds.put(guildID, guildDB);
            return guildDB;
        }
    }

    public boolean guildExistsInDB(String guildID) {
        DBObject query = new BasicDBObject("guildID", guildID);
        DBCursor cursor = guild.find(query);
        return cursor.one() != null;
    }

    public DBObject guildDBToDBObject(GuildDB guildDB) {
        return new BasicDBObject("guildID", guildDB.getGuildID()).append("prefix", guildDB.getPrefix());
    }

    public GuildDB dbObjectToGuildDB(DBObject dbObject) {
        String guildID = dbObject.get("guildID").toString();
        String prefix = dbObject.get("prefix").toString();

        GuildDB guildDB = new GuildDB(guildID);
        guildDB.setPrefix(prefix);
        return guildDB;
    }

    public void updateGuildInDB(GuildDB guildDB) {
        String guildId = guildDB.getGuildID();

        DBObject query = new BasicDBObject("guildID", guildDB);
        guild.findAndModify(query, guildDBToDBObject(guildDB));

        GuildDB cachedGuildDB = cachedGuilds.get(guildDB.getGuildID());

        if (cachedGuildDB != null) {
            cachedGuilds.replace(guildId, guildDB);
        } else {
            cachedGuilds.put(guildId, guildDB);
        }
    }

    public String getPrefixGuildInDB(String guildID) {
        GuildDB GuildDB = getGuildFromDBToGuildDB(guildID);
        return GuildDB.getPrefix();
    }

    public void setPrefixGuildInDB(String guildID, String prefix) {
        GuildDB guildDB = getGuildFromDBToGuildDB(guildID);
        guildDB.setPrefix(prefix);

        DBObject query = new BasicDBObject("guildID", guildID);
        guild.findAndModify(query, guildDBToDBObject(guildDB));
    }
}
