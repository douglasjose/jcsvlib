package com.douglasjose.tech.csv;

/**
 * @author Douglas Rodrigues
 */
public class MSOfficeCsvParser extends AbstractCsvParser {

    protected String getFieldSeparator() {
        return ","; // Comma
    }

    protected String getTextDelimiter() {
        return "\""; // A double quote
    }
}
