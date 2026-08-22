package dev.reedworkmc.reedwork.command;

import java.util.List;

public final class CommandArguments {

    private final String input;

    public CommandArguments(String input) {
        this.input = input;
    }

    public String raw() {
        return input;
    }

    public boolean isEmpty() {
        return input == null || input.isBlank();
    }

    public List<String> words() {
        return List.of(input.split(" "));
    }

    public String[] array() {
        return input.split(" ");
    }

    public int length() {
        return words().size();
    }

    public String get(int index) {
        return words().get(index);
    }

    public String joinFrom(int index) {
        List<String> values = words();

        if (index >= values.size()) {
            return "";
        }

        return String.join(" ", values.subList(index, values.size()));
    }

    public String withoutLabel() {
        int index = input.indexOf(' ');

        if (index == -1) {
            return "";
        }

        return input.substring(index + 1);
    }
}
