package commands.util;

import commands.CommandEnum;
import commands.ICommand;
import functions.Colors;
import functions.entities.UserInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class WhoIs implements ICommand {
    GuildMessageReceivedEvent e;
    Member member;
    CommandEnum commandEnum = new CommandEnum();

    String command = "userinfo";
    String commandAlias = "whois";
    String category = "util";
    String exampleCommand = "!userinfo <@user>/<userID>\n" +
            "!whois <@user>/<userID>";
    String shortCommandDescription = "Get information about a user.";
    String fullCommandDescription = "Get information about a user.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        if (!(args.length > 1)) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("userinfo").setDescription("Error: requires at least 1 arguments.").build()).queue();
            return;
        }

        if (!getMember(args)) return;

        sendEmbed();
    }

    public boolean getMember(String[] args) {
        if (e.getMessage().getMentionedMembers().size() > 0) {
            member = e.getMessage().getMentionedMembers().get(0);
        } else if (e.getGuild().getMemberById(args[1]) != null) {
            member = e.getGuild().getMemberById(args[1]);
        } else {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("userinfo").setDescription("Error: ID given isn't valid.").build()).queue();
            return false;
        }
        return true;
    }

    public void sendEmbed() {
        EmbedBuilder eb = new EmbedBuilder();
        UserInfo userInfo = new UserInfo(member);

        eb.setTitle(member.getUser().getAsTag());
        eb.addField("Time joined:", userInfo.getJoinedServerDate(), false);
        eb.addField("Time created:", userInfo.getDateCreated(), false);
        if (member.getTimeBoosted() != null) eb.addField("Time boosted:", userInfo.getTimeBoosted(), true);

        eb.addField("Roles:", userInfo.getRoles(), true);

        eb.addBlankField(true);
        eb.setThumbnail(member.getUser().getAvatarUrl());
        eb.setColor(member.getColor());
        eb.setFooter("Made by Tijs");




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