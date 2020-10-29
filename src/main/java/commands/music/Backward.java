package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import static functions.AllowedToPlayMusic.allowedToPlayMusic;

public class Backward implements ICommand {
    CommandReceivedEvent e;

    String command = "backward";
    String commandAlias = "backwards";
    String category = "music";
    String exampleCommand = "`!backward (seconds)`";
    String shortCommandDescription = "Backward the current playing song a amount of seconds.";
    String fullCommandDescription = "Backward the current playing song a amount of seconds.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, "backward")) return;


        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument")).queue();
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.backwardsTrack(e.getGuild(), Long.parseLong(e.getArgs()[0]));
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
