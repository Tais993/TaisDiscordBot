package commands.music;

import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Backward implements ICommand {
    GuildMessageReceivedEvent e;

    String command = "backward";
    String commandAlias = "backwards";
    String category = "music";
    String exampleCommand = "`!backward (seconds)`";
    String shortCommandDescription = "Backward the current playing song a amount of seconds.";
    String fullCommandDescription = "Backward the current playing song a amount of seconds.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        PlayerManager manager = PlayerManager.getInstance();
        manager.backwardsTrack(e, Long.parseLong(args[1]));
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
