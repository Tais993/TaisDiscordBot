package Commands;

import Commands.Fun.Ping;
import Commands.Util.Invite;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class CommandEnum {
    Color purple = new Color(148, 0, 211);
    Color orange = new Color(219, 65, 5);

    enum AllMyCommands {
        PING(new Ping()),
        INVITE(new Invite()),
        HELP(new Help());
        ICommand c;

        AllMyCommands(ICommand c) {
            this.c = c;
        }

        public ICommand getCommand() {
            return c;
        }
    }

    static List commands = new List();
    static List funCategory = new List();
    static List utilCategory = new List();

    public void checkCommand(GuildMessageReceivedEvent event, String[] messageSentSplit) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (messageSentSplit[0].equals(c.getCommand())) {
                c.command(event, messageSentSplit);
            }
        }
    }

    public EmbedBuilder getFullHelpItem(String item) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (item.equals(c.getCommand())) {
                EmbedBuilder eb = new EmbedBuilder();

                eb.setTitle("Help " + c.getCommand());
//                eb.setDescription((c.getExampleCommand()) + "\n" + c.getFullCommandDescription());
                eb.addField(c.getExampleCommand(),  c.getFullCommandDescription(), true);
                eb.setFooter("Made by Tijs");

                eb.setColor(orange);
                return eb;
            }
        }
        return null;
    }

    public String getShortHelpItem(String item) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (item.equals(c.getCommand())) {
                return c.getShortCommandDescription();
            }
        }
        return null;
    }

    public EmbedBuilder getHelpCategory(String category) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(orange);

        eb.setTitle("Help " + category);
        eb.setFooter("Made by Tijs");

        switch (category) {
            case "fun":
                for (String item : CommandEnum.funCategory.getItems()) {
                    for (CommandEnum.AllMyCommands value : CommandEnum.AllMyCommands.values()) {
                        ICommand c = value.getCommand();

                        if (item.equals(c.getCommand())) {
                            eb.addField("!" + item, c.getShortCommandDescription(), true);
                        }
                    }
                }
                break;
            case "util":
                for (String item : CommandEnum.utilCategory.getItems()) {
                    for (CommandEnum.AllMyCommands value : CommandEnum.AllMyCommands.values()) {
                        ICommand c = value.getCommand();

                        if (item.equals(c.getCommand())) {
                            eb.addField(item, c.getShortCommandDescription(), true);
                        }
                    }
                }
                break;
        }

        return eb;
    }

    public void getListsReady() {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();
            commands.add(c.getCommand());

            switch (c.getCategory()) {
                case "fun":
                    funCategory.add(c.getCommand());
                    break;
                case "util":
                    utilCategory.add(c.getCommand());
                    break;
            }
        }
    }
}