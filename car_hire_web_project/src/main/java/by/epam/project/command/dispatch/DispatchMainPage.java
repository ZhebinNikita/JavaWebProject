package by.epam.project.command.dispatch;

import by.epam.project.command.Command;
import by.epam.project.constant.PagePathConstant;
import by.epam.project.controller.servlet.Route;
import by.epam.project.exception.ProjectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatchMainPage implements Command {

    private Route route;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException {
        route = new Route();
        route.setPagePath(PagePathConstant.PAGE_MAIN);
    }


    @Override
    public Route getRoute() throws ProjectException {
        return route;
    }

}
