package com.douglasjose.tech.csv;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Douglas Rodrigues
 */
public interface Csv {
    void add(int row, int column, String content);

    String get(int row, int column);

    void remove(int row, int column);

    void load(InputStream is) throws IOException;

    void store(OutputStream os) throws IOException;

    int getColumns();

    int getRows();
}
