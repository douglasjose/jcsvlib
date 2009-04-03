package com.douglasjose.tech.csv;

import junit.framework.TestCase;

import java.io.*;

/**
 * @author Douglas Rodrigues
 */
public class CsvParsingTest extends TestCase {

    public void testWriteCsv() throws Exception {
        Csv csv = CsvFactory.createMSOfficeCsv();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                csv.add(i, j, "content");
            }
        }

        File file = File.createTempFile("writeCsvTest", ".txt");
        OutputStream os = new FileOutputStream(file);

        csv.store(os);
        os.close();

        assertTrue("Wrong file content: " + file.getAbsolutePath(), TestUtils.identicalFiles(file,
                new File(".\\src\\test\\resources\\testWriteCsv.txt")));
        file.deleteOnExit();
    }

    public void testFieldSeparatorInContent() throws Exception {
        Csv csv = CsvFactory.createMSOfficeCsv();
        String content = "A,B"; // Serializes to _"A,B"_
        csv.add(0,0, content);

        File file = File.createTempFile("writeCsvTest", ".txt");
        OutputStream os = new FileOutputStream(file);

        csv.store(os);
        os.close();

        assertTrue("Wrong file content: " + file.getAbsolutePath(), TestUtils.identicalFiles(file,
                new File(".\\src\\test\\resources\\testFieldSeparatorInContent.txt")));

        InputStream is = new FileInputStream(file);

        csv.load(is);
        is.close();

        assertEquals("Wrong content restored from file", content, csv.get(0,0));

        file.deleteOnExit();
    }

    public void testFieldDelimiterInContent() throws Exception {
        Csv csv = CsvFactory.createMSOfficeCsv();
        String content = "A\"B"; // Serializes to _"A""B"_
        csv.add(0,0, content); 

        File file = File.createTempFile("writeCsvTest", ".txt");
        OutputStream os = new FileOutputStream(file);

        csv.store(os);
        os.close();

        assertTrue("Wrong file content: " + file.getAbsolutePath(), TestUtils.identicalFiles(file,
                new File(".\\src\\test\\resources\\testFieldDelimiterInContent.txt")));

        InputStream is = new FileInputStream(file);

        csv.load(is);
        is.close();

        assertEquals("Wrong content restored from file", content, csv.get(0,0));

        file.deleteOnExit();
    }

}
