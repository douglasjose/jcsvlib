package com.douglasjose.tech.csv;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Douglas Rodrigues
 */
public class BasicCsv implements Csv {

    private Map<Integer, Map<Integer, String>> data = new HashMap<Integer, Map<Integer, String>>();

    private CsvParser csvParser;
    
    private int columns = 0;
    private int rows = 0;

    protected BasicCsv(CsvParser parser) {
        this.csvParser = parser;
    }

    /**
     * Add the content to the position [row, column] of the file.
     * Silently replaces existing content.
     *
     * @param row Zero-based row index
     * @param column Zero-based column index
     * @param content String to be stored
     */
    public void add(int row, int column, String content) {
        Map<Integer, String> rowMap = data.get(row);
        if (rowMap == null) {
            rowMap = new HashMap<Integer, String>();
            data.put(row, rowMap);
        }
        rowMap.put(column, content);
        columns = Math.max(columns, column + 1);
        rows = Math.max(rows, row + 1);
    }

    /**
     * Reads the content in the position [row, column] of the file. Return the empty String if there
     * is no content in the given position.
     * @param row Zero-based row index
     * @param column Zero-based column index
     * @return The content at a given position.
     */
    public String get(int row, int column) {
        if (row >= this.rows || column >= this.columns) {
            throw new IndexOutOfBoundsException("Invalid CSV position: [" + row + ","
                    + column + "]");
        }
        Map<Integer, String> rowMap = data.get(row);
        if (rowMap != null) {
            String value = rowMap.get(column);
            if (value != null) {
                return value;
            }
        }
        return "";
    }

    /**
     * Removes the content from the position [row, column].
     * @param row Zero-based row index
     * @param column Zero-based column index
     */
    public void remove(int row, int column) {
        // TODO Synchronize this
        Map<Integer, String> rowMap = data.get(row);
        if (rowMap != null) {
            if (rowMap.containsKey(column)) {
                rowMap.remove(column);
                if (rowMap.isEmpty()) {
                    data.remove(row);
                }                
                recalculateDimension();
            }
        }
    }

    /**
     * Reads a CSV file from a stream
     * 
     * @param is Stream to read the CSV file from
     * @throws IOException
     */
    public void load(InputStream is) throws IOException {
        // Discarding any existing state
        data = new HashMap<Integer, Map<Integer, String>>();
        rows = 0;
        columns = 0;
        csvParser.loadFile(this, is);
    }

    /**
     * Writes the CSV file to a stream.
     *
     * @param os Stream to write the CSV file to
     * @throws IOException
     */
    public void store(OutputStream os) throws IOException {
        csvParser.writeFile(this, os);
    }

    /**
     * @return Number of columns in the file
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @return Number of lines in the file
     */
    public int getRows() {
        return rows;
    }

    private void recalculateDimension() {
        rows = 0; columns = 0;
        Set<Integer> rowIndexes = data.keySet();
        for (Integer rowIndex : rowIndexes) {
            rows = Math.max(rows, rowIndex + 1);
            Set<Integer> columnIndexes = data.get(rowIndex).keySet();
            for (Integer columnIndex : columnIndexes) {
                columns = Math.max(columns, columnIndex + 1);
            }
        }
    }

}
