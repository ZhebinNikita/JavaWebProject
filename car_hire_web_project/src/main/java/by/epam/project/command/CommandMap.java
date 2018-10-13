package by.epam.project.command;

import by.epam.project.command.ajax.account.UpdateBalanceCommand;
import by.epam.project.command.ajax.car.*;
import by.epam.project.command.ajax.order.AcceptOrderCommand;
import by.epam.project.command.dispatcher.TakeNRCarsCommand;
import by.epam.project.command.dispatcher.TakeRCarsCommand;
import by.epam.project.command.dispatcher.AllOrdersDispatcherCommand;
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

            this.put(CommandType.ADD_CAR, new AddCarCommand());
            this.put(CommandType.UPDATE_CAR, new UpdateCarCommand());
            this.put(CommandType.DELETE_CAR, new DeleteCarCommand());
            this.put(CommandType.REQUEST_RENT_CAR, new RequestRentCarCommand());
            this.put(CommandType.TAKE_NOT_RENTED_CARS, new TakeNRCarsCommand());
            this.put(CommandType.TAKE_RENTED_CARS, new TakeRCarsCommand());

            this.put(CommandType.ACCEPT_ORDER, new AcceptOrderCommand());
            this.put(CommandType.TAKE_ALL_ORDERS, new AllOrdersDispatcherCommand());

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
