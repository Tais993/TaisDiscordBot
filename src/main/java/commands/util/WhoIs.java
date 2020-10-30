package commands.util;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.entities.UserInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.time.Instant;

public class WhoIs implements ICommand {
    CommandReceivedEvent e;
    Member member;
    CommandEnum commandEnum = new CommandEnum();

    String userId;

    String command = "userinfo";
    String commandAlias = "whois";
    String category = "util";
    String exampleCommand = "!userinfo <@user>/<userID>\n" +
            "!whois <@user>/<userID>";
    String shortCommandDescription = "Get information about a user.";
    String fullCommandDescription = "Get information about a user.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        if (e.hasArgs()) {
            userId = e.getArgs()[0];
        } else {
            userId = e.getAuthor().getId();
        }

        if (!getMember()) return;

        sendEmbed();
    }

    public boolean getMember() {
        if (e.getMessage().getMentionedMembers().size() > 0) {
            member = e.getMessage().getMentionedMembers().get(0);
        } else {
            member = e.getGuild().retrieveMemberById(userId).complete();

            if (member == null) {
                e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("userinfo").setDescription("Error: ID given isn't valid.").build()).queue();
                return false;
            }
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
        eb.addField("User ID:", member.getId(), true);

        eb.addBlankField(true);
        eb.setThumbnail(member.getUser().getEffectiveAvatarUrl());
        eb.setColor(member.getColor());
        eb.setFooter("Made by Tijs ");
        eb.setTimestamp(Instant.now());

        e.getMessageChannel().sendMessage(eb.build()).queue();
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
