package internal;

import container.Container;
import container.ContractContainer;
import model.client.Client;
import model.client.Sex;
import model.contract.*;
import util.DateUtils;
import validator.Validator;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Specific service which works with contract container.
 */
public class ContractFileService implements FileService<Container<Contract>> {
    private List<Validator<Contract>> validators;

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
                System.out.println ( "array is" );
                var arr = line.split ( ",\t" );
                for (var elem : arr
                ) {
                    System.out.println ( "|" + elem + "|" );
                }
                {
                    var contract = getContract ( line.split ( ",\t" ) );
                    if ( shouldAdd ( contract ) && !contains ( contract, container ) )
                        container.add ( contract );
                    else
                        System.out.println ( "Contract was'nt added:\n" + contract.toString ( ) );

                }


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

    /**
     * Defines if contract read from file should be added to container
     *
     * @param contract contract to validate
     * @return true if contract validation defined that it can be added
     */

    private boolean shouldAdd ( Contract contract ) {
        if(this.validators.size ()==0)
            return true;
        double validationResult = 0;
        for (var validator : this.validators
        ) {
            validationResult += validator.validate ( contract );

        }

        validationResult /= this.validators.size ( );
        return ( validationResult > 0.5 );
    }

    /**
     * Checks if contract is present in container
     *
     * @param contract  contract to check
     * @param container container  to look through
     * @return true if contract is present in container
     */
    private boolean contains ( Contract contract, Container<Contract> container ) {
        for (var containerContract : container.getAll ( )
        ) {
            if ( contract.equals ( containerContract ) )
                return true;
        }
        return false;

    }


}
