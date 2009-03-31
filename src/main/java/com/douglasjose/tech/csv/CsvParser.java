package com.douglasjose.tech.csv;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * @author Douglas Rodrigues
 */
public interface CsvParser {

    //public String escapeCell(String content);

    //public String unescapeCell(String escapedContent);

    public void writeFile(Csv csv, OutputStream os) throws IOException;

    public void loadFile(Csv csv, InputStream is) throws IOException;
}
