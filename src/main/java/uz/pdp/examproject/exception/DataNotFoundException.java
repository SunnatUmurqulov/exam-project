package uz.pdp.examproject.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message){
        super(message);
    }
}
