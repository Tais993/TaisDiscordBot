package commands;

import database.guild.DatabaseGuild;
import database.guild.GuildDB;
import database.guild.GuildHandler;
import database.user.DatabaseUser;
import database.user.UserDB;
import database.user.UserHandler;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

import static commands.BotPrefix.sendBotPrefixGuild;

public class CommandHandler extends ListenerAdapter {
    CommandReceivedEvent e;

    GuildDB guildDB;
    UserDB userDB;

    CommandEnum commandEnum = new CommandEnum();
    UserHandler userHandler = new UserHandler();
    GuildHandler guildHandler = new GuildHandler();
    DatabaseGuild databaseGuild = new DatabaseGuild();
    DatabaseUser databaseUser = new DatabaseUser();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        Thread thread = new Thread(() -> e = new CommandReceivedEvent(event));
        thread.start();

        if (event.isFromGuild()) {
            guildDB = databaseGuild.getGuildFromDBToGuildDB(event.getGuild().getId());
        }

        userDB = databaseUser.getUserFromDBToUserDB(event.getAuthor().getId());

        if (userDB.isBlackListed()) {
            return;
        }

        String prefix;

        if (userDB.getPrefix() != null) {
            prefix = userDB.getPrefix();
        } else {
            prefix = guildDB.getPrefix();
        }

        if (event.getMessage().getContentRaw().startsWith(prefix) || event.getMessage().getContentRaw().startsWith(guildDB.getPrefix())) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
            e.setDBItems(userDB, guildDB);
            if (commandEnum.checkCommand(e)) {
                return;
            }
        }

        if (event.getMessage().getMentionedUsers().contains(event.getJDA().getSelfUser())) {
            sendBotPrefixGuild(event, guildDB);
        }

        if (event.isFromGuild()) {
            userHandler.checkUser(event);
            guildHandler.checkGuild(event);
            System.out.println(event.getGuild().getName() + " " + event.getAuthor().getAsTag() + " > " + event.getMessage().getContentRaw());
        } else {
            System.out.println(event.getPrivateChannel().getName() + " " + event.getAuthor().getAsTag() + " > " + event.getMessage().getContentRaw());
        }
    }
}

