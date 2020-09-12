import Commands.CommandEnum;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {

    CommandEnum commandEnum = new CommandEnum();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String messageSent = event.getMessage().getContentRaw();
        String[] messageSentSplit = messageSent.replace("!", "").split(" ");

        if (event.getAuthor().isBot()) return;

        commandEnum.checkCommand(event, messageSentSplit);
    }
}

