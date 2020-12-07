import java.util.TimerTask;

public class PingTijs extends TimerTask {

    @Override
    public void run() {
        TaisDiscordBot.jda.retrieveUserById(257500867568205824L).queue(user -> user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("`!d bump` NOW").queue()));
    }
}
