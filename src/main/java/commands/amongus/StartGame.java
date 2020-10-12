package commands.amongus;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import database.reactions.DatabaseReactions;
import database.reactions.ReactionDB;
import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class StartGame implements ICommand {
    CommandReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    DatabaseReactions databaseReactions = new DatabaseReactions();
    Colors colors = new Colors();

    String time = "16:00";
    Role role;

    String command = "startgame";
    String commandAlias = "startgame";
    String category = "fun";
    String exampleCommand = "`!startgame`";
    String shortCommandDescription = "Start a game of among us";
    String fullCommandDescription = "Start a game of among us, set amount of imposters, and more.\n" +
            "You can see how many people will join, and what their name is.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("Command only works in a Discord guild/server").queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("startgame").setDescription("Error: Requires a time").build()).queue();
            return;
        }

        time = e.getArgs()[0];

        role = e.getGuild().getRoleById("714049141017149453");

        EmbedBuilder eb = getEmbed();

        eb.setAuthor("Among Us Game", "https://cdn.discordapp.com/icons/705908114406506517/0929283dd126725f7c11ee6a0edb56cc.webp", "https://cdn.discordapp.com/icons/705908114406506517/0929283dd126725f7c11ee6a0edb56cc.webp");
        eb.setTitle("Wil jij ook meedoen? Druk op het vinkje onder dit bericht!");
        eb.setDescription("*Microfoon is nodig!*\n" +
                "Maximaal 10 spelers.");

        eb.addField("Tijdstip:", "vandaag om: " + time, false);

        eb.addField("Mensen die al meespelen:", "*Druk opnieuw op het vinkje om van de lijst af te worden gehaald.*", false);

        e.getMessageChannel().sendMessage(eb.build()).queue(m -> {
            databaseReactions.addReactionToDB(new ReactionDB(m.getId(), e.getMessageChannel().getId()));
            m.addReaction("U+2705").queue();
        });

        e.getMessageChannel().sendMessage(role.getAsMention() + "^^^").queue();

        e.getMessage().delete().queue();
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
