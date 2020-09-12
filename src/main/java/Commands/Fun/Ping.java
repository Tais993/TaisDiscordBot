package Commands.Fun;

import Commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Ping implements ICommand {

    GuildMessageReceivedEvent e;
    String command = "ping";
    String category = "fun";
    String exampleCommand = "`!ping`";
    String shortCommandDescription = "Get a real ping";
    String fullCommandDescription = "Not much to say, just a command that gives your totally not fake ping.";

    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        int ping = 1;

        e.getChannel().sendMessage("Ping? " + ping + "ms").queue();
    }

    public String getCommand() {
        return command;
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
