package by.epam.project.controller.servlet;

import by.epam.project.constant.PagePathConstant;

public class Route {

    private String pagePath = PagePathConstant.PAGE_MAIN;
    private RouteType routeType = RouteType.FORWARD;


    public String getPagePath(){
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        if (pagePath != null) {
            this.pagePath = pagePath;
        }
    }


    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteTypeRedirect() {
        this.routeType = RouteType.REDIRECT;
    }

}
