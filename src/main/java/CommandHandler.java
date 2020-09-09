import Commands.HelpCommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String messageSent = event.getMessage().getContentRaw();
        String[] messageSentSplit = messageSent.replace("!", "").split(" ");

        if (event.getAuthor().isBot()) return;

        switch (messageSentSplit[0]) {
            case "help":
                HelpCommand helpCommand = new HelpCommand(event);
                break;
            case "ping":
                event.getChannel().sendMessage("1ms").queue();
                break;
        }
    }
}

