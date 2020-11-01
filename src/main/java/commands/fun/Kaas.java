package commands.fun;

import commands.CommandReceivedEvent;
import commands.ICommand;

import java.util.ArrayList;
import java.util.Arrays;

public class Kaas implements ICommand {
    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("cheese", "kaas"));
    String category = "fun";
    String exampleCommand = "cheese";
    String shortCommandDescription = "Kaas, otherwise known as cheese";
    String fullCommandDescription = "Kaas";
    @Override
    public void command(CommandReceivedEvent event) {
        event.getMessageChannel().sendMessage("kaas is lekker").queue();
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
