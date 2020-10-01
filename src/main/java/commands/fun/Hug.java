package commands.fun;

import commands.CommandEnum;
import commands.ICommand;
import database.hugs.DatabaseHugs;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class Hug implements ICommand {
    DatabaseHugs databaseHugs = new DatabaseHugs();
    CommandEnum commandEnum = new CommandEnum();
    Random r = new Random();

    String authorMentioned;
    String userToHugMentioned;

    static String filePath = "C:\\Users\\Tijs\\Documents\\Discord bot\\gif\\animehug.gif";
    static File file = new File(filePath);

    GuildMessageReceivedEvent e;
    String command = "hug";
    String commandAlias = "hug";
    String category = "fun";
    String exampleCommand = "`!hug <@user>/<userID>`";
    String shortCommandDescription = "Love you too";
    String fullCommandDescription = "*No homo* lets hug each other!";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        if (e.getMessage().getMentionedMembers().size() >= 1) {
            userToHugMentioned = e.getMessage().getMentionedMembers().get(0).getAsMention();
        } else if (args.length >= 2) {
            Member member = e.getGuild().getMemberById(args[1]);
            if (member != null) {
                userToHugMentioned = member.getAsMention();
            } else {
                e.getChannel().sendMessage(commandEnum.getFullHelpItem("hug").setDescription("Error: give a valid userId").build()).queue();
                return;
            }
        } else {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("hug").setDescription("Error: either mention a user or give a user ID").build()).queue();
            return;
        }

        if (r.nextInt(101) == 100) {
            e.getChannel().sendMessage("https://media.tenor.co/videos/a63b7a5bcb603f717e08232eba5a282b/mp4").queue();
            return;
        }

        int maxInt = databaseHugs.numberItemsInDB();
        int randomGifIndex = r.nextInt(maxInt);
        String gifUrl = databaseHugs.getGifFromDB(randomGifIndex + 1);

        createFile(gifUrl);

        authorMentioned = e.getAuthor().getAsMention();

        e.getChannel().sendMessage(authorMentioned + " hugs " + userToHugMentioned + " :heart:").addFile(file).queue();

    }

    public void createFile(String gifUrl) {
        URL url;
        byte[] bytes;

        try {
            url = new URL(gifUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("Accept", "image/gif");
            DataInputStream di = new DataInputStream(urlConnection.getInputStream());
            bytes = di.readAllBytes();

            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getCommandAlias() {
        return commandAlias;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getExampleCommand() {
        return exampleCommand;
    }

    @Override
    public String getShortCommandDescription() {
        return shortCommandDescription;
    }

    @Override
    public String getFullCommandDescription() {
        return fullCommandDescription;
    }
}
