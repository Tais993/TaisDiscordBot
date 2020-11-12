package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Skunk implements ICommand {
    CommandReceivedEvent e;

    String url = "https://www.youtube.com/playlist?list=PL7tOllzEIb0Eaej-wj-KqGSerKXb1t-Dy";

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("skunk", "skunkseconds"));
    String category = "music";
    String exampleCommand = "skunk";
    String shortCommandDescription = "Skunk.";
    String fullCommandDescription = "Skunk, and skunk.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, "skunk")) return;

        e.getMessageChannel().sendMessage("WELCOME TO THE SKUNK SECONDS!").queue();

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(e.getTextChannel(), url, false, e.getAuthor().getId(), e.getAuthor().getAsTag());
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
