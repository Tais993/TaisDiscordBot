package util.entities;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class GuildInfo {
    Guild guild;

    public GuildInfo(Guild guildToSet){
        guild = guildToSet;
    }

    public int getTotalAnimatedEmojis() {
        return (int) guild.getEmotes().stream().filter(Emote::isAnimated).count();
    }

    public int getTotalNonAnimatedEmojis() {
        return (int) guild.getEmotes().stream().filter(value -> !value.isAnimated()).count();
    }

    public String getOwnerName() {
        Member owner = guild.retrieveOwner().complete();
        return owner.getEffectiveName();
    }
}
