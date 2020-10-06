package commands.music.objects;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

public class AudioPlayerO {
    AudioPlayer player;
    String userId;
    String userTag;

    public AudioPlayerO(AudioPlayer player) {
        this.player = player;
    }

    public AudioPlayerO(AudioPlayer player, String userId, String userTag) {
        this.player = player;
        this.userId = userId;
        this.userTag = userTag;
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setPlayerUp(String userId, String userTag) {
        this.userId = userId;
        this.userTag = userTag;
    }
}
