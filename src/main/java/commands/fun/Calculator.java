package commands.fun;

import commands.CommandReceivedEvent;
import commands.ICommand;
import commands.fun.calculator.Node;

import java.util.ArrayList;
import java.util.Arrays;

public class Calculator implements ICommand {
    CommandReceivedEvent e;
    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("calculate", "calculator"));
    String category = "fun";
    String exampleCommand = "calculate X operator Y";
    String shortCommandDescription = "Calculate!";
    String fullCommandDescription = "Calculate!";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs() && e.getArgs().length >= 3) {
            e.getMessageChannel().sendMessage(getShortHelp("Requires at least 3 arguments", e.getPrefix())).queue();
            return;
        }

        String input = e.getMessageWithoutCommand().replaceAll(" ", "");

        Node node = new Node();

        node.constructTree(input);
        node.inorder();

        e.getMessageChannel().sendMessage(node.calculate() + "").queue();
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
