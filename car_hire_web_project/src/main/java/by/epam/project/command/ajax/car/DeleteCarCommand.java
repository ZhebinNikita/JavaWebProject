package by.epam.project.command.ajax.car;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.entity.Car;
import by.epam.project.language.LangResourceManager;
import by.epam.project.service.impl.CarService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCarCommand implements Command {

    private static final String PARAM_ID = "id";
    private CarService carService = new CarService();
    private LangResourceManager langManager = LangResourceManager.INSTANCE;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        int id = Integer.valueOf(req.getParameter(PARAM_ID));
        Car car = new Car(id);

        if(carService.deleteByID(car))
            resp.getWriter().write(langManager.getString("data.deleted"));
        else
            resp.getWriter().write(langManager.getString("smth.went.wrong"));
    }

}
