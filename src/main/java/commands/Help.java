package commands;

import java.util.ArrayList;
import java.util.Collections;

public class Help implements ICommand{
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("help"));
    String category = "utilities";
    String exampleCommand = "help <item/category>";
    String shortCommandDescription = "Get some help";
    String fullCommandDescription = "Get help for a item or category, but you already know that. Right?";

    public void command(CommandReceivedEvent event) {
        e = event;

        if (e.hasArgs) {
            String[] args = e.getArgs();
            switch (args[0]) {
                case "fun", "general", "utilities", "music", "botmoderation" -> commandEnum.getHelpCategory(args[0], e);
                default -> {
                    if (commandEnum.checkOrValidCommand(args[0], e.isBotModerator())) {
                        e.getChannel().sendMessage(commandEnum.getFullHelpItem(args[0], e.getPrefix()).build()).queue();
                    }
                }
            }
        } else {
            commandEnum.getHelpAllByCategory(e);
        }
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
