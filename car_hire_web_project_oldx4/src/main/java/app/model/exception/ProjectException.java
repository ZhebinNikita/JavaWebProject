package app.model.exception;


/** Throw/Rethrow to the Controller */
public class ProjectException extends Exception{

    private static final long serialVersionUID = 1L;

    public ProjectException(String message, Exception e){
        super(message, e);
    }

}
