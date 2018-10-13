package by.epam.project.constant;

public final class PagePathConstant {

    public final static String PAGE_MAIN = "/jsp/main.jsp";
    public final static String PAGE_CAR_LIST = "/jsp/car_list.jsp";
    public final static String PAGE_ORDERS = "/jsp/orders.jsp";
    public final static String PAGE_PROFILE = "/jsp/profile.jsp";

    public final static String PAGE_ERROR = "/jsp/error_page.jsp";


    public static String getPageByURI(String uri){

        switch (uri) {

            case "/": return PAGE_MAIN;

            case "/profile": return PAGE_PROFILE;

            case "/car_list": return PAGE_CAR_LIST;

            case "/orders": return PAGE_ORDERS;

            default: return PAGE_ERROR;
        }
    }

}
