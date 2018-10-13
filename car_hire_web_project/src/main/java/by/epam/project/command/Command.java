package by.epam.project.command;

import by.epam.project.controller.servlet.Route;
import by.epam.project.exception.ProjectException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException;

}
