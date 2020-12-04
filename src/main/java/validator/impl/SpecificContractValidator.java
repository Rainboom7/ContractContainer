package validator.impl;

import annotations.Injectable;
import model.contract.CellularContract;
import model.contract.Contract;
import model.contract.InternetContract;
import validator.ValidationResult;
import validator.Validator;
import validator.ValidatorCode;
@Injectable
public class SpecificContractValidator implements Validator<Contract> {
    @Override
    public ValidationResult validate ( Contract element ) {
        var result = new ValidationResult ( ValidatorCode.OK );

        if ( element instanceof CellularContract ) {
            var cellularContract = (CellularContract) element;
            if ( cellularContract.getMinutes ( ) < 60 ) {
                result.setCode ( ValidatorCode.RISK );
                result.appendMessage ( "Too little minutes" );

            }
            if ( cellularContract.getMegabytes ( ) < 100 ) {
                result.setCode ( ValidatorCode.RISK );
                result.appendMessage ( "Too little megabytes" );
            }
            if ( cellularContract.getSmsAmount ( ) < 5 ) {
                result.setCode ( ValidatorCode.RISK );
                result.appendMessage ( "Too little sms" );
            }
        } else if ( element instanceof InternetContract ) {
            var internetContract = (InternetContract) element;
            if ( internetContract.getMaxMegabytesSpeed ( ) < 1 ) {
                result.setCode ( ValidatorCode.RISK );
                result.appendMessage ( "Too little speed" );
            }
        }

        return result;

    }
}
