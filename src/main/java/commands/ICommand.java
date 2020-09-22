package commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface ICommand {
    GuildMessageReceivedEvent e = null;
    String command = "";
    String commandAlias = "";
    String category = "";
    String exampleCommand = "";
    String shortCommandDescription = "";
    String fullCommandDescription = "";

    void command(GuildMessageReceivedEvent event, String[] args);

    String getCommand();

    String getCommandAlias();

    String getCategory();

    String getExampleCommand();

    String getShortCommandDescription();

    String getFullCommandDescription();
}
