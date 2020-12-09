package commands;

import commands.amongus.SetAmongUsRole;
import commands.amongus.StartGame;
import commands.bot.Shutdown;
import commands.bot.*;
import commands.fun.*;
import commands.general.*;
import commands.music.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static commands.CommandEnum.bot;
import static utilities.Colors.getCurrentColor;

public class CommandMap {
    private static final HashMap<String, ICommand> commands = new HashMap<>(Map.ofEntries(
            Map.entry("setamongusrole", new SetAmongUsRole()),
            Map.entry("startgame", new StartGame()),
            Map.entry("botstats", new BotStats()),
            Map.entry("dm", new Dm()),
            Map.entry("removejoke", new RemoveJoke()),
            Map.entry("say", new Say()),
            Map.entry("setblacklisted", new SetBlacklisted()),
            Map.entry("setbotmoderator", new SetBotModerator()),
            Map.entry("shutdown", new Shutdown()),
            Map.entry("addjoke", new AddJoke()),
            Map.entry("calculator", new Calculator()),
            Map.entry("hug", new Hug()),
            Map.entry("joke", new Joke()),
            Map.entry("mock", new Mock()),
            Map.entry("ping", new Ping()),
            Map.entry("addhug", new AddHug()),
            Map.entry("avatar", new Avatar()),
            Map.entry("leaderboard", new Leaderboard()),
            Map.entry("level", new Level()),
            Map.entry("quote", new Quote()),
            Map.entry("remindme", new RemindMe()),
            Map.entry("rep", new Rep()),
            Map.entry("reps", new Reps()),
            Map.entry("test", new Test()),
            Map.entry("youtube", new YouTube()),
            Map.entry("backward", new Backward()),
            Map.entry("clearqueue", new ClearQueue()),
            Map.entry("clearuser", new ClearUser()),
            Map.entry("forward", new Forward()),
            Map.entry("join", new Join()),
            Map.entry("leave", new Leave()),
            Map.entry("loop", new Loop()),
            Map.entry("lyrics", new Lyrics()),
            Map.entry("move", new Move()),
            Map.entry("nowplaying", new NowPlaying()),
            Map.entry("pause", new Pause()),
            Map.entry("play", new Play()),
            Map.entry("playlist", new Playlist()),
            Map.entry("playnow", new PlayNow()),
            Map.entry("playplaylist", new PlayPlaylist()),
            Map.entry("playtop", new PlayTop()),
            Map.entry("previous", new Previous()),
            Map.entry("queue", new Queue()),
            Map.entry("radio538", new Radio538()),
            Map.entry("remove", new Remove()),
            Map.entry("replay", new Replay()),
            Map.entry("resume", new Resume()),
            Map.entry("rickroll", new RickRoll()),
            Map.entry("seek", new Seek()),
            Map.entry("setvolume", new SetVolume()),
            Map.entry("shuffle", new Shuffle()),
            Map.entry("skip", new Skip()),
            Map.entry("skipto", new SkipTo()),
            Map.entry("skunk", new Skunk()),
            Map.entry("help", new Help())
    ));

    static ArrayList<String> categories = new ArrayList<>(List.of("General", "Fun", "Bot", "Music", "Util"));

    static HashMap<String, ICommand> commandAliases = new HashMap<>();

    private final static Stream<ICommand> generalCommands = commands.values().stream().filter(iCommand -> iCommand.getCategory().equalsIgnoreCase("general"));

    public static boolean isCategory(String categoryGiven) {
        return categories.stream().anyMatch(category -> category.equalsIgnoreCase(categoryGiven));
    }

    public static boolean isCommand(String commandGiven) {
        return commands.entrySet().stream().anyMatch(entry -> entry.getValue().getCommandAliases().stream().anyMatch(command -> command.equalsIgnoreCase(commandGiven)));
    }

    public static ICommand getCommand(String commandGiven) {
        return (commands.get(commandGiven) != null) ? commands.get(commandGiven) : commandAliases.get(commandGiven);
    }

    public void prepareAliases() {
        commands.values().forEach(iCommand -> iCommand.getCommandAliases().stream().skip(0).forEach(s -> commandAliases.put(s, iCommand)));
    }

    public static boolean runCommand(CommandReceivedEvent e) {
        ICommand command = getCommand(e.getCommand());

        if (command != null) {
            Thread thread = new Thread(() -> command.command(e));
            thread.start();
            return true;
        }
        return false;
    }

    public static void getHelpAllByCategory(CommandReceivedEvent e) {
        PrivateChannel privateChannel = e.getAuthor().openPrivateChannel().complete();

        categories.forEach(category -> {
            if (!(category.equalsIgnoreCase("botmoderation") && !e.isBotModerator())) {

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(getCurrentColor());
                eb.setTitle("Help " + category);

                eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
                eb.setTimestamp(Instant.now());

                commands.forEach(((s, iCommand) -> {
                    if (iCommand.getCategory().equals(category)) {
                        if (eb.getFields().size() == 24) {
                            privateChannel.sendMessage(eb.build()).queue();
                            eb.clearFields();
                        }
                        eb.addField(iCommand.getCommandAliases().get(0), iCommand.getShortCommandDescription(), true);
                    }
                }));

                privateChannel.sendMessage(eb.build()).queue();
            }
        });
    }

    public static EmbedBuilder getFullHelpItem(String item, String prefix) {
        ICommand c = commands.getOrDefault(item, new Help());

        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
        eb.setTimestamp(Instant.now());

        eb.setTitle("Help " + c.getCommandAliases().stream().findFirst());

        c.getCommandAliases().forEach(commandAlias -> eb.appendDescription(prefix + commandAlias + "\n"));

        eb.addField("`" + prefix + c.getExampleCommand() + "`", c.getFullCommandDescription(), true);

        eb.setColor(getCurrentColor());
        return eb;
    }

    public static EmbedBuilder getShortHelpItem(String item, String prefix) {
        ICommand c = commands.getOrDefault(item, new Help());

        EmbedBuilder eb = new EmbedBuilder();

        eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
        eb.setTimestamp(Instant.now());

        eb.setTitle("Error " + c.getCommandAliases().stream().findFirst());
        eb.addField("`" + prefix + c.getExampleCommand() + "`", c.getShortCommandDescription(), true);

        eb.setColor(getCurrentColor());
        return eb;
    }

    public static void getHelpCategory(String category, CommandReceivedEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(getCurrentColor());

        eb.setTitle("Help " + category);
        eb.setAuthor("Tais", "https://tijsbeek.nl", bot.getAvatarUrl());
        eb.setTimestamp(Instant.now());

        commands.forEach((s, c) -> {
            if (c.getCategory().equals(category)) {
                if (eb.getFields().size() == 24) {
                    e.getChannel().sendMessage(eb.build()).queue();
                    eb.clearFields();
                }
                eb.addField(c.getCommandAliases().get(0), c.getShortCommandDescription(), true);
            }
        });

        e.getChannel().sendMessage(eb.build()).queue();
    }

    public static int getTotalCommands() {
        return commands.size();
    }
}
