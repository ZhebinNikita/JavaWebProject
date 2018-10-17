package by.epam.project.lang;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class LangSessionManager {


    public static void setSessionLanguage(HttpServletRequest req) {

        HttpSession session = req.getSession(true);

        LangResourceManager langManager = LangResourceManager.INSTANCE;

        if (req.getParameter("language") != null) {
            if (req.getParameter("language").equals("en")) {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
            } else if (req.getParameter("language").equals("ru_RU")) {
                Locale.setDefault(new Locale("ru", "RU"));
                langManager.changeResource(new Locale("ru", "RU"));
                session.setAttribute("language", "ru_RU");
            } else {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
            }
        } else {

            if (session.getAttribute("language") == null) {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
            } else if (session.getAttribute("language").equals("en")) {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
            } else if (session.getAttribute("language").equals("ru_RU")) {
                Locale.setDefault(new Locale("ru", "RU"));
                langManager.changeResource(new Locale("ru", "RU"));
                session.setAttribute("language", "ru_RU");
            } else {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute("language", "en");
            }
        }

    }

}
