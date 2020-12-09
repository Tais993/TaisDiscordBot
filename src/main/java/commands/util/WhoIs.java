package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import utilities.entities.UserInfo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import static utilities.Time.getDateFromOffset;

public class WhoIs implements ICommand {
    CommandReceivedEvent e;

    User user;
    Member member;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("whois", "userinfo"));
    String category = "utilities";
    String exampleCommand = "whois <@user>/<userID>";
    String shortCommandDescription = "Get information about a user.";
    String fullCommandDescription = "Get information about a user.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        if (e.hasArgs()) {

            user = e.getFirstArgAsUser();

            if (user == null) {
                e.getChannel().sendMessage(getFullHelp("Give a valid user ID!", e.getPrefix())).queue();
                return;
            }

            member = e.getGuild().getMemberById(user.getId());

            if (member == null) {
                e.getChannel().sendMessage(getFullHelp("That isn't a member of this guild!", e.getPrefix())).queue();
                return;
            }
        } else {
            member = e.getMember();
        }

        EmbedBuilder eb = new EmbedBuilder();
        UserInfo userInfo = new UserInfo(member);
        GuildVoiceState voiceState = member.getVoiceState();

        eb.setTitle(member.getUser().getAsTag());
        eb.addField("Time joined:", getDateFromOffset(member.getTimeJoined()), false);
        eb.addField("Time created:", getDateFromOffset(member.getTimeCreated()), false);
        if (member.getTimeBoosted() != null) eb.addField("Time boosted:", userInfo.getTimeBoosted(), true);
        eb.addField("Voice state", (voiceState.inVoiceChannel()) ? voiceState.getChannel().getName() : "None", false);
        eb.addField("Roles:", userInfo.getRoles(), true);
        eb.addField("User ID:", member.getId(), true);

        eb.addBlankField(true);
        eb.setThumbnail(member.getUser().getEffectiveAvatarUrl());
        eb.setColor(member.getColor());
        eb.setTimestamp(Instant.now());

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
