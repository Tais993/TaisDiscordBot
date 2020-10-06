package database.remindme;

public class RemindMeDB {
    String remindMeId;
    String contentRemindMe;

    long timeInMilliSeconds;

    public RemindMeDB(String remindMeId, String contentRemindMe, int timeInMilliSeconds) {
        this.remindMeId = remindMeId;
        this.contentRemindMe = contentRemindMe;
        this.timeInMilliSeconds = System.currentTimeMillis() + timeInMilliSeconds / 1000;
    }

    public void setRemindMeId(String remindMeId) {
        this.remindMeId = remindMeId;
    }

    public void setContentRemindMe(String contentRemindMe) {
        this.contentRemindMe = contentRemindMe;
    }

    public void setTimeInSeconds(int timeInMilliSeconds) {
        this.timeInMilliSeconds = timeInMilliSeconds;
    }

    public String getRemindMeId() {
        return remindMeId;
    }

    public String getContentRemindMe() {
        return contentRemindMe;
    }

    public long getTimeInMilliSeconds() {
        return timeInMilliSeconds;
    }
}
