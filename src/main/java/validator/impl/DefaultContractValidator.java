package validator.impl;

import annotations.Injectable;
import model.contract.Contract;
import validator.ValidationResult;
import validator.Validator;
import validator.ValidatorCode;

import java.util.Calendar;
import java.util.GregorianCalendar;
@Injectable
public class DefaultContractValidator implements Validator<Contract> {
    @Override
    public ValidationResult validate ( Contract element ) {
        var result = new ValidationResult ( ValidatorCode.OK );

        var beginningCalendar = new GregorianCalendar ( );
        var endingCalendar = new GregorianCalendar ( );
        beginningCalendar.setTime ( element.getBeginningDate ( ) );
        endingCalendar.setTime ( element.getEndingDate ( ) );
        if ( ( element.getBeginningDate ( ).after ( element.getEndingDate ( ) ) ) ) {
            return new ValidationResult ( ValidatorCode.ERROR, "Invalid contract date" );
        }
        if ( !( endingCalendar.get ( Calendar.YEAR ) - beginningCalendar.get ( Calendar.YEAR ) == 0
                &&
                endingCalendar.get ( Calendar.MONTH ) - beginningCalendar.get ( Calendar.MONTH ) < 1
        ) ) {
            result.setCode ( ValidatorCode.RISK );
            result.appendMessage ( "Too short contract date length" );
        }
        return result;
    }
}
