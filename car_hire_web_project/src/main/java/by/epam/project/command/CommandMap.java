package by.epam.project.command;

import by.epam.project.command.ajax.account.RefreshBalanceCommand;
import by.epam.project.command.ajax.account.UpdateBalanceCommand;
import by.epam.project.command.ajax.car.*;
import by.epam.project.command.ajax.order.*;
import by.epam.project.command.dispatcher.car.TakeNRCarsCommand;
import by.epam.project.command.dispatcher.order.TakeOrderByEmailCommand;
import by.epam.project.command.dispatcher.order.TakePaidOrdersCommand;
import by.epam.project.command.dispatcher.car.TakeRCarsCommand;
import by.epam.project.command.dispatcher.order.TakeNotPaidOrdersCommand;
import by.epam.project.command.ajax.lang.SetLangJsMessageCommand;
import by.epam.project.command.ajax.user.LoginCommand;
import by.epam.project.command.ajax.user.LogoutCommand;

import java.util.EnumMap;

public class CommandMap {

    private EnumMap<CommandType, Command> map = new EnumMap<CommandType, Command>(CommandType.class){
        {
            this.put(CommandType.LOGIN, new LoginCommand());
            this.put(CommandType.LOGOUT, new LogoutCommand());

            this.put(CommandType.UPDATE_BALANCE, new UpdateBalanceCommand());
            this.put(CommandType.REFRESH_BALANCE, new RefreshBalanceCommand());

            this.put(CommandType.ADD_CAR, new AddCarCommand());
            this.put(CommandType.UPDATE_CAR, new UpdateCarCommand());
            this.put(CommandType.DELETE_CAR, new DeleteCarCommand());
            this.put(CommandType.REQUEST_RENT_CAR, new RequestRentCarCommand());
            this.put(CommandType.TAKE_NOT_RENTED_CARS, new TakeNRCarsCommand());
            this.put(CommandType.TAKE_RENTED_CARS, new TakeRCarsCommand());

            this.put(CommandType.ACCEPT_ORDER, new AcceptOrderCommand());
            this.put(CommandType.DENY_ORDER, new DenyOrderCommand());
            this.put(CommandType.DELETE_ORDER, new DeleteOrderCommand());
            this.put(CommandType.ADD_ORDER_INFO, new AddOrderInfoCommand());
            this.put(CommandType.ADD_AD_SERVICE_PRICE, new AddAdServicePriceCommand());
            this.put(CommandType.PAY_AD_SERVICE_PRICE, new PayAdServicePriceCommand());
            this.put(CommandType.TAKE_PAID_ORDERS, new TakePaidOrdersCommand());
            this.put(CommandType.TAKE_NOT_PAID_ORDERS, new TakeNotPaidOrdersCommand());
            this.put(CommandType.TAKE_ORDER_BY_EMAIL, new TakeOrderByEmailCommand());

            this.put(CommandType.SET_LANG_JS_MESSAGE, new SetLangJsMessageCommand());
        }
    };


    private static CommandMap instance = new CommandMap();


    private CommandMap(){ }


    public static CommandMap getInstance(){ return instance; }


    public Command get(CommandType key) {
        return map.get(key);
    }


}
