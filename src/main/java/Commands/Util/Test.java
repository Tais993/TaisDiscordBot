package Commands.Util;

import Functions.Colors;
import Commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Test implements ICommand {
    Colors colors = new Colors();

    GuildMessageReceivedEvent e;
    String command = "test";
    String commandAlias = "test";
    String category = "util";
    String exampleCommand = "`!test`";
    String shortCommandDescription = "Don't try this!";
    String fullCommandDescription = "";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Help test");
        eb.addField("!test", "yes yes don't try this", true);
        eb.setFooter("Made by Tijs");

        eb.setColor(colors.getCurrentColor());
        e.getChannel().sendMessage(eb.build()).queue();
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
