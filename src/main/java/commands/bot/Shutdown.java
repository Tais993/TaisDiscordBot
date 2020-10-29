package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;

public class Shutdown implements ICommand {
    String command = "shutdown";
    String commandAlias = "sd";
    String category = "general";
    String exampleCommand = "`!shutdown`";
    String shortCommandDescription = "Stop the bot";
    String fullCommandDescription = "Stop the bot";

    @Override
    public void command(CommandReceivedEvent event) {
        if (event.isBotModerator()) {
            System.exit(1);
        } else {
            event.getMessageChannel().sendMessage(getFullHelp("Imagine not being bot moderator, kinda cringe ngl.")).queue();
        }
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
