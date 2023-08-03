package com.example.medbot.help;

public enum Doctors {

    TERAPEVT("TERAPEVT"),
    OKULIST("OKULIST"),
    LOR("LOR"),
    GASTROENTEROLOG("GASTROENTEROLOG"),
    HIRURG("HIRURG"),
    GINEKOLOG("GINEKOLOG");

    private final String title;

    Doctors(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
