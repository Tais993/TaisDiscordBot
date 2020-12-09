package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;

import static music.youtube.SearchYouTube.getVideoUrl;
import static utilities.AllowedToPlayMusic.allowedToPlayMusic;
import static utilities.Utils.isUrl;

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

        if (url.isEmpty()) {
            e.getChannel().sendMessage("Nothing has been found by " + input).queue();
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.loadAndPlay(e.getTextChannel(), url, false, e.getAuthor().getId(), e.getAuthor().getAsTag(), true);
    }

    private boolean searchByName(String input) {
        String videoUrl = getVideoUrl(input);

        if (videoUrl.startsWith("Error:")) {
            e.getChannel().sendMessage("Unknown error: " + videoUrl).queue();
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
