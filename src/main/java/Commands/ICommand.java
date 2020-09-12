package Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface ICommand {
    GuildMessageReceivedEvent e = null;
    String command = "";
    String category = "";
    String exampleCommand = "";
    String shortCommandDescription = "";
    String fullCommandDescription = "";

    void command(GuildMessageReceivedEvent event, String[] args);

    String getCommand();

    String getCategory();

    String getExampleCommand();

    String getShortCommandDescription();

    String getFullCommandDescription();
}
