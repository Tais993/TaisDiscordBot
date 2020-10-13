package commands;

public class Help implements ICommand{
    CommandReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    String command = "help";
    String commandAlias = "help";
    String category = "util";
    String exampleCommand = "!help <item/category>";
    String shortCommandDescription = "Get some help";
    String fullCommandDescription = "Get help for a item or category, but you already know that. Right?";

    public void command(CommandReceivedEvent event) {
        e = event;

        if (e.hasArgs) {
            String[] args = e.getArgs();
            switch (args[0]) {
                case "fun":
                case "util":
                case "general":
                case "music":
                    commandEnum.getHelpCategory(args[0], e);
                    break;
                default:
                    if (commandEnum.checkOrValidCommand(args[0])){
                        e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem(args[0]).build()).queue();
                    }
            }
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
