package database.reactions;

public class ReactionDB {
    String messageID;
    String textChannelID;
    int playersJoining = 0;

    public ReactionDB(String messageID, String textChannelID) {
        this.messageID = messageID;
        this.textChannelID = textChannelID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setTextChannelID(String textChannelID) {
        this.textChannelID = textChannelID;
    }

    public String getTextChannelID() {
        return textChannelID;
    }

    public int getPlayersJoining() {
        return playersJoining;
    }

    public void setPlayersJoining(int playersJoining) {
        this.playersJoining = playersJoining;
    }

    public void addPlayer() {
        playersJoining++;
    }
}
