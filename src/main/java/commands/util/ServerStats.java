package commands.util;

import commands.ICommand;
import functions.Colors;
import functions.entities.GuildInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.utils.concurrent.Task;

import java.util.List;

public class ServerStats implements ICommand {
    GuildMessageReceivedEvent e;
    Colors colors = new Colors();

    String command = "serverinfo";
    String commandAlias = "serverstats";
    String category = "util";
    String exampleCommand = "!serverinfo / !serverstats";
    String shortCommandDescription = "Get info about the server.";
    String fullCommandDescription = "Get information about the server.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        GuildInfo guildInfo = new GuildInfo(e.getGuild());

        e.getChannel().sendMessage("Working on.. Discord I hate you").queue();

//        EmbedBuilder eb = new EmbedBuilder();
//
//        List<Member> members = e.getGuild().loadMembers().get();
//
//        eb.setTitle("Server stats of: " + e.getGuild().getName());
//        eb.addField("Total members:", members.size() + "", false);
//        eb.addField("Total online/DND members:", guildInfo.getOnlineMemberCount() + "", false);
//        eb.addField("Total non animated emojis:", guildInfo.getTotalNonAnimatedEmojis() + "", true);
//        eb.addField("Total animated emojis:", guildInfo.getTotalAnimatedEmojis() + "", true);
//        eb.addBlankField(true);
//        eb.addField("Server has been created on:", guildInfo.getDateCreated(), true);
//        eb.addField("Server owner:", guildInfo.getOwnerName(), true);
//        eb.addBlankField(true);
//        eb.setThumbnail(e.getGuild().getIconUrl());
//        eb.setFooter("Made by Tijs");
//
//        eb.setColor(colors.getCurrentColor());
//
//        e.getChannel().sendMessage(eb.build()).queue();
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
