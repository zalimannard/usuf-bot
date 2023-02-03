package ru.zalimannard.command;

import java.util.Objects;
import java.util.regex.Pattern;

public class Argument {
    private final String text;
    private final Pattern pattern;

    public Argument(String text, Pattern pattern) {
        this.text = text;
        this.pattern = pattern;
    }

    public String getText() {
        return text;
    }

    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Argument argument = (Argument) o;
        return Objects.equals(getText(), argument.getText()) && Objects.equals(getPattern(), argument.getPattern());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getPattern());
    }

    @Override
    public String toString() {
        return "Argument{" +
                "text='" + text + '\'' +
                ", pattern=" + pattern +
                '}';
    }
}
