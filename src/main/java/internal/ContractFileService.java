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

/**
 * Specific service which works with contract container.
 */
public class ContractFileService implements FileService<Container<Contract>> {

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
     * @throws IOException when error occurs while opening file or CSV file format is invalid|
     * @return Contract container with values from given file
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
                container.add ( getContract ( line.split ( ",\t" ) ) );

            }
        }
        catch ( ParseException e ) {
        throw new IOException ( "Wrong csv formatting" );
           }
        return container;
    }
    /**
     * inner method to read one line of file.
     *
     * @param line line from file whic was turned to array
     * @throws ParseException when error occurs while parsing date
     * @return Contract parsed from line
     */
    private Contract getContract ( String[] line ) throws ParseException {

        switch ( line[ 0 ] ) {
            case "TelevisionContract":
                return new TelevisionContract (
                        Integer.valueOf ( line[ 1 ] ),
                        DateUtils.getDate ( line[ 2 ] ),
                        DateUtils.getDate ( line[ 3 ] ),
                        new Client (
                                Integer.valueOf ( line[ 4 ] ),
                                line[ 5 ],
                                DateUtils.getDate ( line[ 6 ] ),
                                Sex.valueOf ( line[ 7 ] ),
                                Integer.valueOf ( line[ 8 ] ) ),
                        ChannelPackage.valueOf ( line[ 9 ] )
                );
            case "InternetContract":
                return new InternetContract (
                        Integer.valueOf ( line[ 1 ] ),
                        DateUtils.getDate ( line[ 2 ] ),
                        DateUtils.getDate ( line[ 3 ] ),
                        new Client (
                                Integer.valueOf ( line[ 4 ] ),
                                line[ 5 ],
                                DateUtils.getDate ( line[ 6 ] ),
                                Sex.valueOf ( line[ 7 ] ),
                                Integer.valueOf ( line[ 8 ] ) ),
                        Integer.valueOf ( line[ 9 ] )
                );
            case "CellularContract":
                return new CellularContract (
                        Integer.valueOf ( line[ 1 ] ),
                        DateUtils.getDate ( line[ 2 ] ),
                        DateUtils.getDate ( line[ 3 ] ),
                        new Client (
                                Integer.valueOf ( line[ 4 ] ),
                                line[ 5 ],
                                DateUtils.getDate ( line[ 6 ] ),
                                Sex.valueOf ( line[ 7 ] ),
                                Integer.valueOf ( line[ 8 ] ) ),
                        Integer.valueOf ( line[ 9 ] ),
                        Integer.valueOf ( line[ 10 ] ),
                        Integer.valueOf ( line[ 11 ] )
                );
            default:
                return null;

        }
    }


}
