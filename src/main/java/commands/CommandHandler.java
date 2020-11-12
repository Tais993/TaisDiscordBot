package commands;

import database.guild.DatabaseGuild;
import database.guild.GuildDB;
import database.guild.GuildHandler;
import database.user.UserHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    CommandReceivedEvent e;

    GuildDB guildDB;

    CommandEnum commandEnum = new CommandEnum();
    UserHandler userHandler = new UserHandler();
    GuildHandler guildHandler = new GuildHandler();
    DatabaseGuild databaseGuild = new DatabaseGuild();
    BotPrefix botPrefix = new BotPrefix();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        if (event.isFromGuild()) {
            guildDB = databaseGuild.getGuildFromDBToGuildDB(event.getGuild().getId());
        }

        e = new CommandReceivedEvent(event, guildDB);

        if (e.getUserDB().isBlackListed()) {
            e.getMessageChannel().sendMessage("You suck.").queue();
            return;
        }

        if (e.getMessageAsString().startsWith(e.getPrefix())) {
            if (commandEnum.checkCommand(e)) return;
        }

        if (e.getMessage().getMentionedUsers().contains(e.getJDA().getSelfUser())) botPrefix.command(e);

        if (e.isFromGuild()) {
            userHandler.checkUser(event);
            guildHandler.checkGuild(event);
        }

        System.out.println(event.getGuild().getName() + " " + event.getAuthor().getAsTag() + " > " + event.getMessage().getContentRaw());
    }
}

