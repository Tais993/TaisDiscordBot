import commands.CommandEnum;
import commands.CommandHandler;
import functions.entities.BotInfo;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        JDA jda = builder.build();
        jda.awaitReady();

        CommandEnum.bot = jda.getSelfUser();
        BotInfo.bot = jda.getSelfUser();

        jda.addEventListener(new CommandHandler());
    }
}


