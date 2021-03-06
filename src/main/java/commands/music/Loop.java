package commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.CommandReceivedEvent;
import commands.ICommand;
import music.GuildMusicManager;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;

import static utilities.AllowedToPlayMusic.allowedToPlayMusic;

public class Loop implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("loop"));
    String category = "music";
    String exampleCommand = "loop all/1";
    String shortCommandDescription = "Loop the current queue / current song.";
    String fullCommandDescription = """
            Loop the current queue / current song.
            `!loop all` to loop the current queue.\s
            `!loop 1` to loop the current playing song.
            Run the command again to toggle it off, or use `!loop off`""";

    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getFullHelp("Requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        switch (e.getArgs()[0]) {
            case "1", "song" -> {
                manager.loopSongToggle(e.getGuild());
                GuildMusicManager musicManager = manager.getGuildMusicManager(e.getGuild());
                AudioTrack playingTrack = musicManager.player.getPlayingTrack();
                e.getChannel().sendMessage("Succesfully looping: " + playingTrack.getInfo().uri).queue();
            }
            case "all", "queue" -> {
                manager.loopQueueToggle(e.getGuild());
                e.getChannel().sendMessage("Succesfully looping the queue").queue();
            }
            case "off" -> manager.loopOff(e.getGuild());
            default -> e.getChannel().sendMessage(getFullHelp("Error: either give 1 or all as input", e.getPrefix())).queue();
        }
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
