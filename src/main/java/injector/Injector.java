package injector;

import annotations.Inject;
import annotations.Injectable;
import annotations.InjectorConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@InjectorConfig({"util.sorter", "validator.impl"})
public class Injector {
    private List<Class> configurationClasses;
    private static Logger logger = LogManager.getLogger ( );


    public Injector ( ) {
        this.configurationClasses = new ArrayList<> ( );

        var annotationPackages = this.getClass ( ).getAnnotation ( annotations.InjectorConfig.class ).value ( );
        try {
            for (var annotationPackage : annotationPackages
            ) {
                getPackageClasses ( annotationPackage.replace ( '.', '/' ) );
            }
            logger.debug ( this.configurationClasses );

        } catch ( IOException e ) {
            logger.error ( "Error opening package, error:", e );
            throw new RuntimeException ( e );
        } catch ( ClassNotFoundException e ) {
            logger.error ( "Error finding class in package, error:", e );
            throw new RuntimeException ( e );
        }
    }

    public <T> T inject ( T object ) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {

        var annotatedFields = Arrays.stream ( object.getClass ( ).getDeclaredFields ( ) ).filter ( f -> f.getAnnotation ( Inject.class ) != null ).collect ( Collectors.toList ( ) );
        for (var field : annotatedFields
        ) {
            if ( field.trySetAccessible ( ) ) {
                if ( field.getType ( ).isAssignableFrom ( List.class ) )
                    field.set ( object, injectList ( field ) );
                else {
                    field.set ( object, injectObject ( field ) );
                }


            }


        }
        return object;
    }

    void getPackageClasses ( String packageName ) throws IOException, ClassNotFoundException {

        var dataInputStream = new DataInputStream ( (InputStream) Objects.requireNonNull ( this.getClass ( ).getClassLoader ( ).getResource ( packageName ) ).getContent ( ) );
        var bufferedReader = new BufferedReader ( new InputStreamReader ( dataInputStream ) );
        String line = null;
        while ( ( line = bufferedReader.readLine ( ) ) != null ) {
            if ( line.endsWith ( ".class" ) ) {
                var className = packageName.replace ( "/", "." ) + "." + line.substring ( 0, line.length ( ) - 6 );
                var createdClass = Class.forName ( className );
                if ( createdClass.isAnnotationPresent ( Injectable.class ) )
                    this.configurationClasses.add ( Class.forName ( className ) );

            }

        }

    }

    private List<Object> injectList ( Field field ) {
        var injectedList = new ArrayList<> ( );
        var parametrizedType = (ParameterizedType) field.getGenericType ( );

        for (var typeArgument : parametrizedType.getActualTypeArguments ( )) {
            for (var presentClass : this.configurationClasses
            ) {
                try {
                    List<Type> interfaces;
                    //Checking if type of list is generic itself
                    if ( typeArgument.toString ( ).contains ( "<" ) && typeArgument.toString ( ).contains ( ">" ) )
                        interfaces = Arrays.asList ( presentClass.getGenericInterfaces ( ) );
                    else
                        interfaces = Arrays.asList ( presentClass.getInterfaces ( ) );
                    if ( interfaces.contains ( typeArgument ) )
                        injectedList.add ( presentClass.getConstructor ( ).newInstance ( ) );
                } catch ( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
                    e.printStackTrace ( );
                }
            }
        }
        return injectedList;
    }

    private Object injectObject ( Field field ) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        var type = field.getType ( );
        var genericType = field.getGenericType ( );
        logger.debug ( genericType );
        List<Class> suitableClasses;
        if ( genericType != null )
            suitableClasses = this.configurationClasses.stream ( ).filter ( f -> Arrays.asList ( f.getGenericInterfaces ( ) ).contains ( genericType ) ).collect ( Collectors.toList ( ) );
        else
            suitableClasses = this.configurationClasses.stream ( ).filter ( f -> Arrays.asList ( f.getInterfaces ( ) ).contains ( type ) ).collect ( Collectors.toList ( ) );
        if ( suitableClasses.size ( ) != 1 )
            throw new ClassNotFoundException ( "More than one suitable class for injecting" );
        return suitableClasses.get ( 0 ).getConstructor ( ).newInstance ( );

    }

}
