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
                validationResult += 30 / 100;
            else
                validationResult += 40 / 100;
            if ( cellularContract.getMegabytes ( ) < 100 )
                validationResult += 30 / 100;
            else
                validationResult += 40 / 100;
            if ( cellularContract.getSmsAmount ( ) < 5 )
                validationResult += 10 / 100;
            else
                validationResult += 20 / 100;
            return validationResult;
        } else if ( element instanceof InternetContract ) {
            var internetContract = (InternetContract) element;
            if ( internetContract.getMaxMegabytesSpeed ( ) < 1 )
                validationResult += 70 / 100;
            else
                validationResult += 1;
            return validationResult;
        } else return 100;
    }
}
