package internal;

import annotations.Inject;
import container.Container;
import model.client.Client;
import model.client.Sex;
import model.contract.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DateUtils;
import validator.Validator;
import validator.ValidatorCode;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Specific service which works with contract container.
 */
public class ContractFileService implements FileService<Container<Contract>> {
    @Inject
    private List<Validator<Contract>> validators;
    private static Logger logger = LogManager.getLogger ( );

    public ContractFileService ( ) {
        this.validators = new ArrayList<> ( );
    }

    public ContractFileService ( List<Validator<Contract>> validators ) {
        this.validators = validators;
    }

    /**
     * Adds given validator to list of validators.
     *
     * @param validator validator to add
     */
    public void addValidator ( Validator<Contract> validator ) {
        this.validators.add ( validator );
    }

    private HashMap<Integer, Client> existingClients = new HashMap<> ( );


    /**
     * Creates file with given name and values from given container.
     *
     * @param fromElement container which will be used to create file from
     * @param filename    name of file you want to create
     * @throws IOException when error occurs while creating file
     */
    @Override

    public void createFile ( Container<Contract> fromElement, String filename ) throws IOException {

        File createdFile = new File ( filename );
        if ( createdFile.createNewFile ( ) ) {
            BufferedWriter writer = new BufferedWriter ( new FileWriter ( filename, true ) );
            for (var contract : fromElement.getAll ( )
            ) {
                writer.write ( contract.toString ( ) );
            }
            writer.close ( );

        }
    }

    /**
     * Reads values from given CSV file into contract container.
     *
     * @param filePath path to file from which you want to read values
     * @return Contract container with values from given file
     * @throws IOException when error occurs while opening file or CSV file format is invalid|
     */
    @Override
    public void readFromFile ( String filePath, Container<Contract> container ) throws IOException {
        try (BufferedReader reader = new BufferedReader ( new FileReader ( filePath, Charset.defaultCharset ( ) ) )) {

            String line;
            while ( ( line = reader.readLine ( ) ) != null ) {
                var arr = line.split ( ",\t" );

                {
                    var contract = getContract ( line.split ( ",\t" ) );
                    if ( shouldAdd ( contract ) )
                        container.add ( contract );

                }
                var contract = getContract ( line.split ( ",\t" ) );
                container.add ( contract );


            }
        } catch ( ParseException e ) {
            throw new IOException ( "Wrong csv formatting" );
        }
    }

    /**
     * inner method to read one line of file.
     *
     * @param line line from file whic was turned to array
     * @return Contract parsed from line
     * @throws ParseException when error occurs while parsing date
     */
    private Contract getContract ( String[] line ) throws ParseException {
        var client = getClient ( Integer.valueOf ( line[ 4 ] ) ).orElse (
                new Client (
                        Integer.valueOf ( line[ 4 ] ),
                        line[ 5 ],
                        DateUtils.getDate ( line[ 6 ] ),
                        Sex.valueOf ( line[ 7 ] ),
                        Integer.valueOf ( line[ 8 ] ) )
        );
        addClient ( Integer.valueOf ( line[ 4 ] ), client );

        switch ( line[ 0 ] ) {
            case "TelevisionContract":
                return new TelevisionContract (
                        Integer.valueOf ( line[ 1 ] ),
                        DateUtils.getDate ( line[ 2 ] ),
                        DateUtils.getDate ( line[ 3 ] ),
                        client
                        ,
                        ChannelPackage.valueOf ( line[ 9 ] )
                );
            case "InternetContract":
                return new InternetContract (
                        Integer.valueOf ( line[ 1 ] ),
                        DateUtils.getDate ( line[ 2 ] ),
                        DateUtils.getDate ( line[ 3 ] ),
                        client,
                        Integer.valueOf ( line[ 9 ] )
                );
            case "CellularContract":
                return new CellularContract (
                        Integer.valueOf ( line[ 1 ] ),
                        DateUtils.getDate ( line[ 2 ] ),
                        DateUtils.getDate ( line[ 3 ] ),
                        client,
                        Integer.valueOf ( line[ 9 ] ),
                        Integer.valueOf ( line[ 10 ] ),
                        Integer.valueOf ( line[ 11 ] )
                );
            default:
                return null;

        }
    }

    /**
     * Defines if contract read from file should be added to container
     *
     * @param contract contract to validate
     * @return true if contract validation defined that it can be added
     */

    private boolean shouldAdd ( Contract contract ) {
        if ( this.validators.size ( ) == 0 )
            return true;
        for (var validator : this.validators
        ) {
            var result = validator.validate ( contract );
            if ( result.getCode ( ).equals ( ValidatorCode.ERROR ) ) {
                logger.error ( "\nContract:" + contract + "\nwasn't added, error:\n" + result.getMessage ( ) );
                return false;
            } else if ( result.getCode ( ).equals ( ValidatorCode.RISK ) ) {
                logger.warn ( "\nContract:" + contract + "\nwas added but has errors:\n" + result.getMessage ( ) );
            }
        }
        return true;

    }

    /**
     * Checks if client  already have contracts
     *
     * @param clientId id of client
     * @return Optional of existing clinet
     */
    private Optional<Client> getClient ( int clientId ) {
        return Optional.ofNullable ( this.existingClients.get ( clientId ) );
    }

    /**
     * Puts created client in existing client map
     *
     * @param clientId contract to check
     * @param client   client
     */
    private void addClient ( int clientId, Client client ) {
        this.existingClients.put ( clientId, client );
    }


}
