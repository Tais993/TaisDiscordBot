package commands.fun;

import commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Kaas implements ICommand {

    GuildMessageReceivedEvent e;
    String command = "cheese";
    String commandAlias = "kaas";
    String category = "fun";
    String exampleCommand = "`!cheese`";
    String shortCommandDescription = "Kaas, otherwise known as cheese";
    String fullCommandDescription = "Kaas";
    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        e.getChannel().sendMessage("kaas is lekker").queue();
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
