package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;

import java.util.ArrayList;
import java.util.Arrays;

public class Shutdown implements ICommand {
    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("shutdown", "sd"));
    String category = "botmoderation";
    String exampleCommand = "shutdown";
    String shortCommandDescription = "Stop the bot";
    String fullCommandDescription = "Stop the bot";

    @Override
    public void command(CommandReceivedEvent event) {
        System.exit(1);
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
