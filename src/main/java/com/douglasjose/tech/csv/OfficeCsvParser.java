package com.douglasjose.tech.csv;

/**
 * Microsoft Office/OpenOffice compatible <code>Csv</code> implementation.
 *
 * @see CsvFactory#createOfficeCsv()
 * @author Douglas Rodrigues
 */
public class OfficeCsvParser extends AbstractCsvParser {

    protected String getFieldSeparator() {
        return ","; // Comma
    }

    protected String getTextDelimiter() {
        return "\""; // A double quote
    }
}
