package commands.fun;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.Permission;
import util.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Mock implements ICommand {
    Random r = new Random();

    CommandReceivedEvent e;
    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("mock", "m"));
    String category = "fun";
    String exampleCommand = "mock <text>";
    String shortCommandDescription = "Mocks the text";
    String fullCommandDescription = "Give some input, the text will then be converted to a mocked text.\n" +
            " example: `!mock testing` output: `tEsTiNg`";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (e.mentionsEveryone()) {
            e.getMessageChannel().sendMessage(getFullHelp("Don't mention people! Not nice >.<", e.getPrefix())).queue();
            return;
        }

        if (e.hasArgs()) {
            String toMock = e.getMessageWithoutCommand();
            StringBuilder output = new StringBuilder();

            int numberCount = r.nextInt(2);

            for (int i = 0; i < toMock.length(); i++) {
                String currentChar = toMock.charAt(i) + "";
                if (!(currentChar.equals(" "))) {
                    if (numberCount == 0) {
                        output.append(currentChar.toLowerCase());
                        numberCount++;
                    } else {
                        output.append(currentChar.toUpperCase());
                        numberCount = 0;
                    }
                } else {
                    output.append(" ");
                }
            }

            if (e.isFromGuild()) {
                removeMessage();
            }

            e.getMessageChannel().sendMessage(output.toString()).queue();
        } else {
            e.getMessageChannel().sendMessage(getFullHelp("Requires a argument!", e.getPrefix())).queue();
        }
    }

    public void removeMessage() {
        Permissions permissions = new Permissions(e.getGuild());
        if (permissions.botHasPermission(Permission.MESSAGE_MANAGE)){
            e.getMessage().delete().complete();
        }
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
