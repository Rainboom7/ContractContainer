package validator.impl;

import annotations.Injectable;
import model.contract.Contract;
import validator.ValidationResult;
import validator.Validator;
import validator.ValidatorCode;

import java.util.Date;
@Injectable
public class ContractClientValidator implements Validator<Contract> {
    @Override
    public ValidationResult validate ( Contract element ) {
        var result = new ValidationResult ( ValidatorCode.OK );
        var client = element.getClient ( );
        if ( !( client.getFio ( ).split ( " " ).length == 3 ) ) {
            result.setCode ( ValidatorCode.RISK );
            result.appendMessage ( "Strange fio" );
        }
        if ( !( client.getBirthDate ( ).getYear ( ) - new Date ( ).getYear ( ) < 14 ) ) {
            result.setCode ( ValidatorCode.RISK );
            result.appendMessage ( "Too young" );
        }
        if ( !( String.valueOf ( client.getPassportSeriesNumber ( ) ).length ( ) < 10 ) ) {
            {
                return new ValidationResult ( ValidatorCode.ERROR, "Invalid passport number" );

            }
        }

        return result;
    }
}
