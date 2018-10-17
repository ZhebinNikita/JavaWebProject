package by.epam.project.command.dispatch;

import by.epam.project.command.Command;
import by.epam.project.command.RouteCommand;
import by.epam.project.controller.servlet.Route;
import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatchErrorPage implements Command, RouteCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

    }

    @Override
    public Route getRoute(HttpServletRequest req) throws ProjectException {
        return null;
    }
}
