package commands.music.objects;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class AudioTrackO {
    AudioTrack track;
    String userId;
    String userTag;

    public AudioTrackO(AudioTrack track, String userId, String userTag) {
        this.track = track;
        this.userId = userId;
        this.userTag = userTag;
    }

    public AudioTrack getTrack() {
        return track;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUp(AudioTrack track, String userId, String userTag) {
        this.track = track;
        this.userId = userId;
        this.userTag = userTag;
    }
}
