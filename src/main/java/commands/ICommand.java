package commands;

import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.Instant;

import static commands.CommandEnum.bot;

public interface ICommand {
    CommandReceivedEvent e = null;
    CommandEnum commandEnum = new CommandEnum();
    String command = "";
    String commandAlias = "";
    String category = "";
    String exampleCommand = "";
    String shortCommandDescription = "";
    String fullCommandDescription = "";

    void command(CommandReceivedEvent event);

    default EmbedBuilder getEmbed() {
        EmbedBuilder eb = new EmbedBuilder();
        Colors colors = new Colors();

        eb.setColor(colors.getCurrentColor());
        eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
        eb.setFooter("Made by Tijs ");
        eb.setTimestamp(Instant.now());

        return eb;
    }

    default MessageEmbed getFullHelp(String error) {
        if (error.isEmpty()) {
            return commandEnum.getFullHelpItem(getCommand()).build();
        }
        return commandEnum.getFullHelpItem(getCommand()).setDescription(error).build();
    }

    String getCommand();

    String getCommandAlias();

    String getCategory();

    String getExampleCommand();

    String getShortCommandDescription();

    String getFullCommandDescription();
}
