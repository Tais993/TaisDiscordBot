package Commands.Util;

import Functions.Colors;
import Commands.CommandEnum;
import Commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ServerStats implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    Colors colors = new Colors();

    int onlineMemberCount;

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

        getOnlineMembersGuild();

        eb.setTitle("Server stats of: " + e.getGuild().getName());
        eb.addField("Total members:", e.getGuild().getMembers().size() + "", false);
        eb.addField("Total online/DND members:", onlineMemberCount + "", false);
        eb.setThumbnail(e.getGuild().getIconUrl());
        eb.setFooter("Made by Tijs");

        eb.setColor(colors.getCurrentColor());

        getOnlineMembersGuild();

        e.getChannel().sendMessage(eb.build()).queue();
    }

    public void getOnlineMembersGuild() {
        onlineMemberCount = 0;
        e.getGuild().getMembers().forEach(value -> {
            if (value.getOnlineStatus() == OnlineStatus.ONLINE || value.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB) {
                onlineMemberCount++;
            }
        });
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
