package by.epam.project.command.dispatcher;

import by.epam.project.command.Command;
import by.epam.project.constant.PagePathConstant;
import by.epam.project.controller.servlet.Route;
import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainPageDispatcherCommand implements Command {

    private Route route;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException {
        route = new Route();
        String pagePath = PagePathConstant.PAGE_MAIN;
        route.setPagePath(pagePath);
    }


    public Route getRoute() {
        return route;
    }
}
