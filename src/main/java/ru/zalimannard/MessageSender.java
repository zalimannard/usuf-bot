package ru.zalimannard;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import ru.zalimannard.command.Command;
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
                new Play(), new Skip(), new Prev(), new Loop(), new Loopq(), new Clear(), new Help()
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

    public void sendTrackAdded(Track track, int queueNumber, Duration timeTo) {
        EmbedBuilder trackAddedEmbed = new EmbedBuilder();
        trackAddedEmbed.setColor(goodColor);
        trackAddedEmbed.setTitle("Трек добавлен:");

        TrackLoader trackLoader = new TrackLoader();
        trackAddedEmbed.setThumbnail(trackLoader.getThumbnailUrl(track));

        String mainLine = queueNumber + ". " + track.getTitle();
        String description = track.getAuthor() + "\n" + track.getUrl() + "\nПродолжительность: " + track.getDuration().getHmsFormat();
        trackAddedEmbed.addField(mainLine, description, false);

        Member requester = guild.getMemberById(track.getRequesterId());
        try {
            if (timeTo.getMilliseconds() > 0) {
                trackAddedEmbed.setFooter(requester.getNickname() + "    Будет через: " + timeTo.getHmsFormat(), requester.getEffectiveAvatarUrl());
            } else {
                trackAddedEmbed.setFooter(requester.getNickname(), requester.getEffectiveAvatarUrl());
            }
        } catch (Exception e) {
            if (timeTo.getMilliseconds() > 0) {
                trackAddedEmbed.setFooter("Будет через: " + timeTo.getHmsFormat());
            }
        }

        getCurrentMessageChannel().sendMessageEmbeds(trackAddedEmbed.build()).submit();
    }

    public void sendCurrentTrackInfo(Track track, int queueNumber, int queueSize, boolean isTrackLooped,
                                     boolean isQueueLooped) {
        EmbedBuilder trackAddedEmbed = new EmbedBuilder();
        trackAddedEmbed.setColor(goodColor);
        trackAddedEmbed.setTitle("Сейчас играет:");

        String mainLine = queueNumber + "/" + queueSize + ". " + track.getTitle();
        String description = track.getAuthor() + "\n" +
                track.getUrl() + "\n"
                + "Продолжительность: " + track.getDuration().getHmsFormat();
        trackAddedEmbed.addField(mainLine, description, false);

        TrackLoader trackLoader = new TrackLoader();
        trackAddedEmbed.setImage(trackLoader.getImageUrl(track));

        Member requester = guild.getMemberById(track.getRequesterId());
        try {
            String footerText = requester.getNickname();
            if (isTrackLooped) {
                footerText += "\nТрек зациклен";
            }
            if (isQueueLooped) {
                footerText += "\nОчередь зациклена";
            }
            trackAddedEmbed.setFooter(footerText, requester.getEffectiveAvatarUrl());
        } catch (Exception e) {
            String footerText = "";
            if (isTrackLooped) {
                footerText += "Трек зациклен";
            }
            if (isQueueLooped) {
                footerText += "\nОчередь зациклена";
            }
            trackAddedEmbed.setFooter(footerText);
        }

        getCurrentMessageChannel().sendMessageEmbeds(trackAddedEmbed.build()).submit();
    }

    public MessageChannel getCurrentMessageChannel() {
        return currentMessageChannel;
    }

    public void setCurrentMessageChannel(MessageChannel currentMessageChannel) {
        this.currentMessageChannel = currentMessageChannel;
    }
}
