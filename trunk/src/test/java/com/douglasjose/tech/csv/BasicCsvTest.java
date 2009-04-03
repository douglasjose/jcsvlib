package com.douglasjose.tech.csv;

import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Douglas Rodrigues
 */
public class BasicCsvTest extends TestCase {

    public void testSimpleCsv() throws Exception {
        Csv csv = CsvFactory.createMSOfficeCsv();
        InputStream is = new FileInputStream(".\\src\\test\\resources\\simple.csv");
        assertNotNull("No CVS file to read from", is);

        csv.load(is);
        assertEquals("Wrong number of columns", 3, csv.getColumns());
        assertEquals("Wrong number of rows", 4, csv.getRows());
        assertEquals("Wrong content retrieved", "e", csv.get(1,1));


        try {
            csv.get(5, 1);
            fail("Reading from inexisting row not throwing exception");
        } catch (IndexOutOfBoundsException e) {
            // good
        }
        try {
            csv.get(1, 5);
            fail("Reading from inexisting column not throwing exception");
        } catch (IndexOutOfBoundsException e) {
            // good
        }
        is.close();
    }
}
