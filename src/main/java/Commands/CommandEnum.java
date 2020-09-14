package Commands;

import Commands.Fun.Ping;
import Commands.Fun.EightBall;
import Commands.Util.Ban;
import Commands.Util.InviteCommand.InviteMain;
import Commands.Util.Rename;
import Commands.Util.ServerStats;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class CommandEnum {
    enum AllMyCommands {
        PING(new Ping()),
        INVITE(new InviteMain()),
        HELP(new Help()),
        BAN(new Ban()),
        EIGHTBALL(new EightBall()),
        SERVERSTATS(new ServerStats()),
        RENAME(new Rename());
        ICommand c;

        AllMyCommands(ICommand c) {
            this.c = c;
        }

        public ICommand getCommand() {
            return c;
        }
    }

    Colors colors = new Colors();

    static List commands = new List();
    static List funCategory = new List();
    static List utilCategory = new List();

    public void checkCommand(GuildMessageReceivedEvent event, String[] messageSentSplit) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (messageSentSplit[0].equalsIgnoreCase(c.getCommand())) {
                c.command(event, messageSentSplit);
            }
        }
    }

    public EmbedBuilder getHelpAll() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(colors.getCurrentColor());

        eb.setTitle("Help all");
        eb.setFooter("Made by Tijs");

        for (CommandEnum.AllMyCommands value : CommandEnum.AllMyCommands.values()) {
            ICommand c = value.getCommand();

            eb.addField(c.getCommand(), c.getShortCommandDescription(), true);
        }
        return eb;
    }

    public EmbedBuilder getFullHelpItem(String item) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (item.equals(c.getCommand())) {
                EmbedBuilder eb = new EmbedBuilder();

                eb.setTitle("Help " + c.getCommand());
                eb.addField(c.getExampleCommand(),  c.getFullCommandDescription(), true);
                eb.setFooter("Made by Tijs");

                eb.setColor(colors.getCurrentColor());
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
        eb.setColor(colors.getCurrentColor());

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