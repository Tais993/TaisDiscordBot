package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.hugs.DatabaseHugs;

public class AddHug implements ICommand {
    DatabaseHugs databaseHugs = new DatabaseHugs();

    CommandReceivedEvent e;
    String command = "addhug";
    String commandAlias = "addhug";
    String category = "fun";
    String exampleCommand = "`!addhug <gifurl>`";
    String shortCommandDescription = "Add gifs";
    String fullCommandDescription = "Add gifs to the current list";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()){
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("addhug").setDescription("Error: requires a URL for a gif").build()).queue();
            return;
        }

        String gifUrlToAdd = e.getArgs()[0];

        databaseHugs.addGifToDB(gifUrlToAdd);

        e.getMessageChannel().sendMessage("Gif has been added").queue();
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
