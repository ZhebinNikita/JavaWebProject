package by.epam.project.model.exception;



public class ProjectException extends Exception{

    private static final long serialVersionUID = 1L;

    public ProjectException(String message, Exception e){
        super(message, e);
    }

    public ProjectException(Exception e){
        super(e);
    }

}
