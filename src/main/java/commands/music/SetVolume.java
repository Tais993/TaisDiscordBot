package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class SetVolume implements ICommand {
    CommandReceivedEvent e;

    String command = "setvolume";
    String commandAlias = "volume";
    String category = "music";
    String exampleCommand = "`!setvolume (volume)`";
    String shortCommandDescription = "Set the volume of the bot. Ranged between 1 and 200.";
    String fullCommandDescription = "Set the volume of the bot. Ranged between 1 and 200.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        PlayerManager manager = PlayerManager.getInstance();

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage("Volume has been set to " + manager.getVolume(e.getGuild())).queue();
            return;
        }

        int volume;

        try {
            volume = Integer.parseInt(e.getArgs()[0]);
        } catch (NumberFormatException exception) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: give a valid number.")).queue();
            return;
        }

        if (!(volume >= 1 && volume <= 200)) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: give a number between 1 and 200.")).queue();
            return;
        }

        if (!allowedToPlayMusic(e, "setvolume")) return;

        if (manager.setVolume(e.getGuild(), volume)) {
            e.getMessageChannel().sendMessage("Volume has been set correctly to " + volume).queue();
        } else {
            e.getMessageChannel().sendMessage("Error unknown.").queue();
        }
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
