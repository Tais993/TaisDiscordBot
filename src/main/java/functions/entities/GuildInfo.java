package functions.entities;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.time.OffsetDateTime;

public class GuildInfo {
    Guild guild;

    public GuildInfo(Guild guildToSet){
        guild = guildToSet;
    }

    public int getTotalEmojis() {
        return guild.getEmotes().size();
    }

    public int getTotalAnimatedEmojis() {
        return (int) guild.getEmotes().stream().filter(Emote::isAnimated).count();
    }

    public int getTotalNonAnimatedEmojis() {
        return (int) guild.getEmotes().stream().filter(value -> !value.isAnimated()).count();
    }

//    public int getOnlineMemberCount() {
//        return (int) guild.getMembers().stream().filter(value -> value.getOnlineStatus() == OnlineStatus.ONLINE || value.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB).count();
//    }

    public String getDateCreated() {
        OffsetDateTime offsetDateTime = guild.getTimeCreated();
        return offsetDateTime.getDayOfMonth() + "-" + offsetDateTime.getMonthValue() + "-" + offsetDateTime.getYear();
    }

    public String getOwnerName() {
        Member owner = guild.retrieveOwner().complete();
        return owner.getEffectiveName();
    }

    public boolean isValidRole(String roleId) {
        return guild.getRoleById(roleId) != null;
    }
}
