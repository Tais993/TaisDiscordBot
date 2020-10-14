package commands.music;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import database.guild.DatabaseGuild;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import music.youtube.SearchYouTube;

import java.net.MalformedURLException;
import java.net.URL;

public class Play implements ICommand {
    CommandReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    DatabaseGuild databaseGuild = new DatabaseGuild();

    String url;

    String command = "play";
    String commandAlias = "p";
    String category = "music";
    String exampleCommand = "`!play <URL>`";
    String shortCommandDescription = "Plays music.";
    String fullCommandDescription = "Plays music.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: requires at least 1 argument")).queue();
            return;
        }

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "play")) {
            return;
        }

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
        manager.loadAndPlay(e.getTextChannel(), url, false, e.getAuthor().getId(), e.getAuthor().getAsTag());
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
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("play").setDescription(videoUrl).build()).queue();
            return false;
        }

        url = videoUrl;
        return true;
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
