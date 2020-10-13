package commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import functions.Colors;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import static music.TrackScheduler.videoDurationToYoutube;

public class SkipTo implements ICommand {
    CommandReceivedEvent e;
    Colors colors = new Colors();
    CommandEnum commandEnum = new CommandEnum();

    String command = "skipto";
    String commandAlias = "skipto";
    String category = "music";
    String exampleCommand = "`!skipto (index song)`";
    String shortCommandDescription = "Skip to a certain position in the queue";
    String fullCommandDescription = "Skip to a certain position in the queue";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument")).queue();
        }

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "skipto")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.skipToTrack(e.getGuild(), Integer.parseInt(e.getArgs()[0]));

        AudioTrack track = manager.getGuildMusicManager(e.getGuild()).player.getPlayingTrack();

        EmbedBuilder eb = new EmbedBuilder(getEmbed());
        eb.setTitle("Queue has been skipped to:");
        eb.appendDescription("*Currently playing*\n");
        eb.appendDescription("**" + track.getInfo().author + "**\n");
        eb.appendDescription("[" + track.getInfo().title + "](" + track.getInfo().uri + ")\n");
        eb.appendDescription(videoDurationToYoutube(track.getPosition()) + " / " + videoDurationToYoutube(track.getDuration()) + "\n");

        e.getMessageChannel().sendMessage(eb.build()).queue();

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
