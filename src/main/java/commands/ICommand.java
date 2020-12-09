package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.Instant;
import java.util.ArrayList;

import static commands.CommandEnum.bot;
import static utilities.Colors.getCurrentColor;

public interface ICommand {
    CommandReceivedEvent e = null;
    ArrayList<String> commandAliases = new ArrayList<>();
    String category = "";
    String exampleCommand = "";
    String shortCommandDescription = "";
    String fullCommandDescription = "";

    void command(CommandReceivedEvent event);

    default EmbedBuilder getEmbed() {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(getCurrentColor());
        eb.setAuthor("Tais", "https://github.com/Tais993/TaisDiscordBot", bot.getAvatarUrl());
        eb.setTimestamp(Instant.now());

        return eb;
    }

    default MessageEmbed getFullHelp(String error, String prefix) {
        if (error.isEmpty()) {
            return CommandMap.getFullHelpItem(getCommandAliases().get(0), prefix).build();
        }
        return CommandMap.getFullHelpItem(getCommandAliases().get(0), prefix).setDescription(error).build();
    }

    default MessageEmbed getShortHelp(String error, String prefix) {
        if (error.isEmpty()) {
            return CommandMap.getShortHelpItem(getCommandAliases().get(0), prefix).build();
        }
        return CommandMap.getShortHelpItem(getCommandAliases().get(0), prefix).setDescription("**" + error + "**").build();
    }

    String getCategory();

    String getExampleCommand();

    String getShortCommandDescription();

    String getFullCommandDescription();

    ArrayList<String> getCommandAliases();
}
