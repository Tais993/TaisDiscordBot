package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class SetVolume implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("setvolume", "volume"));
    String category = "music";
    String exampleCommand = "setvolume (volume)";
    String shortCommandDescription = "Set the volume of the bot. Ranged between 1 and 200.";
    String fullCommandDescription = "Set the volume of the bot. Ranged between 1 and 200.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        PlayerManager manager = PlayerManager.getInstance();

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        if (!e.hasArgs()) {
            e.getChannel().sendMessage("Volume has been set to " + manager.getVolume(e.getGuild())).queue();
            return;
        }

        int volume;

        try {
            volume = Integer.parseInt(e.getArgs()[0]);
        } catch (NumberFormatException exception) {
            e.getChannel().sendMessage(getFullHelp("Error: give a valid number.", e.getPrefix())).queue();
            return;
        }

        if (!(volume >= 1 && volume <= 200)) {
            e.getChannel().sendMessage(getFullHelp("Error: give a number between 1 and 200.", e.getPrefix())).queue();
            return;
        }

        if (manager.setVolume(e.getGuild(), volume)) {
            e.getChannel().sendMessage("Volume has been set correctly to " + volume).queue();
        } else {
            e.getChannel().sendMessage("Error unknown.").queue();
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
