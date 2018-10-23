package by.epam.project.command;

import by.epam.project.command.ajax.account.RefreshBalanceCommand;
import by.epam.project.command.ajax.account.UpdateBalanceCommand;
import by.epam.project.command.ajax.car.*;
import by.epam.project.command.ajax.order.*;
import by.epam.project.command.dispatch.*;
import by.epam.project.command.ajax.lang.SetLangJsMessageCommand;
import by.epam.project.command.ajax.user.LoginCommand;
import by.epam.project.command.ajax.user.LogoutCommand;

import java.util.EnumMap;

import static by.epam.project.command.CommandType.*;

public class CommandMap {

    private EnumMap<CommandType, Command> map = new EnumMap<CommandType, Command>(CommandType.class){
        {
            this.put(LOGIN, new LoginCommand());
            this.put(LOGOUT, new LogoutCommand());

            this.put(UPDATE_BALANCE, new UpdateBalanceCommand());
            this.put(REFRESH_BALANCE, new RefreshBalanceCommand());

            this.put(ADD_CAR, new AddCarCommand());
            this.put(UPDATE_CAR, new UpdateCarCommand());
            this.put(DELETE_CAR, new DeleteCarCommand());
            this.put(REQUEST_RENT_CAR, new RequestRentCarCommand());

            this.put(ACCEPT_ORDER, new AcceptOrderCommand());
            this.put(DENY_ORDER, new DenyOrderCommand());
            this.put(DELETE_ORDER, new DeleteOrderCommand());
            this.put(ADD_ORDER_INFO, new AddOrderInfoCommand());
            this.put(ADD_AD_SERVICE_PRICE, new AddAdServicePriceCommand());
            this.put(PAY_AD_SERVICE_PRICE, new PayAdServicePriceCommand());

            this.put(SET_LANG_JS_MESSAGE, new SetLangJsMessageCommand());



            this.put(DISPATCH_MAIN_PAGE, new DispatchMainPage());
            this.put(DISPATCH_PROFILE_PAGE, new DispatchProfilePage());
            this.put(DISPATCH_ERROR_PAGE, new DispatchErrorPage());
            this.put(DISPATCH_CAR_LIST_PAGE, new DispatchCarListPage());
            this.put(DISPATCH_ORDERS_PAGE, new DispatchOrdersPage());
        }
    };


    private static CommandMap instance = new CommandMap();


    private CommandMap(){ }


    public static CommandMap getInstance(){ return instance; }


    public Command get(CommandType key) {
        return map.get(key);
    }


}
