package commands.fun;

import commands.CommandReceivedEvent;
import commands.ICommand;

import java.util.ArrayList;
import java.util.Arrays;

public class Ping implements ICommand {
    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("ping", "pong"));
    String category = "fun";
    String exampleCommand = "ping";
    String shortCommandDescription = "Get a real ping";
    String fullCommandDescription = "Not much to say, just a command that gives your totally not fake ping.";

    public void command(CommandReceivedEvent event) {
        event.getMessageChannel().sendMessage("Ping: " + event.getMember().getJDA().getGatewayPing() + "ms").queue();
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
