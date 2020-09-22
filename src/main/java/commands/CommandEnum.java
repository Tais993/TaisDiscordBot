package commands;

import commands.bot.BotStats;
import commands.fun.EightBall;
import commands.fun.Mock;
import commands.fun.Ping;
import commands.fun.Quote;
import commands.general.Level;
import commands.music.Join;
import commands.music.Leave;
import commands.music.Play;
import commands.util.*;
import commands.util.ban.TempBan;
import commands.util.invitecommand.InviteMain;
import functions.Colors;
import functions.Time;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;

public class CommandEnum {
    enum AllMyCommands {
        PING(new Ping()),
        HELP(new Help()),
        INVITE(new InviteMain()),
        EIGHTBALL(new EightBall()),
        SERVERSTATS(new ServerStats()),
        RENAME(new Rename()),
        MOCK(new Mock()),
        TEST(new Test()),
        QUOTE(new Quote()),
        TEMPBAN(new TempBan()),
        WHOIS(new WhoIs()),
        BOTSTATS(new BotStats()),
        LEVEL(new Level()),
        CHANGEBOTPREFIX(new ChangeBotPrefix()),
        PLAY(new Play()),
        JOIN(new Join()),
        LEAVE(new Leave());
        ICommand c;

        AllMyCommands(ICommand c) {
            this.c = c;
        }

        public ICommand getCommand() {
            return c;
        }
    }
    public static SelfUser bot;

    Colors colors = new Colors();
    Time time = new Time();

    static List commands = new List();
    static ArrayList categories = new ArrayList();
    static List funCategory = new List();
    static List utilCategory = new List();
    static List botCategory = new List();
    static List generalCategory = new List();

    public boolean checkCommand(GuildMessageReceivedEvent event, String[] messageSentSplit) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (messageSentSplit[0].equalsIgnoreCase(c.getCommand()) || messageSentSplit[0].equalsIgnoreCase(c.getCommandAlias())) {
                c.command(event, messageSentSplit);
                return true;
            }
        }
        return false;
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
                            eb.addField(c.getCommand(), c.getShortCommandDescription(), true);
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
                case "bot":
                    botCategory.add(c.getCommand());
                    break;
                case "general":
                    generalCategory.add(c.getCommand());
                    break;
            }
        }
        categories.add("bot");
        categories.add("fun");
        categories.add("util");
        categories.add("general");
    }

    public SelfUser getBot() {
        return bot;
    }
}