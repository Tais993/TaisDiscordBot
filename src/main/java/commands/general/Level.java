package commands.general;

import commands.CommandEnum;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;
import functions.Colors;
import functions.entities.UserInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Level implements ICommand {
    GuildMessageReceivedEvent e;
    Member memberGiven;
    UserDB userDB;

    CommandEnum commandEnum = new CommandEnum();
    DatabaseUser databaseUser = new DatabaseUser();
    Colors colors = new Colors();

    String command = "level";
    String commandAlias = "rank";
    String category = "general";
    String exampleCommand = "!level <@user>/<userID>";
    String shortCommandDescription = "Get the level of someone in this server.";
    String fullCommandDescription = "Get the level of someone in this server.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        if (args.length > 1) {
            if (e.getMessage().getMentionedMembers().size() > 0) {
                memberGiven = e.getMessage().getMentionedMembers().get(0);
            } else if (e.getGuild().getMemberById(args[1]) != null) {
                memberGiven = e.getGuild().getMemberById(args[1]);
            }
        } else {
            memberGiven = e.getMember();
        }

        createEmbed(databaseUser.getUserFromDBToUserDB(memberGiven.getId()));
    }

    public void createEmbed(UserDB userDB) {
        userDB.calculateXpForLevelUp();

        UserInfo userInfo = new UserInfo(memberGiven);
        EmbedBuilder eb = new EmbedBuilder();

        eb.setThumbnail(userInfo.getAvatar());
        eb.setAuthor(memberGiven.getEffectiveName());
        eb.appendDescription("Level: " + userDB.getLevel() + "\n");
        eb.appendDescription("XP: " + userDB.getXp() + " out of " + userDB.getXpForLevelUp());
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
