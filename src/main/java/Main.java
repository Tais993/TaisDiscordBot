import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import commands.CommandEnum;
import commands.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.LoggerFactory;
import reactionshandler.OnReactionAdded;
import reactionshandler.OnReactionRemove;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static music.youtube.SearchYouTube.setYtApiKey;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        CommandEnum commandEnum = new CommandEnum();
        commandEnum.getListsReady();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\Discord bot\\token\\tais.token")));

        String token = br.readLine();
        br.close();

        JDA jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).build();
        jda.awaitReady();
        jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.streaming("lttstore.com", "lttstore.com"));


        CommandEnum.bot = jda.getSelfUser();

        jda.addEventListener(new CommandHandler());
        jda.addEventListener(new OnReactionAdded());
        jda.addEventListener(new OnReactionRemove());

        setYtApiKey();

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.ERROR);
    }
}


