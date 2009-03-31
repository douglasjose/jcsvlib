package com.douglasjose.tech.csv;

import junit.framework.TestCase;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;

/**
 * @author Douglas Rodrigues
 */
public class WriteCsvTest extends TestCase {

    public void testWriteCsv() throws Exception {
        Csv csv = CsvFactory.createMSOfficeCsv();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                csv.add(i, j, "content");
            }
        }

        File file = File.createTempFile("writeCsvTest", ".txt");
        System.out.println("Temp file: " + file.getAbsolutePath());
        OutputStream os = new FileOutputStream(file);

        csv.store(os);
        os.close();

        assertTrue("Wrong file content: " + file.getAbsolutePath(), TestUtils.identicalFiles(file,
                new File(".\\src\\test\\resources\\testWriteCsv.txt")));
    }

    public void testFieldSeparatorInContent() {
        
    }

}
