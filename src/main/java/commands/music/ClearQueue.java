package commands.music;

import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ClearQueue implements ICommand {
    GuildMessageReceivedEvent e;

    String command = "clearqueue";
    String commandAlias = "removeall";
    String category = "music";
    String exampleCommand = "`!clearqueue`";
    String shortCommandDescription = "Queue gets cleared.";
    String fullCommandDescription = "Queue gets cleared";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        PlayerManager manager = PlayerManager.getInstance();
        manager.clearQueue(e);

        e.getChannel().sendMessage("Queue has been cleared.").queue();
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
