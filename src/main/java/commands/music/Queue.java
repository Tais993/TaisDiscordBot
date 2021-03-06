package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class Queue implements ICommand {

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("queue", "q"));
    String category = "music";
    String exampleCommand = "queue";
    String shortCommandDescription = "Get the queue of the music.";
    String fullCommandDescription = "Get the queue of the music.";

    @Override
    public void command(CommandReceivedEvent event) {
        PlayerManager manager = PlayerManager.getInstance();

        EmbedBuilder eb = manager.getQueue(event.getGuild(), getEmbed());

        event.getChannel().sendMessage(eb.build()).queue();
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
