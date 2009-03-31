package com.douglasjose.tech.csv;

/**
 * @author Douglas Rodrigues
 */
public class CsvFactory {

    private CsvFactory() {} // Do not instantiate this class

    public static Csv createMSOfficeCsv() {
        return new Csv(new MSOfficeCsvParser());
    }

}
