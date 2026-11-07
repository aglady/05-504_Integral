package ru.gr05504.application;

public interface Command {
    void execute();
    void undo();
}
