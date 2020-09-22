package database.user;

import java.util.Random;

public class UserDB {
    String userID;
    int level = 1;
    int xp = 0;
    int xpForLevelUp = 100;


    public UserDB(String userID) {
        this.userID = userID;
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
}
