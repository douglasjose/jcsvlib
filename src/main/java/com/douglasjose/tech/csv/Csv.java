package com.douglasjose.tech.csv;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Data structure to represent a CSV (comma separated values) file as a matrix in memory.
 * The data in the structure is addressed through indexes in the form <code>(row, column)</code>.
 *
 * @author Douglas Rodrigues
 */
public interface Csv {

    /**
     * Add content to a indexed position in the matrix.
     *
     * @param row Row index
     * @param column Column index
     * @param content Content to be stored
     */
    void add(int row, int column, String content);

    /**
     * Retrieves the content from a given position.
     * Returns the empty string if there is no content in the specified position.
     *
     * @param row Row index
     * @param column Column index
     * @return Stored content
     */
    String get(int row, int column);

    /**
     * Removes the content from a given position.
     *
     * @param row Row index
     * @param column Column index
     * @return If the data structure has changed after this operation
     */
    boolean remove(int row, int column);

    /**
     * Loads the file from a stream.
     *
     * @param is Stream to load the file from
     * @throws IOException If the data could not be read from the stream
     */
    void load(InputStream is) throws IOException;

    /**
     * Writes the file to a stream.
     *
     * @param os Stream to write the file to
     * @throws IOException If the data could not be written to the stream
     */
    void store(OutputStream os) throws IOException;

    /**
     * Retrieves the number of columns in the matrix.
     *
     * @return Number of columns
     */
    int getColumns();

    /**
     * Retrieves the number of rows in the matrix.
     *
     * @return Number of rows
     */
    int getRows();
}
