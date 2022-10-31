package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.TrackScheduler;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * The command Clear.
 */
public class Clear extends Command {

    /**
     * Instantiates a new Clear command.
     */
    public Clear() {
        super(
                new ArrayList<>(Arrays.asList("clear", "c")),
                new ArrayList<>(Arrays.asList(
                        new Argument(
                                " ",
                                Pattern.compile(" *")
                        )
                )),
                "Добавить трек(и) в конец очереди",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL,
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            TrackScheduler trackScheduler = getTrackScheduler(member.getGuild());
            while (trackScheduler.getPlaylistSize() > 0) {
                // The bypass is such that the first track does not start playing
                if (trackScheduler.getPlaylistSize() == 1) {
                    trackScheduler.remove(1);
                } else if (trackScheduler.getPlaylistSize() == trackScheduler.getCurrentTrackNumber()) {
                    trackScheduler.remove(trackScheduler.getPlaylistSize() - 1);
                } else {
                    trackScheduler.remove(trackScheduler.getPlaylistSize());
                }
            }
            getMessageSender(member.getGuild()).sendMessage("Очередь очищена");
        }
    }
}