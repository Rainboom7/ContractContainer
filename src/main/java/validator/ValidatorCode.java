package validator;

public enum ValidatorCode {
    OK ( "Ok" ), RISK ( "Risk" ), ERROR ( "Error" );


    private String message;

    ValidatorCode ( String message ) {
        this.message = message;
    }

    public String getMessage ( ) {
        return message;
    }
}
