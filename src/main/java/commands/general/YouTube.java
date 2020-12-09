package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;

import java.util.ArrayList;
import java.util.Collections;

import static music.youtube.SearchYouTube.getVideoUrl;

public class YouTube implements ICommand {
    CommandReceivedEvent e;

    String url;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("youtube"));
    String category = "general";
    String exampleCommand = "youtube <gifurl>";
    String shortCommandDescription = "Search for a YouTube video.";
    String fullCommandDescription = "Search for a YouTube video.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (e.mentionsEveryone()) {
            e.getChannel().sendMessage("Don't mention everyone! Not nice >.<").queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getFullHelp("Requires something to search, I can't search nothing.", e.getPrefix())).queue();
            return;
        }

        String input = e.getMessageWithoutCommand();

        String videoUrl = getVideoUrl(input);

        if (videoUrl.startsWith("Error:")) {
            e.getChannel().sendMessage(getFullHelp(videoUrl, e.getPrefix())).queue();
            return;
        }

        url = videoUrl;

        if (url.isEmpty()) {
            e.getChannel().sendMessage("Nothing has been found by " + input).queue();
        } else {
            e.getChannel().sendMessage(url).queue();
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
