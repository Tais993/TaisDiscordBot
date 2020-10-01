package commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.CommandEnum;
import commands.ICommand;
import functions.Colors;
import music.GuildMusicManager;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import static music.TrackScheduler.videoDurationToYoutube;

public class NowPlaying implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    Colors colors = new Colors();

    String command = "nowplaying";
    String commandAlias = "np";
    String category = "music";
    String exampleCommand = "!nowplaying";
    String shortCommandDescription = "Get the current playing song";
    String fullCommandDescription = "Get the current playing song";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        PlayerManager manager = PlayerManager.getInstance();
        GuildMusicManager musicManager = manager.getGuildMusicManager(e.getGuild());

        AudioTrack playingTrack = musicManager.player.getPlayingTrack();

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Now playing");
        eb.appendDescription("*Currently playing*\n");
        eb.appendDescription("**" + playingTrack.getInfo().author + "**\n");
        eb.appendDescription("[" + playingTrack.getInfo().title + "](" + playingTrack.getInfo().uri + ")\n");
        eb.appendDescription(videoDurationToYoutube(playingTrack.getPosition()) + " / " + videoDurationToYoutube(playingTrack.getDuration()) + "\n");
        eb.setColor(colors.getCurrentColor());

        e.getChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getCommandAlias() {
        return commandAlias;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getExampleCommand() {
        return exampleCommand;
    }

    @Override
    public String getShortCommandDescription() {
        return shortCommandDescription;
    }

    @Override
    public String getFullCommandDescription() {
        return fullCommandDescription;
    }
}
