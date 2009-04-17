package com.douglasjose.tech.csv;

import junit.framework.TestCase;

import java.io.*;

/**
 * @author Douglas Rodrigues
 */
public class CsvParsingTest extends TestCase {

    public void testWriteCsv() throws Exception {
        Csv csv = CsvFactory.createOfficeCsv();

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
        Csv csv = CsvFactory.createOfficeCsv();
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
        Csv csv = CsvFactory.createOfficeCsv();
        String content = "A\"B"; // Serializes to _"A""B"_
        String content2 = "\"";
        csv.add(0,0, content);
        csv.add(1,0, content2);

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
        assertEquals("Wrong content restored from file", content2, csv.get(1,0));

        file.deleteOnExit();
    }

    public void testSemicolonAsFieldSeparator() throws Exception {
        Csv csv = CsvFactory.createCsvWithCustomDelimiters(";", "\"");
        String content1 = "A;B";
        String content2 = "A\"B";

        csv.add(0, 0, content1);
        csv.add(0, 1, content2);

        File file = File.createTempFile("writeCsvTest", ".txt");
        OutputStream os = new FileOutputStream(file);

        csv.store(os);
        os.close();

        assertTrue("Wrong file content: " + file.getAbsolutePath(), TestUtils.identicalFiles(file,
                new File(".\\src\\test\\resources\\testSemicolonAsFieldSeparator.txt")));

        InputStream is = new FileInputStream(file);

        csv.load(is);
        is.close();

        assertEquals("Wrong content restored from file", content1, csv.get(0,0));
        assertEquals("Wrong content restored from file", content2, csv.get(0,1));

        file.deleteOnExit();
    }

    public void testMulticharacterSeparators() throws Exception {
        Csv csv = CsvFactory.createCsvWithCustomDelimiters("[F]", "[T]");

        String content1 = "content";
        String content2 = "cont[F]ent";
        String content3 = "[F]";
        String content4 = "[T]";
        String content5 = "[T]content[T]";


        csv.add(0, 0, content1);
        csv.add(0, 1, content2);
        csv.add(0, 2, content3);
        csv.add(1, 0, content4);
        csv.add(1, 1, content5);

        File file = File.createTempFile("writeCsvTest", ".txt");
        OutputStream os = new FileOutputStream(file);

        csv.store(os);
        os.close();

        assertTrue("Wrong file content: " + file.getAbsolutePath(), TestUtils.identicalFiles(file,
                new File(".\\src\\test\\resources\\testMulticharacterSeparators.txt")));

        InputStream is = new FileInputStream(file);

        csv.load(is);
        is.close();

        assertEquals("Wrong content restored from file", content1, csv.get(0,0));
        assertEquals("Wrong content restored from file", content2, csv.get(0,1));
        assertEquals("Wrong content restored from file", content3, csv.get(0,2));
        assertEquals("Wrong content restored from file", content4, csv.get(1,0));
        assertEquals("Wrong content restored from file", content5, csv.get(1,1));
        assertEquals("Wrong content restored from file", "", csv.get(1,2));

        file.deleteOnExit();

    }

}

