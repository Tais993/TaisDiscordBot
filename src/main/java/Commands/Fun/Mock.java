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
            Permissions permissions = new Permissions(e.getGuild());
            String toMock = e.getMessage().getContentRaw().replaceFirst("!mock ", "");
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

            if (permissions.botHasPermission(Permission.MESSAGE_MANAGE)){
                e.getMessage().delete().complete();
                e.getChannel().sendMessage(output.toString()).queue();
            } else {
                e.getChannel().sendMessage(output.toString()).queue();
            }
        } else {
            event.getChannel().sendMessage(commandEnum.getFullHelpItem("mock").build()).queue();
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
