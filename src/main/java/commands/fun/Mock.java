package commands.fun;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.Permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

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
            if (e.isFromGuild() && e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                e.getMessage().delete().complete();
            }

            e.getMessageChannel().sendMessage(stringToMocked(e.getMessageWithoutCommand())).queue();
        } else {
            e.getMessageChannel().sendMessage(getFullHelp("Requires a argument!", e.getPrefix())).queue();
        }
    }

    private String stringToMocked(String toMock) {
        StringBuilder output = new StringBuilder();

        AtomicBoolean toUpper = new AtomicBoolean(r.nextBoolean());

        toMock.chars()
                .mapToObj(i -> (char) i)
                .forEach(currentChar -> {
                    if (!Character.isLetter(currentChar)) {
                        output.append(currentChar);
                    } else if (toUpper.get()) {
                        output.append(Character.toUpperCase(currentChar));
                        toUpper.set(false);
                    } else {
                        output.append(currentChar);
                        toUpper.set(true);
                    }
                });

        return output.toString();
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
