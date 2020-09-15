package Commands.Util;

import Functions.Colors;
import Commands.CommandEnum;
import Commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ServerStats implements ICommand {
    GuildMessageReceivedEvent e;
    Colors colors = new Colors();

    String command = "serverstats";
    String commandAlias = "stats";
    String category = "util";
    String exampleCommand = "!serverstats";
    String shortCommandDescription = "Get the server stats";
    String fullCommandDescription = "Get total member count, and total online/DND members.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Server stats of: " + e.getGuild().getName());
        eb.addField("Total members:", e.getGuild().getMembers().size() + "", false);
        eb.addField("Total online/DND members:", getOnlineMemberCount() + "", false);
        eb.addField("Total emojis:", getTotalGuildEmojis() + "", false);
        eb.setThumbnail(e.getGuild().getIconUrl());
        eb.setFooter("Made by Tijs");

        eb.setColor(colors.getCurrentColor());

        e.getChannel().sendMessage(eb.build()).queue();
    }

    public int getOnlineMemberCount() {
        return (int) e.getGuild().getMembers().stream().filter(value -> value.getOnlineStatus() == OnlineStatus.ONLINE || value.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB).count();
    }

    public int getTotalGuildEmojis() {
        return e.getGuild().getEmotes().size();
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
