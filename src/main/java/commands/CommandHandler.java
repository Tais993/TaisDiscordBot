package commands;

import database.guild.DatabaseGuild;
import database.guild.GuildDB;
import database.guild.GuildHandler;
import database.user.DatabaseUser;
import database.user.UserDB;
import database.user.UserHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    CommandReceivedEvent e;

    GuildDB guildDB;
    UserDB userDB;

    CommandEnum commandEnum = new CommandEnum();
    UserHandler userHandler = new UserHandler();
    GuildHandler guildHandler = new GuildHandler();
    DatabaseGuild databaseGuild = new DatabaseGuild();
    DatabaseUser databaseUser = new DatabaseUser();
    BotPrefix botPrefix = new BotPrefix();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        e = new CommandReceivedEvent(event);

        Thread thread = new Thread(() -> e = new CommandReceivedEvent(event));
        thread.start();

        if (event.isFromGuild()) {
            guildDB = databaseGuild.getGuildFromDBToGuildDB(event.getGuild().getId());
        }

        userDB = databaseUser.getUserFromDBToUserDB(event.getAuthor().getId());

        if (userDB.isBlackListed()) {
            return;
        }

        if (event.getMessage().getContentRaw().startsWith(guildDB.getPrefix())) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {}
            e.setDBItems(userDB, guildDB);
            if (commandEnum.checkCommand(e)) return;
        }

        if (event.getMessage().getMentionedUsers().contains(event.getJDA().getSelfUser())) botPrefix.command(event, guildDB);

        if (event.isFromGuild()) {
            userHandler.checkUser(event);
            guildHandler.checkGuild(event);
        }

        System.out.println(event.getGuild().getName() + " " + event.getAuthor().getAsTag() + " > " + event.getMessage().getContentRaw());
    }
}

