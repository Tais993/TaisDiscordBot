package commands.util.ban;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PermBan implements ICommand {
    GuildMessageReceivedEvent e;
    String command = "permban";
    String commandAlias = "permban";
    String category = "util";
    String exampleCommand = "`!8ball <question>?`";
    String shortCommandDescription = "Ask 8ball a question";
    String fullCommandDescription = "Ask 8ball a question, and get a totally real answer.\n" +
            "Question mark is required.";

    @Override
    public void command(CommandReceivedEvent event, String[] args) {

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
