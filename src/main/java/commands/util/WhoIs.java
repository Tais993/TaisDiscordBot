package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import util.entities.UserInfo;

import java.time.Instant;

import static util.Time.getDateFromOffset;

public class WhoIs implements ICommand {
    CommandReceivedEvent e;

    User user;
    Member member;

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

            user = e.getFirstArgAsUser();

            if (user == null) {
                e.getMessageChannel().sendMessage(getFullHelp("Give a valid user ID!")).queue();
                return;
            }

            member = e.getGuild().getMemberById(user.getId());

            if (member == null) {
                e.getMessageChannel().sendMessage(getFullHelp("That isn't a member of this guild!")).queue();
                return;
            }
        } else {
            member = e.getMember();
        }

        EmbedBuilder eb = new EmbedBuilder();
        UserInfo userInfo = new UserInfo(member);

        eb.setTitle(member.getUser().getAsTag());
        eb.addField("Time joined:", getDateFromOffset(member.getTimeJoined()), false);
        eb.addField("Time created:", getDateFromOffset(member.getTimeCreated()), false);
        if (member.getTimeBoosted() != null) eb.addField("Time boosted:", userInfo.getTimeBoosted(), true);

        eb.addField("Roles:", userInfo.getRoles(), true);
        eb.addField("User ID:", member.getId(), true);

        eb.addBlankField(true);
        eb.setThumbnail(member.getUser().getEffectiveAvatarUrl());
        eb.setColor(member.getColor());
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
