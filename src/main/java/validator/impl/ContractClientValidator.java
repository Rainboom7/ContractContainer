package validator.impl;

import model.contract.Contract;
import validator.Validator;

import java.util.Date;

public class ContractClientValidator implements Validator<Contract> {
    @Override
    public double validate ( Contract element ) {
        double validationResult = 0;
        var client = element.getClient ( );
        if ( client.getFio ( ).split ( " " ).length == 3 ) {
            validationResult = 0.35;
        } else {
            validationResult += 0.2;
        }
        if ( client.getBirthDate ( ).getYear ( ) - new Date ( ).getYear ( ) < 14 ) {
            validationResult += 0.35;
        } else {
            validationResult += 0.2;
        }
        if ( String.valueOf ( client.getPassportSeriesNumber ( ) ).length ( ) < 10 ) {
            validationResult += 0.15;
        } else {
            validationResult += 0.3;
        }

        return validationResult;

    }
}
