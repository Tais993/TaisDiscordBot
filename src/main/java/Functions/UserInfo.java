package Functions;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.time.OffsetDateTime;

public class UserInfo {
    User user;
    Member member;

    public UserInfo(Member memberGiven) {
        member = memberGiven;
        user = memberGiven.getUser();
    }

    public String getDateCreated() {
        OffsetDateTime offsetDateTime = member.getTimeCreated();
        return offsetDateTime.getDayOfMonth() + "-" + offsetDateTime.getMonthValue() + "-" + offsetDateTime.getYear() + " " + offsetDateTime.getHour() + ":" + offsetDateTime.getMinute();
    }

    public String getJoinedServerDate() {
        OffsetDateTime offsetDateTime = member.getTimeJoined();
        return offsetDateTime.getDayOfMonth() + "-" + offsetDateTime.getMonthValue() + "-" + offsetDateTime.getYear() + " " + offsetDateTime.getHour() + ":" + offsetDateTime.getMinute();
    }

    public String getTimeBoosted() {
        OffsetDateTime offsetDateTime = member.getTimeBoosted();
        OffsetDateTime currentOffsetDateTime = OffsetDateTime.now();

        int year = currentOffsetDateTime.getYear() - offsetDateTime.getYear();
        int month = currentOffsetDateTime.getMonthValue() - offsetDateTime.getMonthValue();
        double day = currentOffsetDateTime.getDayOfMonth() - offsetDateTime.getDayOfMonth();

        if (year != 0){
            month = month + (year * 12);
        }

        if (month != 0){
            day = day + (month * 30.4368499);
        }

        return Math.round(day) + " days";
    }

    public String getMonthsNitro() {
        OffsetDateTime offsetDateTime = member.getTimeBoosted();
        return offsetDateTime.getDayOfMonth() + "-" + offsetDateTime.getMonthValue() + "-" + offsetDateTime.getYear() + " " + offsetDateTime.getHour() + ":" + offsetDateTime.getMinute();
    }

    public String getRoles() {
        StringBuilder allRoles = new StringBuilder();
        member.getRoles().forEach((value -> allRoles.append(value.getAsMention())));

        return allRoles.toString();
    }

    public String test() {
        return "";
    }
}
