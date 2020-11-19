package internal;

import java.io.IOException;

/**
 * The interface of service to perform working with files.
 */
public interface FileService<T> {
    void createFile(T fromElement,String fileName) throws IOException;
    void readFromFile(String filePath, T element ) throws IOException;
}
