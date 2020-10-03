package database.remindme;

import java.util.ArrayList;

public class UserRemindMeDB {
    String userId;
    ArrayList<RemindMeDB> remindMeDBArrayList = new ArrayList<>();

    public UserRemindMeDB(String userId) {
        this.userId = userId;
    }

    public void addToArrayList(RemindMeDB remindMeDB) {
        remindMeDBArrayList.add(remindMeDB);
    }

    public RemindMeDB getFromArrayList(int index) {
        return remindMeDBArrayList.get(0);
    }

    public void removeFromArrayList(RemindMeDB remindMeDB) {
        remindMeDBArrayList.remove(remindMeDB);
    }

    public ArrayList<RemindMeDB> getRemindMeDBArrayList() {
        return remindMeDBArrayList;
    }

    public void setRemindMeDBArrayList(ArrayList<RemindMeDB> remindMeDBArrayList) {
        this.remindMeDBArrayList = remindMeDBArrayList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void tenMinutesCheck() {
        remindMeDBArrayList.forEach((remindMeDB -> {
            if (remindMeDB.tenMinutesCheck()) {

            }
        }));
    }
}
