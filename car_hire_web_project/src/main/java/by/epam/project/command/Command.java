package by.epam.project.command;

import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface Command {

    boolean execute(HttpServletRequest req) throws ProjectException;

}
