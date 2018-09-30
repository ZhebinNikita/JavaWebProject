package by.epam.project.controller.commands;

import by.epam.project.controller.services.Service;
import by.epam.project.model.exception.ProjectException;

public abstract class Command {

    public abstract boolean execute() throws ProjectException;

}
