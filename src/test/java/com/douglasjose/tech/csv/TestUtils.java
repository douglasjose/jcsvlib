package com.douglasjose.tech.csv;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Douglas Rodrigues
 */
public class TestUtils {

    public static boolean identicalFiles(File f1, File f2) throws IOException {
        BufferedReader r1 = new BufferedReader(new FileReader(f1));
        BufferedReader r2 = new BufferedReader(new FileReader(f2));
        try {
            String line1, line2;
            while (((line1 = r1.readLine()) != null) | ((line2 = r2.readLine()) != null)) {
                if (!identicalLines(line1, line2)) {
                    return false;
                }
            }
            return true;
        } finally {
            r1.close();
            r2.close();
        }
    }

    private static boolean identicalLines(String line1, String line2) {
        return line1 != null && line2 != null && line1.equals(line2);
    }
}
