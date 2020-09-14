package Commands.Util;

import Commands.Colors;
import Commands.CommandEnum;
import Commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ServerStats implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    Colors colors = new Colors();

    String command = "serverstats";
    String commandAlias = "stats";
    String category = "util";
    String exampleCommand = "!help <item/category>";
    String shortCommandDescription = "Get some help";
    String fullCommandDescription = "Get help for a item or category, but you already know that. Right?";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Server stats of: " + e.getGuild().getName());
        eb.addField("Total members:", e.getGuild().getMembers().size() + "", true);
        eb.setThumbnail(e.getGuild().getIconUrl());
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
