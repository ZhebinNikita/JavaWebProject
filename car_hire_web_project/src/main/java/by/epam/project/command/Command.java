package by.epam.project.command;

import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    boolean execute(HttpServletRequest req) throws ProjectException;

}
