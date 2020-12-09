package utilities.entities;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;

public class GuildInfo {
    Guild guild;

    public GuildInfo(Guild guild){
        this.guild = guild;
    }

    public int getTotalAnimatedEmojis() {
        return (int) guild.getEmotes().stream().filter(Emote::isAnimated).count();
    }

    public int getTotalNonAnimatedEmojis() {
        return (int) guild.getEmotes().stream().filter(value -> !value.isAnimated()).count();
    }
}
