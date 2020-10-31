package database.user;

import java.util.Random;

public class UserDB {
    String userID;
    int level = 1;
    int xp = 0;
    int xpForLevelUp = 100;

    long lastTimeRepGiven;

    int reps = 0;

    boolean isBotModerator = false;
    boolean isBlackListed = false;

    public UserDB(String userID) {
        this.userID = userID;
    }

    public UserDB(String userID, int level, int xp, int reps) {
        this.userID = userID;
        this.level = level;
        this.xp = xp;
        this.reps = reps;
    }

    public UserDB(String userID, int level, int xp, boolean isBotModerator, boolean isBlackListed) {
        this.userID = userID;
        this.level = level;
        this.xp = xp;
        this.isBotModerator = isBotModerator;
        this.isBlackListed = isBlackListed;
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public void addRandomXp() {
        Random random = new Random();
        xp = xp + random.nextInt(20);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getUserID() {
        return userID;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getXpForLevelUp() {
        return xpForLevelUp;
    }

    public void calculateXpForLevelUp() {
        xpForLevelUp = level * 100;
    }

    public boolean calculateLevel() {
        if (xp >= xpForLevelUp) {
            xp = xp - xpForLevelUp;
            level++;
            return true;
        }
        return false;
    }

    public boolean isBotModerator() {
        return isBotModerator;
    }

    public void setBotModerator(boolean botModerator) {
        isBotModerator = botModerator;
    }

    public boolean isBlackListed() {
        return isBlackListed;
    }

    public void setBlackListed(boolean blackListed) {
        isBlackListed = blackListed;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getReps() {
        return reps;
    }

    public void addRep() {
        reps++;
    }

    public long getLastTimeRepGiven() {
        return lastTimeRepGiven;
    }

    public void setLastTimeRepGiven() {
        this.lastTimeRepGiven = System.currentTimeMillis();
    }
}
