package util;

import model.contract.Contract;

import java.util.Comparator;
import java.util.Date;

public class ContractComparators {
    public  static Comparator<? super Contract> byId(){
        return  Comparator.nullsLast ( Comparator.comparing ( Contract::getId ) );
    }
    public  static Comparator<? super Contract> byClientName(){
        return Comparator.nullsLast ( Comparator.comparing ( c -> c.getClient ( ).getFio ( ), String::compareToIgnoreCase ) );
    }
    public  static Comparator<? super Contract> byClientId(){
        return   Comparator.nullsLast ( Comparator.comparing ( c -> c.getClient ( ).getId ( ), Integer::compareTo ) );
    }
    public  static Comparator<? super Contract> byBeginningDate(){
        return    Comparator.nullsLast ( Comparator.comparing ( Contract::getBeginningDate, Date::compareTo ) );
    }
    public  static Comparator<? super Contract> byEndingDate(){
        return    Comparator.nullsLast ( Comparator.comparing ( Contract::getEndingDate, Date::compareTo ) );
    }

}
