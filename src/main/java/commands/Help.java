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

    public void command(CommandReceivedEvent event, String[] args) {
        e = event;
        if (args.length > 1) {
            switch (args[1]) {
                case "fun":
                    e.getMessageChannel().sendMessage(commandEnum.getHelpCategory("fun").build()).queue();
                    break;
                case "util":
                    e.getMessageChannel().sendMessage(commandEnum.getHelpCategory("util").build()).queue();
                    break;
                case "general":
                    e.getMessageChannel().sendMessage(commandEnum.getHelpCategory("general").build()).queue();
                    break;
                case "music":
                    e.getMessageChannel().sendMessage(commandEnum.getHelpCategory("music").build()).queue();
                    break;
                default:
                    if (commandEnum.checkOrValidCommand(event, args)){
                        e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem(args[1]).build()).queue();
                    }
            }
        } else {
            commandEnum.getHelpAllByCategory(e);
        }
    }

    public void helpAll() {
        e.getMessageChannel().sendMessage(commandEnum.getHelpAll().build()).queue();
        commandEnum.getHelpAll();
    }

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
