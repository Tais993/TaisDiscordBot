package commands.music;

import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Remove implements ICommand {
    GuildMessageReceivedEvent e;

    String command = "remove";
    String commandAlias = "remove";
    String category = "music";
    String exampleCommand = "`!remove <index>`";
    String shortCommandDescription = "Removes song from queue.";
    String fullCommandDescription = "Removes song from queue.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        PlayerManager manager = PlayerManager.getInstance();

        EmbedBuilder eb = manager.removeFromQueue(e, 1);

        e.getChannel().sendMessage(eb.build()).queue();
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
