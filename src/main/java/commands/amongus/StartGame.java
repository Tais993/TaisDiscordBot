package commands.amongus;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.reactions.DatabaseReactions;
import database.reactions.ReactionDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;
import java.util.Arrays;

public class StartGame implements ICommand {
    CommandReceivedEvent e;
    DatabaseReactions databaseReactions = new DatabaseReactions();

    String time;
    Role role;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("startgame", "startamongusgame"));
    String category = "fun";
    String exampleCommand = "startgame <time>";
    String shortCommandDescription = "Start a game of among us";
    String fullCommandDescription = "Start a game of among us, set amount of imposters, and more.\n" +
            "You can see how many people will join, and what their name is.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (e.mentionsEveryone()) {
            e.getMessageChannel().sendMessage("Don't mention everyone! Not nice >.<").queue();
            return;
        }

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("Command only works in a Discord guild/server").queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: Requires a time", e.getPrefix())).queue();
            return;
        }

        time = e.getArgs()[0];

        role = e.getGuild().getRoleById(e.getGuildDB().getAmongUsRoleId());

        EmbedBuilder eb = getEmbed();

        eb.setAuthor("Among Us Game", "https://www.iphoned.nl/wp-content/uploads/2020/10/among-us-1.jpg", "https://www.iphoned.nl/wp-content/uploads/2020/10/among-us-1.jpg");
        eb.setTitle("Wil jij ook meedoen? Druk op het vinkje onder dit bericht!");
        eb.setDescription("*Microfoon is nodig!*\n" +
                "Maximaal 10 spelers.");

        eb.addField("Tijdstip:", "vandaag om: " + time, false);

        eb.addField("Mensen die al meespelen:", "*Druk opnieuw op het vinkje om van de lijst af te worden gehaald.*", false);

        String roleId = e.getGuildDB().getAmongUsRoleId();
        
        if (!roleId.equals("0")) {
            Role role = e.getGuild().getRoleById(roleId);

            if (!role.isMentionable()) {
                e.getMessageChannel().sendMessage(role.getName() + " isn't mentionable! Please make it mentionable.").queue();
                return;
            } else {
                e.getMessageChannel().sendMessage(role.getAsMention()).queue();
            }
        }

        e.getMessageChannel().sendMessage(eb.build()).queue(m -> {
            databaseReactions.addReactionToDB(new ReactionDB(m.getId(), e.getMessageChannel().getId()));
            m.addReaction("U+2705").queue();
        });

        e.getMessage().delete().queue();
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
