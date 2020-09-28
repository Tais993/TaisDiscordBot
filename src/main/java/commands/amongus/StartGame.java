package commands.amongus;

import commands.CommandEnum;
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
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    DatabaseReactions databaseReactions = new DatabaseReactions();
    Colors colors = new Colors();

    TextChannel textChannel;
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
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        if (!(args.length > 1)) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("startgame").setDescription("Error: Requires a time").build()).queue();
            return;
        }

        time = args[1];

        role = e.getGuild().getRoleById("714049141017149453");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(colors.getCurrentColor());

        eb.setAuthor("Among Us Game", "https://cdn.discordapp.com/icons/705908114406506517/0929283dd126725f7c11ee6a0edb56cc.webp", "https://cdn.discordapp.com/icons/705908114406506517/0929283dd126725f7c11ee6a0edb56cc.webp");
        eb.setTitle("Wil jij ook meedoen? Druk op het vinkje onder dit bericht!");
        eb.setDescription("*Microfoon is nodig!*\n" +
                "Maximaal 10 spelers.");

        eb.addField("Tijdstip:", "vandaag om: " + time, false);

        eb.addField("Mensen die al meespelen:", "*Druk opnieuw op het vinkje om van de lijst af te worden gehaald.*", false);

        e.getChannel().sendMessage(eb.build()).queue(m -> {
            databaseReactions.addReactionToDB(new ReactionDB(m.getId(), e.getChannel().getId()));
            m.addReaction("U+2705").queue();
        });

        e.getChannel().sendMessage(role.getAsMention() + "^^^").queue();

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
