package commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static music.TrackScheduler.videoDurationToYoutube;
import static utilities.AllowedToPlayMusic.allowedToPlayMusic;

public class SkipTo implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("skipto", "skiptonumber"));
    String category = "music";
    String exampleCommand = "skipto (index song)";
    String shortCommandDescription = "Skip to a certain position in the queue";
    String fullCommandDescription = "Skip to a certain position in the queue";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getFullHelp("Requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
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

        e.getChannel().sendMessage(eb.build()).queue();

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

    @Override
    public ArrayList<String> getCommandAliases() {
        return commandAliases;
    }
}
