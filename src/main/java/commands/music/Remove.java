package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Remove implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("remove"));
    String category = "music";
    String exampleCommand = "remove <index>";
    String shortCommandDescription = "Removes song from queue.";
    String fullCommandDescription = "Removes song from queue.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        String[] args = e.getArgs();

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        EmbedBuilder eb = manager.removeFromQueue(e.getGuild(), Integer.parseInt(args[0]));

        e.getMessageChannel().sendMessage(eb.build()).queue();
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
