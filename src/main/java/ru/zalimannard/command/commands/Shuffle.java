package ru.zalimannard.command.commands;

import net.dv8tion.jda.api.entities.Member;
import ru.zalimannard.command.Argument;
import ru.zalimannard.command.Command;
import ru.zalimannard.command.Requirement;
import ru.zalimannard.track.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Shuffle extends Command {
    public Shuffle() {
        super(
                new ArrayList<>(Arrays.asList("shuffle", "sh")),
                new ArrayList<>(List.of(
                        new Argument(
                                " ",
                                Pattern.compile(" *")
                        )
                )),
                "Перемешать треки в очереди",
                new ArrayList<>(Arrays.asList(
                        Requirement.BOT_IN_THE_VOICE_CHANNEL,
                        Requirement.REQUESTER_IN_THE_VOICE_CHANNEL
                ))
        );
    }

    @Override
    protected void onExecute(Member member, String textArgument) {
        if (getArguments().get(0).getPattern().matcher(textArgument).matches()) {
            ArrayList<Track> tracks = new ArrayList<>();
            for (int i = scheduler.getPlaylistSize(); i >= 1; --i) {
                if (scheduler.getCurrentTrackNumber() != i) {
                    tracks.add(new Track(scheduler.getTrack(i), scheduler.getTrack(i).getRequesterId()));
                    scheduler.remove(i);
                }
            }

            Random random = new Random();
            while (tracks.size() > 0) {
                int number = random.nextInt(tracks.size());
                scheduler.insert(tracks.get(number));
                tracks.remove(number);
            }
            messageSender.sendQueue(scheduler, 1, 11);
        }
    }
}