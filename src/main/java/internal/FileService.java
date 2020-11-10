package internal;

import java.io.IOException;

/**
 * The interface of service to perform working with files.
 */
public interface FileService<T> {
    void createFile(T fromElement,String fileName) throws IOException;
    T readFromFile(String filePath) throws IOException;
}
