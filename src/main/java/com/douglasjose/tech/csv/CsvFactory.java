package com.douglasjose.tech.csv;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Douglas Rodrigues
 */
public class CsvFactory {

    private CsvFactory() {} // Do not instantiate this class

    public static Csv createMSOfficeCsv() {
        return new BasicCsv(new MSOfficeCsvParser());
    }

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

        public void remove(int row, int column) {
            synchronized(mutex) {
                csv.remove(row, column);
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
