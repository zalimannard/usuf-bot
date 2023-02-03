package ru.zalimannard;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.commands.Queue;
import ru.zalimannard.command.commands.*;
import ru.zalimannard.track.Track;
import ru.zalimannard.track.TrackLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MessageSender {
    private final String commandPrefix;
    private final Color goodColor = Color.decode("#2ECC71");
    private final Color badColor = Color.decode("#FF0000");
    private MessageChannel currentMessageChannel;
    private final Guild guild;
    private Message previousNowPlaying;

    public MessageSender(String commandPrefix, MessageChannel currentMessageChannel, Guild guild) {
        this.commandPrefix = commandPrefix;
        setCurrentMessageChannel(currentMessageChannel);
        this.guild = guild;
    }

    public void sendMessage(String text) {
        EmbedBuilder trackAddedEmbed = new EmbedBuilder();
        trackAddedEmbed.setColor(goodColor);
        trackAddedEmbed.setTitle(text);
        getCurrentMessageChannel().sendMessageEmbeds(trackAddedEmbed.build()).submit();
    }

    public void sendError(String text) {
        EmbedBuilder trackAddedEmbed = new EmbedBuilder();
        trackAddedEmbed.setColor(badColor);
        trackAddedEmbed.setTitle(text);
        getCurrentMessageChannel().sendMessageEmbeds(trackAddedEmbed.build()).submit();
    }

    public void sendHelp() {
        ArrayList<Command> commands = new ArrayList<>(Arrays.asList(
                new Play(), new Skip(), new Info(), new Jump(), new Queue(), new Insert(), new Prev(), new Loop(),
                new Loopq(), new Clear(), new Help()
        ));
        EmbedBuilder helpEmbed = new EmbedBuilder();
        helpEmbed.setColor(goodColor);
        helpEmbed.setTitle("Команды:");
        for (Command command : commands) {
            String names = commandPrefix + command.getNames().get(0);
            for (int i = 1; i < command.getNames().size(); ++i) {
                names += " [" + commandPrefix + command.getNames().get(i) + "]";
            }
            String arguments = "";
            if (command.getArguments().size() == 1) {
                if (!command.getArguments().get(0).getText().equals(" ")) {
                    arguments = "(" + command.getArguments().get(0).getText() + ")";
                }
            } else {
                arguments = "(" + command.getArguments().get(0).getText() + ")";
                for (int i = 1; i < command.getArguments().size(); ++i) {
                    arguments += " / (" + command.getArguments().get(i).getText() + ")";
                }
            }
            helpEmbed.addField(names + " " + arguments, command.getDescription(), false);
        }
        getCurrentMessageChannel().sendMessageEmbeds(helpEmbed.build()).submit();
    }

    public void sendTrackAdded(TrackScheduler scheduler, int trackNumber) {
        Track track = scheduler.getTrack(trackNumber);
        EmbedBuilder trackAddedEmbed = new EmbedBuilder();
        trackAddedEmbed.setColor(goodColor);
        trackAddedEmbed.setTitle("Трек добавлен:");

        TrackLoader trackLoader = new TrackLoader();
        trackAddedEmbed.setThumbnail(trackLoader.getThumbnailUrl(track));

        String mainLine = trackNumber + ". " + track.getTitle();
        String description = track.getAuthor() + "\n" + track.getUrl() + "\nПродолжительность: " + track.getDuration().getHmsFormat();
        trackAddedEmbed.addField(mainLine, description, false);

        String footerText = "";
        if (trackNumber > scheduler.getCurrentTrackNumber()) {
            footerText += "Будет через: " + Utils.calculateTimeToTrack(scheduler, trackNumber).getHmsFormat() + "\n";
        }
        try {
            Member requester = guild.getMemberById(track.getRequesterId());
            String requesterNickname = requester.getNickname() + "\n";
            trackAddedEmbed.setFooter(requesterNickname + footerText, requester.getEffectiveAvatarUrl());
        } catch (Exception e) {
            trackAddedEmbed.setFooter(footerText);
        }

        getCurrentMessageChannel().sendMessageEmbeds(trackAddedEmbed.build()).submit();
    }

    public void sendNowPlaying(TrackScheduler scheduler) {
        Track track = scheduler.getCurrentTrack();

        EmbedBuilder nowPlayingEmbed = new EmbedBuilder();
        nowPlayingEmbed.setColor(goodColor);
        nowPlayingEmbed.setTitle("Сейчас играет:");

        String mainLine = scheduler.getCurrentTrackNumber() + "/" + scheduler.getPlaylistSize() + ". " + track.getTitle();
        String description = track.getAuthor() + "\n" +
                track.getUrl() + "\n"
                + "Продолжительность: " + track.getDuration().getHmsFormat();
        nowPlayingEmbed.addField(mainLine, description, false);

        TrackLoader trackLoader = new TrackLoader();
        nowPlayingEmbed.setImage(trackLoader.getImageUrl(track));

        String footerText = "";
        if (scheduler.isQueueLooped()) {
            footerText += "Трек зациклен\n";
        }
        if (scheduler.isQueueLooped()) {
            footerText += "Очередь зациклена\n";
        }

        try {
            Member requester = guild.getMemberById(track.getRequesterId());
            String requesterNickname = requester.getNickname() + "\n";
            nowPlayingEmbed.setFooter(requesterNickname + footerText, requester.getEffectiveAvatarUrl());
        } catch (Exception e) {
            nowPlayingEmbed.setFooter(footerText);
        }

        deletePreviousNowPlaying();
        try {
            previousNowPlaying =
                    getCurrentMessageChannel().sendMessageEmbeds(nowPlayingEmbed.build()).submit().get();
        } catch (Exception e) {
            // Неотправленное сообщение не повод вылетать
        }
    }

    public void sendTrackInfo(TrackScheduler scheduler, int trackNumber) {
        Track track = scheduler.getTrack(trackNumber);

        EmbedBuilder trackInfoEmbed = new EmbedBuilder();
        trackInfoEmbed.setColor(goodColor);
        if (trackNumber == scheduler.getCurrentTrackNumber()) {
            trackInfoEmbed.setTitle("Сейчас играет:");
        } else {
            trackInfoEmbed.setTitle("О треке:");
        }

        String mainLine = trackNumber + "/" + scheduler.getPlaylistSize() + ". " + track.getTitle();
        String description = track.getAuthor() + "\n" +
                track.getUrl() + "\n"
                + "Продолжительность: " + track.getDuration().getHmsFormat();
        trackInfoEmbed.addField(mainLine, description, false);

        TrackLoader trackLoader = new TrackLoader();
        trackInfoEmbed.setThumbnail(trackLoader.getThumbnailUrl(scheduler.getTrack(trackNumber)));

        String footerText = "";
        if (scheduler.isQueueLooped()) {
            footerText += "Трек зациклен\n";
        }
        if (scheduler.isQueueLooped()) {
            footerText += "Очередь зациклена\n";
        }
        if (trackNumber == scheduler.getCurrentTrackNumber()) {
            footerText += scheduler.getCurrentTrackTimePosition().getHmsFormat() + " / " + scheduler.getCurrentTrack().getDuration().getHmsFormat() + "\n";
        }
        if (trackNumber > scheduler.getCurrentTrackNumber()) {
            footerText += "Будет через: " + Utils.calculateTimeToTrack(scheduler, trackNumber).getHmsFormat() + "\n";
        }

        try {
            Member requester = guild.getMemberById(track.getRequesterId());
            String requesterNickname = requester.getNickname() + "\n";
            trackInfoEmbed.setFooter(requesterNickname + footerText, requester.getEffectiveAvatarUrl());
        } catch (Exception e) {
            trackInfoEmbed.setFooter(footerText);
        }

        getCurrentMessageChannel().sendMessageEmbeds(trackInfoEmbed.build()).submit();
    }

    public void sendQueue(TrackScheduler scheduler, int first, int last) {
        first = Math.max(1, first);
        last = Math.min(scheduler.getPlaylistSize(), last);
        EmbedBuilder queueEmbed = new EmbedBuilder();

        int counter = 0;
        for (int i = first; i <= last; ++i) {
            ++counter;
            String firstLine = i + ". " + scheduler.getTrack(i).getTitle();
            if (i == scheduler.getCurrentTrackNumber()) {
                firstLine = "\uD83C\uDF1F " + firstLine;
            }
            String secondLine = scheduler.getTrack(i).getDuration().getHmsFormat();
            try {
                Member requester = guild.getMemberById(scheduler.getTrack(i).getRequesterId());
                secondLine += "    Заказал: " + requester.getNickname();
            } catch (Exception e) {
                // Если не найден заказчик - всё равно
            }
            secondLine += "\n" + scheduler.getTrack(i).getUrl();

            queueEmbed.addField(firstLine, secondLine, false);

            if ((counter % 25 == 0) || (i == last)) {
                queueEmbed.setColor(goodColor);
                queueEmbed.setTitle("Очередь воспроизведения (Всего " + scheduler.getPlaylistSize() +
                        ", на " + Utils.calculateFullTime(scheduler).getHmsFormat() + "):");
                getCurrentMessageChannel().sendMessageEmbeds(queueEmbed.build()).submit();
                queueEmbed = new EmbedBuilder();
            }
        }
    }

    public void deletePreviousNowPlaying() {
        if (previousNowPlaying != null) {
            previousNowPlaying.delete().submit();
            previousNowPlaying = null;
        }
    }

    public MessageChannel getCurrentMessageChannel() {
        return currentMessageChannel;
    }

    public void setCurrentMessageChannel(MessageChannel currentMessageChannel) {
        this.currentMessageChannel = currentMessageChannel;
    }
}
