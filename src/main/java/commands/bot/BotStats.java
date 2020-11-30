package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class BotStats implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("botstats", "stats"));
    String category = "general";
    String exampleCommand = "botstats";
    String shortCommandDescription = "Get info about the bot.";
    String fullCommandDescription = "Get information about the bot.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        EmbedBuilder eb = getEmbed();

        eb.setTitle("Botstats");
        eb.addField("Total joined guilds:",  e.getJDA().getSelfUser().getMutualGuilds().size() + "", false);
        eb.addField("Total commands", commandEnum.getTotalCommands() + "", true);
        eb.addField("Created by:", e.getJDA().retrieveUserById("257500867568205824").complete().getAsTag(), true);
        eb.addField("Github:", "[Click here to get it!](https://github.com/Tais993/taisdiscordbot \"Tais Discord Bot on Github\")", true);
        eb.setThumbnail(e.getJDA().getSelfUser().getAvatarUrl());

        e.getChannel().sendMessage(eb.build()).queue();
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
