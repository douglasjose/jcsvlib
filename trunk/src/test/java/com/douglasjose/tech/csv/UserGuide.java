package com.douglasjose.tech.csv;

/**
 * Class used to exemplify the usage of the library
 *
 * @author Douglas Rodrigues
 */
public class UserGuide {

    public static void main(String args[]) throws Exception {
        Csv csv = CsvFactory.createOfficeCsv();
        csv.add(0, 0, "mydata");
        csv.add(1, 1, "anotherdata");
        String content = csv.get(1, 1); // content = "anotherdata"
        System.out.println(content);
        csv.remove(1, 1);
        int i = csv.getColumns(); // i = 1
        int j = csv.getRows(); // j = 1
        System.out.println(i + " " + j);

        Csv syncCsv = CsvFactory.synchronizedCsv(csv);

        Csv customCsv = new BasicCsv(new AbstractCsvParser() {
            protected String getFieldSeparator() {
                return "'"; // Single quote instead of double quote
            }

            protected String getTextDelimiter() {
                return ";"; // Semi-colon instead of comma
            }
        });
    }
}
