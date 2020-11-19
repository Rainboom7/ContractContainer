package validator.impl;

import model.contract.Contract;
import validator.Validator;

import java.util.Date;

public class ContractClientValidator implements Validator<Contract> {
    @Override
    public double validate ( Contract element ) {
        double validationResult = 0;
        var client = element.getClient ( );
        if ( client.getFio ( ).split ( " " ).length == 3 )
            validationResult += 35 / 100;
        else
            validationResult += 20 / 100;

        if ( client.getBirthDate ( ).getYear ( ) - new Date ( ).getYear ( ) < 14 )
            validationResult += 35 / 100;

        else
            validationResult += 20 / 100;

        if ( String.valueOf ( client.getPassportSeriesNumber ( ) ).length ( ) < 10 )
            validationResult += 15 / 100;
        else
            validationResult += 30 / 100;
        return validationResult;

    }
}
