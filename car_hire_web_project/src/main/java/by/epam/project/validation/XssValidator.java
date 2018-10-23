package by.epam.project.validation;

public final class XssValidator implements Validator{

    public static String xssValidate(String str) {

        if (str.contains("</script>") || str.contains("<script>")) {
            str = "";
        }

        return str;

    }

}
