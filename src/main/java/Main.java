import container.Container;
import container.ContractContainer;
import internal.ContractFileService;
import model.client.Client;
import model.client.Sex;
import model.contract.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DateUtils;
import validator.impl.ContractClientValidator;
import validator.impl.DefaultContractValidator;
import validator.impl.SpecificContractValidator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private  static Logger logger = LogManager.getLogger ();
    public static void main ( String[] args ) throws Exception {

        ContractContainer contractContainer = new ContractContainer ( );
        for (int i = 0; i < 3; i++)
            contractContainer.add ( new TelevisionContract ( i, new Date ( ), new Date ( ), new Client ( i, "IVAN", new Date ( ), Sex.MALE, 12323112 ), ChannelPackage.CHILD ) );
        for (int i = 3; i < 5; i++)
            contractContainer.add ( new InternetContract ( i, new Date ( ), new Date ( ), new Client ( i, "IVAN", new Date ( ), Sex.MALE, 12323112 ), 1232 ) );
        for (int i = 5; i < 8; i++)
            contractContainer.add ( new CellularContract ( i, new Date ( ), new Date ( ), new Client ( i, "IVAN", new Date ( ), Sex.MALE, 12323112 ), 12,1233,32 ) );

        ContractFileService service = new ContractFileService ();

        service.createFile ( contractContainer,"a.csv" );
       Container<Contract>newContainer = new ContractContainer (  );
       service.addValidator ( new ContractClientValidator () );
       service.addValidator ( new DefaultContractValidator () );
       service.addValidator ( new SpecificContractValidator () );
       service.readFromFile ( "a.csv" ,newContainer);
        logger.debug ( newContainer );





    }


}
