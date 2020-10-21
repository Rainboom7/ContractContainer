import container.ContractContainer;
import model.client.Client;
import model.client.Sex;
import model.contract.ChannelPackage;
import model.contract.InternetContract;
import model.contract.TelevisionContract;

import java.util.Date;

public class Main {
    public static void main ( String[] args ) {
        ContractContainer contractContainer = new ContractContainer ( );
        for (int i = 0; i < 6; i++)
            contractContainer.add ( new TelevisionContract ( i, new Date ( ), new Date ( ), new Client ( i, "IVAN", new Date ( ), Sex.MALE, 12323112 ), ChannelPackage.CHILD ) );
        for (int i = 6; i < 9; i++)
            contractContainer.add ( new InternetContract ( i, new Date ( ), new Date ( ), new Client ( i, "IVAN", new Date ( ), Sex.MALE, 12323112 ), 1232 ) );
        System.out.println ( contractContainer );
        contractContainer.deleteAt ( 5 );
        System.out.println (  contractContainer.getById ( 3 ) );



    }


}
