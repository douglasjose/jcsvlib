package com.douglasjose.tech.csv;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Utility class used to create {@link com.douglasjose.tech.csv.Csv} implementations based in a
 * specific format.
 * Can also provide synchronized <code>Csv</code> implementations.
 *
 * @author Douglas Rodrigues
 */
public class CsvFactory {

    private CsvFactory() {} // Do not instantiate this class

    /**
     * Creates a new Microsoft Office/LibreOffice/OpenOffice compliant Csv data structure.
     *
     * @return Csv implementation
     */
    public static Csv createOfficeCsv() {
        return new BasicCsv(new CustomDelimitersCsvParser(",", "\""));
    }

    /**
     * Creates a Csv with customized delimiters.
     *
     * @param fieldSeparator String used to separate fields in the file
     * @param textDelimiter String used as text delimiter
     * @return Customized Csv implementation
     */
    public static Csv createCsvWithCustomDelimiters(String fieldSeparator, String textDelimiter) {
        return new BasicCsv(new CustomDelimitersCsvParser(fieldSeparator, textDelimiter));
    }
    /**
     * Creates a synchronized (thread-safe) <code>Csv</code> implementation, backed by the provided
     * <code>Csv</code> object.
     *
     * @param csv The data structure to be wrapped by the synchronized <code>Csv</code>
     * @return A thread-safe <code>Csv</code>
     */
    public static Csv synchronizedCsv(Csv csv) {
        return new SynchronizedCsv(csv);
    }

    static class SynchronizedCsv implements Csv {

        private final Csv csv;
        private final Object mutex;

        public SynchronizedCsv(Csv csv) {
            this.csv = csv;
            mutex = this;
        }

        public void add(int row, int column, String content) {
            synchronized(mutex) {
                csv.add(row, column, content);
            }
        }

        public String get(int row, int column) {
            synchronized(mutex) {
                return csv.get(row, column);
            }
        }

        public boolean remove(int row, int column) {
            synchronized(mutex) {
                return csv.remove(row, column);
            }
        }

        public void load(InputStream is) throws IOException {
            synchronized(mutex) {
                csv.load(is);
            }
        }

        public void store(OutputStream os) throws IOException {
            synchronized(mutex) {
                csv.store(os);
            }
        }

        public int getColumns() {
            synchronized(mutex) {
                return csv.getColumns();
            }
        }

        public int getRows() {
            synchronized(mutex) {
                return csv.getRows();
            }
        }
    }

}
