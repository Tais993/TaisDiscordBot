package music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

import static music.TrackScheduler.videoDurationToYoutube;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
        Long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                Colors colors = new Colors();
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Adding to queue ");
                eb.appendDescription("[" + track.getInfo().title + "](" + track.getInfo().uri + ")\n");
                eb.setColor(colors.getCurrentColor());

                channel.sendMessage(eb.build()).queue();

                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                play(musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                channel.sendMessage("Could not play: " + e.getMessage()).queue();
            }
        });
    }

    private void play (GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    public EmbedBuilder getQueue(GuildMessageReceivedEvent e, AudioTrack playingTrack) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());

        return musicManager.scheduler.getQueue(playingTrack);
    }

    public EmbedBuilder removeFromQueue(GuildMessageReceivedEvent e, int removeAudioTrackIndex) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        AudioTrack audioTrack = musicManager.scheduler.removeFromQueue(removeAudioTrackIndex);

        EmbedBuilder eb = new EmbedBuilder();
        Colors colors = new Colors();
        eb.setColor(colors.getCurrentColor());

        if (audioTrack == null) {
            eb.setTitle("Error removing track from queue");
            eb.appendDescription("Given index is higher as queue size");
        } else {
            eb.setTitle("Succesfully removed " + audioTrack.getInfo().title);
            eb.appendDescription("**" + audioTrack.getInfo().author + "**\n");
            eb.appendDescription("[" + audioTrack.getInfo().title + "](" + audioTrack.getInfo().uri + ")\n");
            eb.appendDescription(videoDurationToYoutube(audioTrack.getDuration()));
        }

        return eb;
    }

    public void skip(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.nextTrack();
    }

    public void pause(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.player.setPaused(true);
    }

    public void resume(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.player.setPaused(false);
    }
}