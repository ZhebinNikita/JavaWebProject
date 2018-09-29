package by.epam.project.command;

import by.epam.project.exception.ProjectException;

public abstract class Command {

    public abstract boolean execute() throws ProjectException;

}
