package functions.entities;

import net.dv8tion.jda.api.entities.SelfUser;

import java.time.OffsetDateTime;

public class BotInfo {
    public static SelfUser bot;

    public int getJoinedGuilds() {
        return bot.getMutualGuilds().size();
    }

    public String getAvatarUrl() {
        return bot.getAvatarUrl();
    }

    public String getTimeCreated() {
        OffsetDateTime offsetDateTime = bot.getTimeCreated();
        return offsetDateTime.getDayOfMonth() + "-" + offsetDateTime.getMonthValue() + "-" + offsetDateTime.getYear() + " " + offsetDateTime.getHour() + ":" + offsetDateTime.getMinute();
    }
}
