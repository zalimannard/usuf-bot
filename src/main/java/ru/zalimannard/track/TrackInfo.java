package ru.zalimannard.track;

import ru.zalimannard.Time;

import java.util.Objects;

/**
 * The type Track info.
 * @author Dmitry Kolesnikov
 * @version 1.0
 */
public class TrackInfo {
    private final String title;
    private final String author;
    private final Time duration;
    private final String url;

    /**
     * Instantiates a new Track info.
     *
     * @param title    the title
     * @param author   the author
     * @param duration the duration
     * @param url      the url
     */
    public TrackInfo(String title, String author, Time duration, String url) {
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.url = url;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public Time getDuration() {
        return duration;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackInfo trackInfo = (TrackInfo) o;
        return Objects.equals(getTitle(), trackInfo.getTitle()) && Objects.equals(getAuthor(), trackInfo.getAuthor()) && Objects.equals(getDuration(), trackInfo.getDuration()) && Objects.equals(getUrl(), trackInfo.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getAuthor(), getDuration(), getUrl());
    }

    @Override
    public String toString() {
        return "TrackInfo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", duration=" + duration +
                ", url='" + url + '\'' +
                '}';
    }
}
