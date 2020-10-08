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

    public void loadAndPlay(TextChannel channel, String trackUrl, boolean addFirst, String userId, String userName) {
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

                if (addFirst) {
                    playTop(musicManager, track, userId, userName);
                } else {
                    play(musicManager, track, userId, userName);
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                playlist.getTracks().forEach((audioTrack -> {
                    loadAndPlay(channel, audioTrack.getInfo().uri, false, userId, userName ,true);
                }));
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

    public void loadAndPlay(TextChannel channel, String trackUrl, boolean addFirst, String userId, String userName, boolean isPlayList) {
            GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

            playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    if (isPlayList) {
                        play(musicManager, track, userId, userName);
                        return;
                    }

                    if (addFirst) {
                        playTop(musicManager, track, userId, userName);
                    } else {
                        play(musicManager, track, userId, userName);
                    }
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {

                    playlist.getTracks().forEach((audioTrack -> {
                        loadAndPlay(channel, audioTrack.getInfo().uri, false, userId, userName, true);
                    }));
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

    public void loadAndPlayRadio(TextChannel channel, String radioUrl, String userId, String userName, String radioName) {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, radioUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                    playRadio(musicManager, track, userId, userName, radioName);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                playlist.getTracks().forEach((audioTrack -> {
                    loadAndPlay(channel, audioTrack.getInfo().uri, false, userId, userName, true);
                }));
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + radioUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                channel.sendMessage("Could not play: " + e.getMessage()).queue();
            }
        });
    }

    private void play (GuildMusicManager musicManager, AudioTrack track, String userId, String userName) {
        musicManager.scheduler.queue(track, userId, userName);
        musicManager.player.setVolume(musicManager.scheduler.volume);
    }

    private void playTop (GuildMusicManager musicManager, AudioTrack track, String userId, String userName) {
        musicManager.scheduler.addFirstToQueue(track, userId, userName);
        musicManager.player.setVolume(musicManager.scheduler.volume);
    }

    private void playRadio (GuildMusicManager musicManager, AudioTrack track, String userId, String userName, String radioName) {
        musicManager.scheduler.playRadio(track, userId, userName, radioName);
    }

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    public EmbedBuilder getQueue(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());

        return musicManager.scheduler.getQueue();
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

    public boolean setVolume(GuildMessageReceivedEvent e, int volume) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.player.setVolume(volume);
        musicManager.scheduler.volume = volume;

        return musicManager.player.getVolume() == volume;
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

    public void playPreviousTrack(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.playPreviousTrack();
    }

    public void forwardsTrack(GuildMessageReceivedEvent e, long seconds) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.forwardsTrack(seconds);
    }

    public void backwardsTrack(GuildMessageReceivedEvent e, long seconds) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.backwardsTrack(seconds);
    }

    public void clearQueue(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.clearQueue();
    }

    public void loopSongToggle(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.loopSong = !musicManager.scheduler.loopSong;
    }

    public void loopQueueToggle(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.loopQueue = !musicManager.scheduler.loopQueue;
    }

    public boolean getLoopQueue(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        return musicManager.scheduler.loopQueue;
    }

    public boolean getLoopSong(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        return musicManager.scheduler.loopSong;
    }

    public void loopOff(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.loopSong = false;
        musicManager.scheduler.loopQueue = false;
    }

    public void shuffleQueue(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.shuffleQueue();
    }

    public void setSongPosition(GuildMessageReceivedEvent e, long seconds) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.setTrackTime(seconds);
    }

    public void moveTrackInQueue(GuildMessageReceivedEvent e, int indexTrackToMove, int indexToMoveTo) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.moveTrackInQueue(indexTrackToMove, indexToMoveTo);
    }

    public void skipToTrack(GuildMessageReceivedEvent e, int index) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.skipToTrack(index);
    }

    public void replayTrack(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.replayTrack();
    }

    public void clearQueueFromUser(GuildMessageReceivedEvent e, String userId) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        musicManager.scheduler.clearQueueFromUser(userId);
    }

    public EmbedBuilder getNowPlaying(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = getGuildMusicManager(e.getGuild());
        return musicManager.scheduler.getNowPlaying();
    }

    public int getVolume(Guild g) {
        GuildMusicManager musicManager = getGuildMusicManager(g);
        return musicManager.player.getVolume();
    }
}