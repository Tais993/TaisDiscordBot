package Commands.Fun;

import Commands.CommandEnum;
import Commands.ICommand;
import Functions.Permissions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class Mock implements ICommand {
    CommandEnum commandEnum = new CommandEnum();
    Random r = new Random();
    Permissions permissions = new Permissions();

    GuildMessageReceivedEvent e;
    String command = "mock";
    String commandAlias = "m";
    String category = "fun";
    String exampleCommand = "`!mock <text>`";
    String shortCommandDescription = "Mocks the text";
    String fullCommandDescription = "Give some input, the text will then be converted to a mocked text.\n" +
            " example: `!mock testing` output: `tEsTiNg`";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        if (args.length > 1) {
            String toMock = e.getMessage().getContentRaw().replaceFirst("!mock ", "");
            String output = "";

            int numberCount = r.nextInt(1);

            for (int i = 0; i < toMock.length(); i++) {
                String currentChar = toMock.charAt(i) + "";
                if (!(currentChar.equals(" "))) {
                    if (numberCount == 0) {
                        output = output + currentChar.toLowerCase();
                        numberCount++;
                    } else {
                        output = output + currentChar.toUpperCase();
                        numberCount = 0;
                    }
                } else {
                    output = output + " ";
                }
            }

            if (permissions.botHasPermission(e.getGuild(), Permission.MESSAGE_MANAGE)){
                e.getMessage().delete().complete();
                e.getChannel().sendMessage(output).queue();
            } else {
                e.getChannel().sendMessage(output).queue();
            }
        } else {
            event.getChannel().sendMessage(commandEnum.getFullHelpItem("mock").build()).queue();
        }
    }

    public String removeCommand() {
        String input = e.getMessage().getContentRaw();
        if (input.contains(getCommand())) {
            input = input.replaceAll("!mock ", "");
        } else {
            input = input.replaceFirst("!m ", "");
        }

        return command;
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
