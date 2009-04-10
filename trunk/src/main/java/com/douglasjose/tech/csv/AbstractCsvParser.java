package com.douglasjose.tech.csv;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Skeleton interface class which provides a general <code>CsvParser</code> implementation.
 * A common <code>CsvParser</code> implementation may be defined by extending this class and
 * defining a text delimiter and a field delimiter for the CSV file.
 *
 * @author Douglas Rodrigues
 */
public abstract class AbstractCsvParser implements CsvParser {

    private final char[] CHAR_TEXT_DELIMITER = this.getTextDelimiter().toCharArray();
    private final char[] CHAR_TEXT_DELIMITER_LITERAL =
            (this.getTextDelimiter() + this.getTextDelimiter()).toCharArray();
    private final char[] CHAR_FIELD_SEPARATOR = this.getFieldSeparator().toCharArray();

    private String escapeCell(String content) {
        String out = content;
        boolean hasFieldSeparator = content.indexOf(this.getFieldSeparator()) > -1;
        boolean hasTextDelimiter = content.indexOf(this.getTextDelimiter()) > -1;
        if (hasTextDelimiter) {
            // The text delimiter is escaped (duplicated)
            out = this.replaceAll(out, this.getTextDelimiter(),
                    this.getTextDelimiter() + this.getTextDelimiter());
        }
        if (hasFieldSeparator || hasTextDelimiter) {
            // Put the content between the text delimiter
            out = this.getTextDelimiter() + out + this.getTextDelimiter();
        }
        return out;
    }

    public void writeFile(Csv csv, OutputStream os) throws IOException {
        PrintWriter pw = new PrintWriter(os);
        for (int i = 0; i < csv.getRows(); i++) {
            for (int j = 0; j < csv.getColumns(); j++) {
                pw.print(escapeCell(csv.get(i,j)));
                if (j < csv.getColumns() - 1) {
                    pw.print(this.getFieldSeparator());
                }
            }
            pw.println();
        }        
        pw.close();
        os.close();
    }

    public void loadFile(Csv csv, InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        int lineNumber = 0;
        int columnNumber;
        while ((line = reader.readLine()) != null) {
            List<String> fields = this.splitLine(line);
            columnNumber = 0;
            for (String field : fields) {
                csv.add(lineNumber, columnNumber++, field);
            }
            lineNumber++;
        }
        reader.close();
        is.close();
    }

    private List<String> splitLine(String line) {
        List<String> out = new ArrayList<String>();
        // If the current position points to a literal (within text delimiter) or not
        boolean literal = false;
        char[] cLine = line.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < line.length(); i++) {
            if (!literal && startsWith(cLine, CHAR_FIELD_SEPARATOR, i)) {
                out.add(sb.toString());
                sb = new StringBuffer();
                i += CHAR_FIELD_SEPARATOR.length - 1; // Skipping
            } else if (startsWith(cLine, CHAR_TEXT_DELIMITER, i)) {
                // Text delimiter found; if single, flip the literal flag,
                // if double and within a literal, means it is part of the content
                if (literal && startsWith(cLine, CHAR_TEXT_DELIMITER_LITERAL, i)) {
                    sb.append(CHAR_TEXT_DELIMITER);
                    i += CHAR_TEXT_DELIMITER_LITERAL.length - 1;
                } else {
                    literal = !literal;
                    i += CHAR_TEXT_DELIMITER.length - 1; // Skipping
                }
            } else {
                sb.append(cLine[i]);
            }
        }
        String last = sb.toString();
        if (last != null && last.length() > 0) {
            out.add(last);
        }        
        return out;
    }

    /**
     * Checks for the presence of a char array (<code>pattern</code>) as a subsequence in another
     * char array (<code>buffer</code>) starting from the position <code>offset</code>. 
     */
    private boolean startsWith(char[] buffer, char[] pattern, int offset) {
        int i;
        for (i = 0; i < pattern.length && offset + i < buffer.length; i++) {
            if (buffer[offset + i] != pattern[i]) {
                return false;
            }
        }
         return i == pattern.length;
    }

    /**
     * Replaces all the occurences of <code>replace</code> in <code>original</code> by <code>
     * replacement</code>.
     *
     * @param original The original string
     * @param replace The substring to be replaced
     * @param replacement The replacement string
     * @return The modified string
     */
    private String replaceAll(String original, String replace, String replacement) {
        StringBuffer sb = new StringBuffer(original.length());
        int start = 0, end;
        while ((end = original.indexOf(replace,  start)) > -1) {
            sb.append(original.substring(start, end)).append(replacement);
            start = end + replace.length();
        }
        sb.append(original.substring(start));
        return sb.toString();
    }

    /**
     * String used to separate cells in the file.
     *
     * @return Field separator
     */
    protected abstract String getFieldSeparator();

    /**
     * String used to surround the content in a file.
     *
     * @return Text delimiter
     */
    protected abstract String getTextDelimiter();
}