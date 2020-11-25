package internal;

import container.Container;
import container.ContractContainer;
import model.client.Client;
import model.client.Sex;
import model.contract.*;
import util.DateUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * Specific service which works with contract container.
 */
public class ContractFileService implements FileService<Container<Contract>> {
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
    public Container<Contract> readFromFile ( String filePath ) throws IOException {
        Container<Contract> container = new ContractContainer ( );
        try (BufferedReader reader = new BufferedReader ( new FileReader ( filePath, Charset.defaultCharset ( ) ) )) {

            String line;
            while ( ( line = reader.readLine ( ) ) != null ) {
                System.out.println ( "array is" );
                var arr = line.split ( ",\t" );
                for (var elem : arr
                ) {
                    System.out.println ( "|" + elem + "|" );
                }
                var contract = getContract ( line.split ( ",\t" ) );
                container.add ( contract );


            }
        } catch ( ParseException e ) {
            throw new IOException ( "Wrong csv formatting" );
        }
        return container;
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
