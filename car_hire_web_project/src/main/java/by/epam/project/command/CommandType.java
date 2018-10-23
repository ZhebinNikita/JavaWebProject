package by.epam.project.command;

public enum CommandType {
    LOGIN,
    LOGOUT,

    UPDATE_BALANCE,
    REFRESH_BALANCE,

    ADD_CAR,
    UPDATE_CAR,
    REQUEST_RENT_CAR,
    DELETE_CAR,

    ACCEPT_ORDER,
    DENY_ORDER,
    DELETE_ORDER,
    ADD_ORDER_INFO,
    ADD_AD_SERVICE_PRICE,
    PAY_AD_SERVICE_PRICE,

    SET_LANG_JS_MESSAGE,



    DISPATCH_MAIN_PAGE,
    DISPATCH_PROFILE_PAGE,
    DISPATCH_ERROR_PAGE,
    DISPATCH_CAR_LIST_PAGE,
    DISPATCH_ORDERS_PAGE;

    public static CommandType getDispatcherCommand(String pageURI){

        switch (pageURI){
            case "/": return DISPATCH_MAIN_PAGE;
            case "/profile": return DISPATCH_PROFILE_PAGE;
            case "/car_list": return DISPATCH_CAR_LIST_PAGE;
            case "/orders": return DISPATCH_ORDERS_PAGE;
            default: return DISPATCH_ERROR_PAGE;
        }

    }

}
