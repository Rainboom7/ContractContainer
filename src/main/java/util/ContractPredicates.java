package util;

import model.client.Client;
import model.contract.Contract;

import java.util.Date;
import java.util.function.Predicate;

public class ContractPredicates {
    public static Predicate<Contract> byDateAfter ( Date begin ) {
        return c -> c.getBeginningDate ( ).after ( begin );
    }

    public static Predicate<Contract> byDateBetween ( Date begin, Date end ) {
        return c ->
                c.getBeginningDate ( ).after ( begin )
                        &&
                        c.getEndingDate ( ).before ( end );
    }

    public static Predicate<Contract> byClient ( Client client ) {
        return c -> c.getClient ( ).equals ( client );
    }

}
