package database.guild;

import com.mongodb.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class DatabaseGuild {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection guild;

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
        DBObject query = new BasicDBObject("guildID", guildID);
        DBCursor cursor = guild.find(query);
        if (cursor.one() == null){
            return new GuildDB(guildID);
        }
        return dbObjectToGuildDB(cursor.one());
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

    public void addGuildToDB(GuildDB guildDB) {
        guild.insert(guildDBToDBObject(guildDB));
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
