package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Shuffle implements ICommand {
    CommandReceivedEvent e;

    String command = "shuffle";
    String commandAlias = "shuffle";
    String category = "music";
    String exampleCommand = "`!shuffle`";
    String shortCommandDescription = "Shuffle the queue.";
    String fullCommandDescription = "Shuffle the queue.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, "shuffle")) return;

        PlayerManager manager = PlayerManager.getInstance();
        manager.shuffleQueue(e.getGuild());

        e.getMessageChannel().sendMessage("Queue has been shuffeled").queue();
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
