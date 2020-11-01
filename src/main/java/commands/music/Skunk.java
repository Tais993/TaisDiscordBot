package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Skunk implements ICommand {
    CommandReceivedEvent e;

    String url = "https://www.youtube.com/playlist?list=PL7tOllzEIb0Eaej-wj-KqGSerKXb1t-Dy";

    String command = "skunk";
    String commandAlias = "skunk";
    String category = "music";
    String exampleCommand = "`!skunk`";
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
