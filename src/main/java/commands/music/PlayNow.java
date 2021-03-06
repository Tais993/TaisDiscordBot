package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import static music.youtube.SearchYouTube.getVideoUrl;
import static utilities.AllowedToPlayMusic.allowedToPlayMusic;

public class PlayNow implements ICommand {
    CommandReceivedEvent e;

    String url;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("playnow"));
    String category = "music";
    String exampleCommand = "playnow (url/song title)";
    String shortCommandDescription = "Instantly play a song.";
    String fullCommandDescription = "Instantly play a song, the song gets added to the top of the queue.\n" +
            "And the current playing song gets skipped.";


    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getFullHelp("Error: requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        String input = e.getMessageWithoutCommand();

        if (!isUrl(input)) {
            if (!searchByName(input)) return;
        } else {
            url = input;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(e.getTextChannel(), url, true, e.getAuthor().getId(), e.getAuthor().getAsTag(), true);
        manager.skip(e.getGuild());
    }

    private boolean isUrl(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException malformedURLException) {
            return false;
        }
    }

    private boolean searchByName(String input) {
        String videoUrl = getVideoUrl(input);

        if (videoUrl.startsWith("Error:")) {
            e.getChannel().sendMessage("Unknown error: "+ videoUrl).queue();
            return false;
        }

        url = videoUrl;
        return true;
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
