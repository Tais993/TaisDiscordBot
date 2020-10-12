package commands.fun;

import commands.CommandReceivedEvent;
import commands.ICommand;

public class Kaas implements ICommand {
    String command = "cheese";
    String commandAlias = "kaas";
    String category = "fun";
    String exampleCommand = "`!cheese`";
    String shortCommandDescription = "Kaas, otherwise known as cheese";
    String fullCommandDescription = "Kaas";
    @Override
    public void command(CommandReceivedEvent event, String[] args) {
        event.getMessageChannel().sendMessage("kaas is lekker").queue();
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
