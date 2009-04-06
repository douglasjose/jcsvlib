package com.douglasjose.tech.csv;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * The <code>CsvParser</code> class contains the functionality required to persist the
 * {@link com.douglasjose.tech.csv.Csv} data structure.
 * Implementations of this interface are expected to be components of implementations of the
 * <code>Csv</code> interface, isolating the persistence logic from any other implementation.
 * As there is no standard defining how a CSV file should be, this inteface allows a <code>Csv
 * </code> data structure to be persisted in different formats. 
 *
 * @author Douglas Rodrigues
 */
public interface CsvParser {

    /**
     * Writes a <code>Csv</code> data structure to a stream.
     *
     * @param csv Data structure to be persisted
     * @param os Stream to persist the data to
     * @throws IOException If the data cannot be persisted
     */
    public void writeFile(Csv csv, OutputStream os) throws IOException;

    /**
     * Loads a <code>Csv</code> data structure from a stream.
     *
     * @param csv Destination data structure
     * @param is Stream to read the data from
     * @throws IOException If the data cannot be read
     */
    public void loadFile(Csv csv, InputStream is) throws IOException;
}
