package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;
import ru.zalimannard.track.Track;
import ru.zalimannard.track.TrackLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * The command Play.
 */
public class Play extends Command {

    /**
     * Instantiates a new Play command.
     */
    public Play() {
        super(
                new ArrayList<>(Arrays.asList("play", "p")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                "Url",
                                Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
                        )
                )),
                "Добавить трек(и) в конец очереди",
                new ArrayList<>(Arrays.asList(
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            AudioManager audioManager = member.getGuild().getAudioManager();
            TrackLoader trackLoader = new TrackLoader();
            ArrayList<Track> tracksToAdd = trackLoader.getTracksByUrl(textArgument, member.getId());

            if (tracksToAdd.size() == 0) {
                getMessageSender(member.getGuild()).sendError("Ничего не добавлено");
            } else if (tracksToAdd.size() == 1) {
                getMessageSender(member.getGuild()).sendMessage("Добавлен один трек");
                getTrackScheduler(member.getGuild()).insert(tracksToAdd.get(0));
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            } else {
                getMessageSender(member.getGuild()).sendMessage("Добавлено" + tracksToAdd.size() + "трека(ов)");
                for (Track trackToAdd : tracksToAdd) {
                    getTrackScheduler(member.getGuild()).insert(trackToAdd);
                }
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            }
        }
    }
}
