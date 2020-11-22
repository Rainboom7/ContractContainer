package validator.impl;

import model.contract.Contract;
import validator.Validator;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DefaultContractValidator implements Validator<Contract> {
    @Override
    public double validate ( Contract element ) {
        var beginningCalendar = new GregorianCalendar ( );
        var endingCalendar = new GregorianCalendar ( );
        beginningCalendar.setTime ( element.getBeginningDate ( ) );
        endingCalendar.setTime ( element.getEndingDate ( ) );
        var validationResult = 0;
        if ( element.getEndingDate ( ).after ( element.getEndingDate ( ) ) ) {
            validationResult += 0.5;
        } else {
            validationResult += 0.05;
        }
        if ( endingCalendar.get ( Calendar.YEAR ) - beginningCalendar.get ( Calendar.YEAR ) == 0
                &&
                endingCalendar.get ( Calendar.MONTH ) - beginningCalendar.get ( Calendar.MONTH ) < 1
        ) {
            validationResult += 0.3;
        } else {
            validationResult += 0.5;
        }
        return validationResult;
    }
}
