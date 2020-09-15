package Commands;

import Commands.Fun.Mock;
import Commands.Fun.Ping;
import Commands.Fun.EightBall;
import Commands.Fun.Quote;
import Functions.Colors;
import Functions.Time;
import Commands.Util.InviteCommand.InviteMain;
import Commands.Util.Test;
import Commands.Util.Ban;
import Commands.Util.Rename;
import Commands.Util.ServerStats;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;

public class CommandEnum {
    enum AllMyCommands {
        PING(new Ping()),
        HELP(new Help()),
        BAN(new Ban()),
        INVITE(new InviteMain()),
        EIGHTBALL(new EightBall()),
        SERVERSTATS(new ServerStats()),
        RENAME(new Rename()),
        MOCK(new Mock()),
        TEST(new Test()),
        QUOTE(new Quote());
        ICommand c;

        AllMyCommands(ICommand c) {
            this.c = c;
        }

        public ICommand getCommand() {
            return c;
        }
    }
    static public SelfUser bot;

    Colors colors = new Colors();
    Time time = new Time();

    static List commands = new List();
    static ArrayList categories = new ArrayList();
    static List funCategory = new List();
    static List utilCategory = new List();

    public void checkCommand(GuildMessageReceivedEvent event, String[] messageSentSplit) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (messageSentSplit[0].equalsIgnoreCase(c.getCommand()) || messageSentSplit[0].equalsIgnoreCase(c.getCommandAlias())) {
                c.command(event, messageSentSplit);
            }
        }
    }

    public boolean checkOrValidCommand(GuildMessageReceivedEvent event, String[] messageSentSplit) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (messageSentSplit[0].equalsIgnoreCase(c.getCommand())) {
                return true;
            }
        }
        return false;
    }

    public EmbedBuilder getHelpAll() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(colors.getCurrentColor());

        eb.setTitle("Help all");
        eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
        eb.setFooter("Made by Tijs | " + time.getFullDate());

        for (CommandEnum.AllMyCommands value : CommandEnum.AllMyCommands.values()) {
            ICommand c = value.getCommand();

            eb.addField(c.getCommand(), c.getShortCommandDescription(), true);
        }
        return eb;
    }

    public void getHelpAllByCategory(GuildMessageReceivedEvent e) {

        categories.forEach(category -> {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(colors.getCurrentColor());
            eb.setTitle("Help " + category);

            eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
            eb.setFooter("Made by Tijs | " + time.getFullDate());

            for (CommandEnum.AllMyCommands value : CommandEnum.AllMyCommands.values()) {
                ICommand c = value.getCommand();

                if (c.getCategory().equals(category)) {
                    eb.addField(c.getCommand(), c.getShortCommandDescription(), true);
                }
            }
            e.getChannel().sendMessage(eb.build()).queue();
        });
    }

    public EmbedBuilder getFullHelpItem(String item) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (item.equals(c.getCommand())) {
                EmbedBuilder eb = new EmbedBuilder();
                Time time = new Time();

                eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
                eb.setFooter("Made by Tijs | " + time.getFullDate());

                eb.setTitle("Help " + c.getCommand());
                eb.setDescription("(option) means optimal\n" +
                        "<option> is required");
                eb.addField(c.getExampleCommand(),  c.getFullCommandDescription(), true);

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
        eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
        eb.setFooter("Made by Tijs | " + time.getFullDate());

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
        categories.add("fun");
        categories.add("util");
    }
}