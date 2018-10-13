package by.epam.project.command.ajax.lang;

import by.epam.project.command.Command;
import by.epam.project.exception.ProjectException;
import by.epam.project.language.LangResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetLangJsMessageCommand implements Command {

    private LangResourceManager langManager = LangResourceManager.INSTANCE;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ProjectException, IOException {

        resp.getWriter().write(langManager.getString(req.getParameter("lang_key")));

    }

}
