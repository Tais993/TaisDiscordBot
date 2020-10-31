package commands;

import commands.amongus.SetAmongUsRole;
import commands.amongus.StartGame;
import commands.bot.Shutdown;
import commands.bot.*;
import commands.fun.*;
import commands.general.*;
import commands.music.*;
import commands.util.*;
import commands.util.ban.TempBan;
import commands.util.invitecommand.InviteMain;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.SelfUser;

import java.time.Instant;
import java.util.ArrayList;

import static functions.Colors.getCurrentColor;

public class CommandEnum {
    enum AllMyCommands {
        PING(new Ping()),
        HELP(new Help()),
        INVITE(new InviteMain()),
        EIGHTBALL(new EightBall()),
        SERVERSTATS(new ServerStats()),
        RENAME(new Rename()),
        MOCK(new Mock()),
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
        SKUNK(new Skunk()),
        RADIO538(new Radio538()),
        YOUTUBE(new YouTube()),
        TEST(new Test()),
        JOKE(new Joke()),
        ADDJOKE(new AddJoke()),
        REMOVEJOKE(new RemoveJoke()),
        SETBLACKLISTED(new SetBlacklisted()),
        SETBOTMODERATOR(new SetBotModerator()),
        REP(new Rep()),
        REPS(new Reps()),
        SAY(new Say()),
        AVATAR(new Avatar()),
        DM(new Dm()),
        SETAMONGUSROLE(new SetAmongUsRole());
        ICommand c;

        AllMyCommands(ICommand c) {
            this.c = c;
        }

        public ICommand getCommand() {
            return c;
        }
    }

    public static SelfUser bot;

    static ArrayList<String> categories = new ArrayList<>();

    public boolean checkCommand(CommandReceivedEvent e) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (e.getCommand().equalsIgnoreCase(c.getCommand()) || e.getCommand().equalsIgnoreCase(c.getCommandAlias())) {
                if (c.getCategory().equalsIgnoreCase("botmoderation")) {
                    if (e.isBotModerator()) {
                        c.command(e);
                        return true;
                    }
                    e.getMessageChannel().sendMessage("Requires to be a bot moderator!").queue();
                    return false;
                }
                c.command(e);
                return true;
            }
        }
        return false;
    }

    public boolean checkOrValidCommand(String arg, boolean isBotModerator) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (arg.equalsIgnoreCase(c.getCommand())) {
                if (c.getCategory().equalsIgnoreCase("botmoderation")) {
                    return isBotModerator;
                }
                return true;
            }
        }
        return false;
    }

    public void getHelpAllByCategory(CommandReceivedEvent e) {

        PrivateChannel privateChannel = e.getAuthor().openPrivateChannel().complete();

        categories.forEach(category -> {
            if (!(category.equals("botmoderation") && !e.isBotModerator())) {

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(getCurrentColor());
                eb.setTitle("Help " + category);

                eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
                eb.setTimestamp(Instant.now());

                for (CommandEnum.AllMyCommands value : CommandEnum.AllMyCommands.values()) {
                    ICommand c = value.getCommand();

                    if (c.getCategory().equals(category)) {
                        if (eb.getFields().size() == 24) {
                            privateChannel.sendMessage(eb.build()).queue();
                            eb.clearFields();
                        }
                        eb.addField(c.getCommand(), c.getShortCommandDescription(), true);
                    }
                }
                privateChannel.sendMessage(eb.build()).queue();
            }
        });
    }

    public EmbedBuilder getFullHelpItem(String item) {
        for (AllMyCommands value : AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (item.equals(c.getCommand())) {
                EmbedBuilder eb = new EmbedBuilder();

                eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
                eb.setTimestamp(Instant.now());

                eb.setTitle("Help " + c.getCommand());
                eb.setDescription("(option) means optional\n" +
                        "<option> is required");
                eb.addField(c.getExampleCommand(),  c.getFullCommandDescription(), true);

                eb.setColor(getCurrentColor());
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

    public void getHelpCategory(String category, CommandReceivedEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(getCurrentColor());

        eb.setTitle("Help " + category);
        eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
        eb.setTimestamp(Instant.now());

        for (CommandEnum.AllMyCommands value : CommandEnum.AllMyCommands.values()) {
            ICommand c = value.getCommand();

            if (c.getCategory().equals(category)) {
                if (eb.getFields().size() == 24) {
                    e.getMessageChannel().sendMessage(eb.build()).queue();
                    eb.clearFields();
                }
                eb.addField(c.getCommand(), c.getShortCommandDescription(), true);
            }
        }

        e.getMessageChannel().sendMessage(eb.build()).queue();
    }

    public void getListsReady() {
        categories.add("fun");
        categories.add("util");
        categories.add("general");
        categories.add("music");
        categories.add("botmoderation");
    }

    public int getTotalCommands() {
        return AllMyCommands.values().length;
    }
}