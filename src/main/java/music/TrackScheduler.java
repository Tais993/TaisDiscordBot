package music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        player.startTrack(queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    public EmbedBuilder getQueue(AudioTrack np) {
        Colors colors = new Colors();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(colors.getCurrentColor());


        eb.setTitle("Queue");
        eb.appendDescription("*Currently playing*\n");
        eb.appendDescription("**" + np.getInfo().author + "**\n");
        eb.appendDescription("[" + np.getInfo().title + "](" + np.getInfo().uri + ")\n");
        eb.appendDescription(videoDurationToYoutube(np.getPosition()) + " / " + videoDurationToYoutube(np.getDuration()) + "\n");

        for (AudioTrack value : queue) {
            eb.appendDescription("\n");

            eb.appendDescription("**" + value.getInfo().author + "**\n");
            eb.appendDescription("[" + value.getInfo().title + "](" + value.getInfo().uri + ")\n");
            eb.appendDescription(videoDurationToYoutube(value.getDuration()) + "\n");
        }

        return eb;
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
}
