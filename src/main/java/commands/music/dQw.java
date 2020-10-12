package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.guild.DatabaseGuild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class dQw implements ICommand {
    String command = "dQw";
    String commandAlias = "dQw";
    String category = "music";
    String exampleCommand = "`!dQw`";
    String shortCommandDescription = "HAHAHAHAHA IMAGINE GETTING RICKROLLED LOL.";
    String fullCommandDescription = "No hate ly";

    @Override
    public void command(CommandReceivedEvent event, String[] args) {
        if (args[0].equals("dQw")) {
            RickRoll rickRoll = new RickRoll();
            rickRoll.command(event, args);
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
