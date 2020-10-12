package commands;

public interface ICommand {
    CommandReceivedEvent e = null;
    String command = "";
    String commandAlias = "";
    String category = "";
    String exampleCommand = "";
    String shortCommandDescription = "";
    String fullCommandDescription = "";

    void command(CommandReceivedEvent event, String[] args);

    String getCommand();

    String getCommandAlias();

    String getCategory();

    String getExampleCommand();

    String getShortCommandDescription();

    String getFullCommandDescription();
}
