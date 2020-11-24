package validator;

public class ValidationResult {
    ValidatorCode code;
    String message;

    public ValidationResult ( ValidatorCode code, String message ) {
        this.code = code;
        this.message = message;
    }

    public void appendMessage ( String newMessage ) {
        this.message += "\n" + newMessage;
    }

    public void setCode ( ValidatorCode code ) {
        this.code = code;
        this.message = code.getMessage ( );
    }

    public ValidationResult ( ValidatorCode code ) {
        setCode ( code );
    }

    public ValidatorCode getCode ( ) {
        return code;
    }

    public String getMessage ( ) {
        return message;
    }
}
