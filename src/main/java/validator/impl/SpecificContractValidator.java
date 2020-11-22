package validator.impl;

import model.contract.CellularContract;
import model.contract.Contract;
import model.contract.InternetContract;
import validator.Validator;

public class SpecificContractValidator implements Validator<Contract> {
    @Override
    public double validate ( Contract element ) {
        double validationResult = 0;
        if ( element instanceof CellularContract ) {

            var cellularContract = (CellularContract) element;
            if ( cellularContract.getMinutes ( ) < 60 )
                validationResult += 0.3;
            else
                validationResult += 0.4;
            if ( cellularContract.getMegabytes ( ) < 100 )
                validationResult += 0.3;
            else
                validationResult += 0.4;
            if ( cellularContract.getSmsAmount ( ) < 5 )
                validationResult += 0.1;
            else
                validationResult += 0.2;
            return validationResult;
        } else if ( element instanceof InternetContract ) {
            var internetContract = (InternetContract) element;
            if ( internetContract.getMaxMegabytesSpeed ( ) < 1 )
                validationResult += 0.7;
            else
                validationResult += 1;
            return validationResult;
        } else return 100;
    }
}
