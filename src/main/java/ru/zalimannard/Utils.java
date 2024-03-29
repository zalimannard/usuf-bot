package ru.zalimannard;

public abstract class Utils {
    public static boolean isNonNegativeIntegerNumber(String value) {
        if (value.length() == 0) {
            return false;
        }

        String digits = "0123456789";
        for (int i = 0; i < value.length(); ++i) {
            if (!digits.contains(String.valueOf(value.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static Duration calculateTimeToTrack(TrackScheduler scheduler, int trackNumberInQueue) {
        Duration timeToTrack = new Duration(0);
        if (trackNumberInQueue > scheduler.getCurrentTrackNumber()) {
            if (scheduler.getCurrentTrackNumber() > 0) {
                Duration remainingTimeCurrentTrack = scheduler.getCurrentTrack().getDuration();
                timeToTrack.add(remainingTimeCurrentTrack);
                timeToTrack.sub(scheduler.getCurrentTrackTimePosition());
            }

            for (int i = scheduler.getCurrentTrackNumber() + 1; i < trackNumberInQueue; ++i) {
                timeToTrack.add(scheduler.getTrack(i).getDuration());
            }
        }
        return timeToTrack;
    }

    public static Duration calculateFullTime(TrackScheduler scheduler) {
        Duration fullDuration = new Duration(0);
        for (int i = 1; i <= scheduler.getPlaylistSize(); ++i) {
            fullDuration.add(scheduler.getTrack(i).getDuration());
        }
        return fullDuration;
    }

    public static String russianToEnglish(String string) {
        String russian = "йцукенгшщзфывапролдячсмитьЙЦУКЕНГШЩЗФЫВАПРОЛДЯЧСМИТЬ";
        String english = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

        for (int i = 0; i < russian.length(); ++i) {
            string = string.replace(russian.charAt(i), english.charAt(i));
        }

        return string;
    }
}
