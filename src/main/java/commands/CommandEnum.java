package commands;

import commands.amongus.StartGame;
import commands.bot.BotStats;
import commands.bot.Shutdown;
import commands.fun.*;
import commands.general.*;
import commands.music.*;
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
        LEAVE(new Leave()),
        SKIP(new Skip()),
        QUEUE(new Queue()),
        PAUSE(new Pause()),
        RESUME(new Resume()),
        SHUTDOWN(new Shutdown()),
        STARTGAME(new StartGame()),
        REACTIONROLE(new ReactionRole()),
        KAAS(new Kaas()),
        NOWPLAYING(new NowPlaying()),
        REMOVE(new Remove()),
        LYRICS(new Lyrics()),
        HUG(new Hug()),
        ADDHUG(new AddHug()),
        RICKROLL(new RickRoll()),
        CLEAR(new Clear()),
        SETVOLUME(new SetVolume()),
        DQW(new dQw()),
        PREVIOUS(new Previous()),
        FORWARD(new Forward()),
        BACKWARDS(new Backward()),
        CLEARQUEUE(new ClearQueue()),
        LEADERBOARD(new Leaderboard()),
        LOOP(new Loop()),
        SHUFFLE(new Shuffle()),
        SEEK(new Seek()),
        PLAYTOP(new PlayTop()),
        MOVE(new Move()),
        PLAYNOW(new PlayNow()),
        SKIPTO(new SkipTo()),
        REPLAY(new Replay()),
        CLEARUSER(new ClearUser()),
        SKUNK(new Skunk());
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
    static List generalCategory = new List();
    static List musicCategory = new List();

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
                eb.setDescription("(option) means optional\n" +
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
            case "general":
                for (String item : CommandEnum.generalCategory.getItems()) {
                    for (CommandEnum.AllMyCommands value : CommandEnum.AllMyCommands.values()) {
                        ICommand c = value.getCommand();

                        if (item.equals(c.getCommand())) {
                            eb.addField(item, c.getShortCommandDescription(), true);
                        }
                    }
                }
                break;
            case "music":
                for (String item : CommandEnum.musicCategory.getItems()) {
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
                case "general":
                    generalCategory.add(c.getCommand());
                    break;
                case "music":
                    musicCategory.add(c.getCommand());
                    break;
            }
        }
        categories.add("fun");
        categories.add("util");
        categories.add("general");
        categories.add("music");
    }

    public SelfUser getBot() {
        return bot;
    }

    public int getTotalCommands() {
        return AllMyCommands.values().length;
    }
}