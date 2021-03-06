package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;

import java.util.ArrayList;
import java.util.Collections;

public class Test implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("test"));
    String category = "general";
    String exampleCommand = "test";
    String shortCommandDescription = "For testing purposes.";
    String fullCommandDescription = "For testing purposes.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
    }

    public int decrement(int number) {
        return --number;
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
