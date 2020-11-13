package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import music.youtube.SearchYouTube;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Play implements ICommand {
    CommandReceivedEvent e;

    String url;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("play", "p"));
    String category = "music";
    String exampleCommand = "play <URL>";
    String shortCommandDescription = "Plays music.";
    String fullCommandDescription = "Plays music.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        if (!allowedToPlayMusic(e, "play")) return;

        String input = e.getMessageWithoutCommand();

        if (!isUrl(input)) {
            if (!searchByName(input)) return;
        } else {
            url = input;
        }

        if (url.isEmpty()) {
            e.getMessageChannel().sendMessage("Nothing has been found by " + input).queue();
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.loadAndPlay(e.getTextChannel(), url, false, e.getAuthor().getId(), e.getAuthor().getAsTag(), true);
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
        SearchYouTube searchYouTube = new SearchYouTube();

        String videoUrl = searchYouTube.getVideoUrl(input);

        if (videoUrl.startsWith("Error:")) {
            e.getMessageChannel().sendMessage("Unknown error: " + videoUrl).queue();
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
