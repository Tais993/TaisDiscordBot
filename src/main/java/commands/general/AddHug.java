package commands.general;

import commands.CommandEnum;
import commands.ICommand;
import database.hugs.DatabaseHugs;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class AddHug implements ICommand {
    DatabaseHugs databaseHugs = new DatabaseHugs();
    CommandEnum commandEnum = new CommandEnum();

    GuildMessageReceivedEvent e;
    String command = "addhug";
    String commandAlias = "addhug";
    String category = "fun";
    String exampleCommand = "`!addhug <gifurl>`";
    String shortCommandDescription = "Add gifs";
    String fullCommandDescription = "Add gifs to the current list";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        if (args.length > 2){
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("addhug").setDescription("Error: requires a URL for a gif").build()).queue();
            return;
        }

        String gifUrlToAdd = args[1];

        databaseHugs.addGifToDB(gifUrlToAdd);

        e.getChannel().sendMessage("Gif has been added").queue();
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
