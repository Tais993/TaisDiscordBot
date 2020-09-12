package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class Help implements ICommand{
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    String command = "help";
    String category = "util";
    String exampleCommand = "!help <item/category>";
    String shortCommandDescription = "Get some help";
    String fullCommandDescription = "Get help for a item or category, but you already know that. Right?";

    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        if (args.length > 0) {
            switch (args[1]) {
                case "fun":
                    e.getChannel().sendMessage(commandEnum.getHelpCategory("fun").build()).queue();
                    break;
                case "util":
                    e.getChannel().sendMessage(commandEnum.getHelpCategory("util").build()).queue();
                    break;
                default:
                    e.getChannel().sendMessage(commandEnum.getFullHelpItem(args[1]).build()).queue();
            }
        } else {
            helpAll();
        }
    }

    public void helpAll() {
    }

    public String getCommand() {
        return command;
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
