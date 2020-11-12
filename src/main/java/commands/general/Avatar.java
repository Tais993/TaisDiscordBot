package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import static util.Colors.getCurrentColor;

public class Avatar implements ICommand {
    CommandReceivedEvent e;

    User user;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("avatar"));
    String category = "general";
    String exampleCommand = "avatar (user Id)/(user as mention)";
    String shortCommandDescription = "Get someone's avatar";
    String fullCommandDescription = "Get someone's avatar";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(getCurrentColor());
        eb.setTimestamp(Instant.now());

        if (!e.hasArgs()) {
            user = e.getAuthor();
        } else {
            user = e.getFirstArgAsUser();
        }

        eb.setTitle("Avatar of: " + user.getAsTag());
        eb.setImage(user.getAvatarUrl());

        e.getMessageChannel().sendMessage(eb.build()).queue();
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
