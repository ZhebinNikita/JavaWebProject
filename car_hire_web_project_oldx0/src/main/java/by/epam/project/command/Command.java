package by.epam.project.command;

import by.epam.project.exception.ProjectException;

public interface Command {

    boolean execute() throws ProjectException;

}
