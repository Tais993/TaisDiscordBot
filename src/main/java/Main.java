import commands.CommandEnum;
import commands.CommandHandler;
import functions.entities.BotInfo;
import music.youtube.Search;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import reactionshandler.OnReactionAdded;
import reactionshandler.OnReactionRemove;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        CommandEnum commandEnum = new CommandEnum();
        commandEnum.getListsReady();

        InputStream is = new FileInputStream(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\Discord bot\\token\\tais.token");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String token = br.readLine();
        br.close();

        JDA jda = JDABuilder.createDefault(token).setDisabledIntents(GatewayIntent.GUILD_MEMBERS).build();
        //
        // .setMemberCachePolicy(MemberCachePolicy.ALL).setDisabledIntents(GatewayIntent.GUILD_PRESENCES)

        jda.awaitReady();
        jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.of(Activity.ActivityType.WATCHING   , "lttstore.com"));

        CommandEnum.bot = jda.getSelfUser();
        BotInfo.bot = jda.getSelfUser();

        jda.addEventListener(new CommandHandler());
        jda.addEventListener(new OnReactionAdded());
        jda.addEventListener(new OnReactionRemove());

        Search search = new Search();
        search.setYtApiKey();

//        Timer timer = new Timer(); // creating timer
//        TimerTask task = new RemindMeHandler(); // creating timer task
//        timer.schedule(task, new Date()); // scheduling the task
    }
}


