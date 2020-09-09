package Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class HelpCommand {
    GuildMessageReceivedEvent e;

    public HelpCommand(GuildMessageReceivedEvent event) {
        e = event;
        String messageReceived = event.getMessage().getContentRaw().replace("!help x", "");
        switch (messageReceived) {
            case "":
                helpAll();
                break;
            case "meme":
            case "memes":
            case "meme's":
                helpMemes();
                break;
            case "fun":
                helpFun();
                break;
        }
    }

    public void helpAll() {

    }

    public void helpMemes() {

    }

    public void helpFun() {
        e.getChannel().sendMessage("!ping for your totally real ping!").queue();
    }
}
