package music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import commands.music.objects.AudioPlayerO;
import commands.music.objects.AudioTrackO;
import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Collections;
import java.util.LinkedList;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayerO playerO;
    private final LinkedList<AudioTrackO> queue;
    private int currentIndex = 0;

    private AudioTrackO previousAudioTrackO;
    private AudioTrackO currentPlayingAudioTrackO;

    boolean loopSong = false;
    boolean loopQueue = false;

    int volume = 100;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player) {
        this.playerO = new AudioPlayerO(player);
        this.queue = new LinkedList<>();
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track, String userId, String userTag) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!playerO.getPlayer().startTrack(track, true)) {
            queue.offer(new AudioTrackO(track, userId, userTag));
        } else {
            playerO.setPlayerUp(userId, userTag);
            currentPlayingAudioTrackO = new AudioTrackO(playerO.getPlayer().getPlayingTrack(), playerO.getUserId(), playerO.getUserTag());
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */

    public void nextTrack() {
        previousAudioTrackO = currentPlayingAudioTrackO;
        if (loopSong) {
            playerO.getPlayer().startTrack(previousAudioTrackO.getTrack().makeClone(), false);
            playerO.setPlayerUp(playerO.getUserId(), playerO.getUserTag());
            currentPlayingAudioTrackO = new AudioTrackO(playerO.getPlayer().getPlayingTrack(), playerO.getUserId(), playerO.getUserTag());
            return;
        }
        if (loopQueue) {
            queue.add(new AudioTrackO(playerO.getPlayer().getPlayingTrack().makeClone(), playerO.getUserId(), playerO.getUserTag()));
            return;
        }
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        AudioTrackO audioTrackO = new AudioTrackO(queue.get(0).getTrack(), queue.get(0).getUserId(), queue.get(0).getUserTag());
        playerO.getPlayer().startTrack(queue.poll().getTrack(), false);
        playerO.setPlayerUp(audioTrackO.getUserId(), audioTrackO.getUserTag());
        currentPlayingAudioTrackO = new AudioTrackO(playerO.getPlayer().getPlayingTrack(), playerO.getUserId(), playerO.getUserTag());
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    public EmbedBuilder getQueue() {

        EmbedBuilder eb = getNowPlaying();
        eb.appendDescription("\n");


        currentIndex = 0;

        queue.forEach((audioTrackO -> {
            AudioTrack audioTrack = audioTrackO.getTrack();
            eb.appendDescription("\n");
            currentIndex++;

            eb.appendDescription("*Number in queue: " + currentIndex + "*\n");
            eb.appendDescription("**" + audioTrack.getInfo().author + "**\n");
            eb.appendDescription("[" + audioTrack.getInfo().title + "](" + audioTrack.getInfo().uri + ")\n");
            eb.appendDescription(videoDurationToYoutube(audioTrack.getDuration()) + " | " + audioTrackO.getUserTag() + "\n");
        }));

        return eb;
    }

    public EmbedBuilder getNowPlaying() {
        AudioTrack np = playerO.getPlayer().getPlayingTrack();
        Colors colors = new Colors();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(colors.getCurrentColor());

        eb.setTitle("Queue");
        eb.appendDescription("*Currently playing*\n");
        eb.appendDescription("**" + np.getInfo().author + "**\n");
        eb.appendDescription("[" + np.getInfo().title + "](" + np.getInfo().uri + ")\n");
        eb.appendDescription(videoDurationToYoutube(np.getPosition()) + " / " + videoDurationToYoutube(np.getDuration()) + " | " + playerO.getUserTag());

        return eb;
    }

    public AudioTrack removeFromQueue(int removeAudioTrackIndex) {
        AudioTrack audioTrack;

        if (queue.size() >= removeAudioTrackIndex) {
            audioTrack = queue.get(removeAudioTrackIndex - 1).getTrack();
            queue.remove(removeAudioTrackIndex - 1);
            return audioTrack;
        }
        return null;
    }

    public static String videoDurationToYoutube(long value) {
        long totalSeconds = value / 1000;

        int minutes = 0;
        int seconds;

        while (totalSeconds >= 60) {
            minutes++;
            totalSeconds += - 60;
        }

        seconds = (int) totalSeconds;

        StringBuilder sb = new StringBuilder();
        sb.append(minutes);

        if (sb.toString().length() == 1) {
            sb.replace(0, 1, "0" + minutes + "");
        }

        sb.append(":");

        sb.append(seconds);
        if (sb.toString().length() == 4) {
            sb.replace(3, 4, "0" + seconds + "");
        }

        return sb.toString();
    }

    public void playPreviousTrack() {
        queue.addFirst(new AudioTrackO(playerO.getPlayer().getPlayingTrack(), previousAudioTrackO.getUserId(), previousAudioTrackO.getUserTag()));
        playerO.getPlayer().stopTrack();
        previousAudioTrackO.getTrack().setPosition(0);
        playerO.getPlayer().startTrack(previousAudioTrackO.getTrack().makeClone(), false);
    }

    public void forwardsTrack(long seconds) {
        AudioTrack audioTrack = playerO.getPlayer().getPlayingTrack();
        playerO.getPlayer().stopTrack();

        long timeToSet = audioTrack .getPosition() + (seconds * 1000);

        AudioTrack audioTrackClone = audioTrack.makeClone();
        audioTrackClone.setPosition(timeToSet);
        playerO.getPlayer().startTrack(audioTrackClone, false);
    }

    public void backwardsTrack(long seconds) {
        AudioTrack audioTrack = playerO.getPlayer().getPlayingTrack();
        playerO.getPlayer().stopTrack();

        long timeToSet = audioTrack .getPosition() - (seconds * 1000);

        AudioTrack audioTrackClone = audioTrack.makeClone();
        audioTrackClone.setPosition(timeToSet);
        playerO.getPlayer().startTrack(audioTrackClone, false);
    }

    public void clearQueue() {
        queue.clear();
    }

    public void shuffleQueue() {
        Collections.shuffle(queue);
        Collections.shuffle(queue);
        Collections.shuffle(queue);
    }


    public void setTrackTime(long seconds) {
        AudioTrack audioTrack = playerO.getPlayer().getPlayingTrack();
        playerO.getPlayer().stopTrack();

        long timeToSet = seconds * 1000;

        AudioTrack audioTrackClone = audioTrack.makeClone();
        audioTrackClone.setPosition(timeToSet);
        playerO.getPlayer().startTrack(audioTrackClone, false);
    }

    public void addFirstToQueue(AudioTrack track, String userId, String userName) {
        queue.addFirst(new AudioTrackO(track, userId, userName));
    }

    public void moveTrackInQueue(int indexTrackToMove, int indexToMoveTo) {
        AudioTrackO audioTrackO = queue.get(indexTrackToMove - 1);
        AudioTrack track = audioTrackO.getTrack();
        queue.remove(track);
        if (queue.size() >= indexToMoveTo) {
            queue.add(indexToMoveTo - 1, new AudioTrackO(track.makeClone(), audioTrackO.getUserId(), audioTrackO.getUserTag()));
        } else {
            queue.add(new AudioTrackO(track.makeClone(), audioTrackO.getUserId(), audioTrackO.getUserTag()));
        }
    }

    public void skipToTrack(int index) {
        if (queue.size() >= index) {
            if (index > 1) queue.subList(0, index - 1).clear();
            nextTrack();
        }
    }

    public void replayTrack() {
        AudioTrack audioTrack = playerO.getPlayer().getPlayingTrack();
        playerO.getPlayer().stopTrack();

        AudioTrack audioTrackClone = audioTrack.makeClone();
        audioTrackClone.setPosition(0);
        playerO.getPlayer().startTrack(audioTrackClone, false);
    }

    public void clearQueueFromUser(String userId) {
        queue.forEach((audioTrackO -> {
            if (audioTrackO.getUserId().equals(userId)) queue.remove(audioTrackO);
        }));
    }
}
