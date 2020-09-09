
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Main extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        System.out.println("Inside Tais's main");

        BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/tokens/Tais.token")));
        String token = br.readLine();
        br.close();

        System.out.println("token: " + token);

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        JDA jda = builder.build();
        jda.awaitReady();

        jda.addEventListener(new CommandHandler());
    }
}


