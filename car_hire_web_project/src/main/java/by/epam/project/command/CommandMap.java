package by.epam.project.command;

import by.epam.project.command.user.DeleteUserCommand;
import by.epam.project.command.user.LoginCommand;
import by.epam.project.command.user.RegisterUserCommand;
import by.epam.project.command.user.UpdateUserCommand;

import java.util.EnumMap;

public class CommandMap {

    private EnumMap<CommandType, Command> map = new EnumMap<CommandType, Command>(CommandType.class){
        {
            this.put(CommandType.LOGIN, new LoginCommand());

            this.put(CommandType.REGISTER_USER, new RegisterUserCommand());
            this.put(CommandType.UPDATE_USER, new UpdateUserCommand());
            this.put(CommandType.DELETE_USER, new DeleteUserCommand());
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
