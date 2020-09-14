package Commands.Fun;

import Commands.CommandEnum;
import Commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class EightBall implements ICommand {
    CommandEnum commandEnum = new CommandEnum();
    Random r = new Random();

    GuildMessageReceivedEvent e;
    String category = "fun";
    String command = "8ball";
    String commandAlias = "eightball";
    String exampleCommand = "`!8ball <question>?`";
    String shortCommandDescription = "Ask 8ball a question";
    String fullCommandDescription = "Ask 8ball a question, and get a totally real answer.\n" +
            "Question mark is required.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        if (event.getMessage().getContentRaw().contains("?")) {
            int chance = Math.round(r.nextFloat()*10);

            switch (chance) {
                case 1: case 2:
                    event.getChannel().sendMessage("For sure!").queue();
                    break;
                case 3: case 4:
                    event.getChannel().sendMessage("Yes.").queue();
                    break;
                case 5: case 6:
                    event.getChannel().sendMessage("Maybe").queue();
                    break;
                case 7: case 8:
                    event.getChannel().sendMessage("No.").queue();
                    break;
                case 9: case 10:
                    event.getChannel().sendMessage("Never...").queue();
                    break;
            }
        } else {
            event.getChannel().sendMessage(commandEnum.getFullHelpItem("8ball").build()).queue();
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
