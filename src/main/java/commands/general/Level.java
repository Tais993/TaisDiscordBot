package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;
import functions.Colors;
import functions.entities.UserInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.time.Instant;

public class Level implements ICommand {
    CommandReceivedEvent e;
    User userGiven;

    DatabaseUser databaseUser = new DatabaseUser();
    Colors colors = new Colors();

    String command = "level";
    String commandAlias = "rank";
    String category = "general";
    String exampleCommand = "!level <@user>/<userID>";
    String shortCommandDescription = "Get the level of someone in this server.";
    String fullCommandDescription = "Get the level of someone in this server.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            levelCommandPrivate();
        } else {
            levelCommandGuild();
        }

        createEmbed(databaseUser.getUserFromDBToUserDB(userGiven.getId()));
    }

    public void levelCommandGuild() {
        if (e.hasArgs()) {
            if (e.getMessage().getMentionedMembers().size() > 0) {
                userGiven = e.getMessage().getMentionedMembers().get(0).getUser();
            } else if (e.getGuild().getMemberById(e.getArgs()[0]) != null) {
                userGiven = e.getJDA().getUserById(e.getArgs()[0]);
            }
        } else {
            userGiven = e.getAuthor();
        }
    }

    public void levelCommandPrivate() {
        if (e.hasArgs()) {
                userGiven = e.getJDA().retrieveUserById(e.getArgs()[0]).complete();
        } else {
            userGiven = e.getAuthor();
        }
    }

    public void createEmbed(UserDB userDB) {
        userDB.calculateXpForLevelUp();

        UserInfo userInfo = new UserInfo(userGiven);
        EmbedBuilder eb = new EmbedBuilder();

        eb.setThumbnail(userInfo.getAvatar());
        eb.setAuthor(userGiven.getAsTag());
        eb.appendDescription("Level: " + userDB.getLevel() + "\n");
        eb.appendDescription("XP: " + userDB.getXp() + " out of " + userDB.getXpForLevelUp());
        eb.setFooter("Made by Tijs ");

        eb.setTimestamp(Instant.now());
        eb.setColor(colors.getCurrentColor());

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
