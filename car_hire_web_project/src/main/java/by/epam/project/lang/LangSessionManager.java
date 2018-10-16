package by.epam.project.lang;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class LangSessionManager {

    private static final String PARAM_LANG = "lang";
    private static final String ATTR_LANG = "language";

    private static final String LOCALE_EN = "en";
    private static final String LOCALE_RU_RU = "ru_RU";

    private static final Locale ruRU = new Locale("ru", "RU");


    public static void setSessionLanguage(HttpServletRequest req) {

        HttpSession session = req.getSession(true);

        LangResourceManager langManager = LangResourceManager.INSTANCE;

        if (req.getParameter(PARAM_LANG) != null) {

            if (req.getParameter(PARAM_LANG).equals(LOCALE_EN)) {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute(ATTR_LANG, LOCALE_EN);
            }
            else if (req.getParameter(PARAM_LANG).equals(LOCALE_RU_RU)) {
                Locale.setDefault(ruRU);
                langManager.changeResource(ruRU);
                session.setAttribute(ATTR_LANG, LOCALE_RU_RU);
            }
            else {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute(ATTR_LANG, LOCALE_EN);
            }
        } else {

            if (session.getAttribute(PARAM_LANG) == null) {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute(ATTR_LANG, LOCALE_EN);
            }
            else if (session.getAttribute(PARAM_LANG).equals(LOCALE_EN)) {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute(ATTR_LANG, LOCALE_EN);
            }
            else if (session.getAttribute(PARAM_LANG).equals(LOCALE_RU_RU)) {
                Locale.setDefault(ruRU);
                langManager.changeResource(ruRU);
                session.setAttribute(ATTR_LANG, LOCALE_RU_RU);
            }
            else {
                Locale.setDefault(Locale.ENGLISH);
                langManager.changeResource(Locale.ENGLISH);
                session.setAttribute(ATTR_LANG, LOCALE_EN);
            }
        }

    }

}
