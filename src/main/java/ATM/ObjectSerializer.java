package ATM;

import java.io.*;

/**
 * A utility class used to serialize and deserialize objects of a specific type.
 *
 * @param <T> The type of the object to serialize.
 */
class ObjectSerializer<T extends Serializable> {

    /**
     * Deserialize and return an object in a given file.
     *
     * @param filename The file containing the serialized object.
     * @return the deserialized object.
     * @throws IOException            if an I/O error occurs while reading the object.
     * @throws ClassNotFoundException if the class of the serialized object could not be found.
     * @throws ClassCastException     if the deserialized object is of the wrong type.
     */
    @SuppressWarnings("unchecked")
    T readObject(String filename) throws IOException, ClassNotFoundException, ClassCastException {
        FileInputStream inputFile = new FileInputStream(filename);
        BufferedInputStream buffer = new BufferedInputStream(inputFile);
        ObjectInputStream objectFile = new ObjectInputStream(buffer);
        T object = (T) objectFile.readObject();
        objectFile.close();
        buffer.close();
        inputFile.close();
        return object;
    }

    /**
     * Serialize an object and write it to a given file.
     *
     * @param object   The object to serialize.
     * @param filename The file to write to.
     * @throws IOException if an I/O exception occurs while writing to the file.
     */
    void writeObject(T object, String filename) throws IOException {
        FileOutputStream outputFile = new FileOutputStream(filename);
        BufferedOutputStream buffer = new BufferedOutputStream(outputFile);
        ObjectOutputStream objectFile = new ObjectOutputStream(buffer);
        objectFile.writeObject(object);
        objectFile.close();
        buffer.close();
        outputFile.close();
    }

}
