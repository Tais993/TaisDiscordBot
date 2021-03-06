import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import commands.CommandEnum;
import commands.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.LoggerFactory;
import reactionshandler.OnReactionAdded;
import reactionshandler.OnReactionRemove;
import utilities.EventWaiter;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;
import static music.youtube.SearchYouTube.setYtApiKey;

public class TaisDiscordBot {
    static JDA jda;

    public static void main(String[] args) throws Exception {
        CommandEnum commandEnum = new CommandEnum();
        commandEnum.getListsReady();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\Discord bot\\token\\tais.token")));

        String token = br.readLine();
        br.close();

        jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).setActivity(Activity.of(Activity.ActivityType.STREAMING, "lttstore.com", "lttstore.com")).build().awaitReady();

        CommandEnum.bot = jda.getSelfUser();

        jda.addEventListener(new EventWaiter());
        jda.addEventListener(new CommandHandler());
        jda.addEventListener(new OnReactionAdded());
        jda.addEventListener(new OnReactionRemove());

        setYtApiKey();

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.ERROR);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.scheduleAtFixedRate(new PingTijs(), 100000, 100000, SECONDS);
        scheduler.scheduleAtFixedRate(EventWaiter::checkValidationWaiters, 1, 1, SECONDS);
    }
}


