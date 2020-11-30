package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.hugs.DatabaseHugs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AddHug implements ICommand {
    DatabaseHugs databaseHugs = new DatabaseHugs();
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("addhug"));
    String category = "fun";
    String exampleCommand = "addhug <gifurl>";
    String shortCommandDescription = "Add gifs";
    String fullCommandDescription = "Add gifs to the current list";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()){
            e.getChannel().sendMessage(getFullHelp("Error: requires a URL for a gif", e.getPrefix())).queue();
            return;
        }

        if (e.mentionsEveryone()) {
            e.getChannel().sendMessage(getFullHelp("Don't mention everyone! Not nice >.<", e.getPrefix())).queue();
            return;
        }

        String gifUrlToAdd = e.getArgs()[0];

        databaseHugs.addGifToDB(gifUrlToAdd);

        e.getChannel().sendMessage("Gif has been added").queue();
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
