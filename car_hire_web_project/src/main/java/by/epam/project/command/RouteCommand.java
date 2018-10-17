package by.epam.project.command;


import by.epam.project.controller.servlet.Route;
import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface RouteCommand {

    Route getRoute(HttpServletRequest req) throws ProjectException;

}
