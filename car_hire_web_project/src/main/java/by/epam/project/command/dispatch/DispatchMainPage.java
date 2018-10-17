package by.epam.project.command.dispatch;

import by.epam.project.command.Command;
import by.epam.project.command.RouteCommand;
import by.epam.project.constant.PagePathConstant;
import by.epam.project.controller.servlet.Route;
import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatchMainPage implements Command, RouteCommand {

    private Route route;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException {
        route = new Route();
        String pagePath = PagePathConstant.PAGE_MAIN;
        route.setPagePath(pagePath);
    }


    @Override
    public Route getRoute(HttpServletRequest req) throws ProjectException {
        return route;
    }

}
