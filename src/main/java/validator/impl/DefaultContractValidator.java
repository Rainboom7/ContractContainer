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
        if ( element.getEndingDate ( ).after ( element.getEndingDate ( ) ) )
            validationResult += 50 / 100;
        else
            validationResult += 5 / 100;
        if ( endingCalendar.get ( Calendar.YEAR ) - beginningCalendar.get ( Calendar.YEAR ) == 0
                &&
                endingCalendar.get ( Calendar.MONTH ) - beginningCalendar.get ( Calendar.MONTH ) < 1
        )
            validationResult += 30 / 100;
        else
            validationResult += 50 / 100;
        return validationResult;
    }
}
