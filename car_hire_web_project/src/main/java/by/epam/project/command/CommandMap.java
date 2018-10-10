package by.epam.project.command;

import by.epam.project.command.car.*;
import by.epam.project.command.order.TakeAllOrdersCommand;
import by.epam.project.command.user.LoginCommand;
import by.epam.project.command.user.LogoutCommand;

import java.util.EnumMap;

public class CommandMap {

    private EnumMap<CommandType, Command> map = new EnumMap<CommandType, Command>(CommandType.class){
        {
            this.put(CommandType.LOGIN, new LoginCommand());
            this.put(CommandType.LOGOUT, new LogoutCommand());

            this.put(CommandType.UPDATE_CAR, new UpdateCarCommand());
            this.put(CommandType.ADD_CAR, new AddCarCommand());
            this.put(CommandType.DELETE_CAR, new DeleteCarCommand());
            this.put(CommandType.TAKE_NOT_RENTED_CARS, new TakeNRCarsCommand());
            this.put(CommandType.TAKE_RENTED_CARS, new TakeRCarsCommand());

            this.put(CommandType.TAKE_ALL_ORDERS, new TakeAllOrdersCommand());
        }
    };

    private static CommandMap instance = new CommandMap();

    private CommandMap(){ }


    public static CommandMap getInstance(){ return instance; }


    public Command get(CommandType key){
        return map.get(key);
    }


    // If default command was returned - redirect to the start page
    @SuppressWarnings("Since15")
    public Command get(String cmdString, Command defaultValue) {
        CommandType key = CommandType.valueOf(CommandType.class, cmdString);
        return map.getOrDefault(key, defaultValue);
    }


    // If default command was returned - redirect to the start page
    @SuppressWarnings("Since15")
    public Command getOrDefault(CommandType key, Command defaultValue){
        return map.getOrDefault(key, defaultValue);
    }

}
