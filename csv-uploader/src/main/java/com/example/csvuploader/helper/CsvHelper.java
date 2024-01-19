package com.example.csvuploader.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.csvuploader.model.Tutorial;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;


public class CsvHelper {
    public static String TYPE = "text/csv";
    static String HEADER = "Id";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Tutorial> csvToTutorials(InputStream is) {
        long starttime = System.currentTimeMillis();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Tutorial> tutorials = new ArrayList<Tutorial>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Tutorial tutorial = new Tutorial(
                );

                tutorials.add(tutorial);
            }
            long endTime = System.currentTimeMillis();
            System.out.println(Math.subtractExact(endTime,starttime));
            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream tutorialsToCSV(List<Tutorial> tutorials) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (Tutorial tutorial : tutorials) {
                List<String> data = Arrays.asList(
                        String.valueOf(tutorial.getId())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

}

//
//package com.example.csvuploader.helper;
//
//
//import com.example.csvuploader.model.Tutorial;
//import io.deephaven.dataframe.DataFrame;
//import io.deephaven.dataframe.io.Csv;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CsvHelper {
//
//    public static boolean hasCSVFormat(InputStream is) {
//        // Deephaven CSV library does not provide direct support for checking file format.
//        // You might need to rely on other mechanisms or assume it is a CSV file.
//        return true;
//    }
//
//    public static List<Tutorial> csvToTutorials(InputStream is) {
//        try {
//            DataFrame df = Csv.read(is);
//
//            List<Tutorial> tutorials = new ArrayList<>();
//
//            // Assuming you have a column named "Id" in your DataFrame
//            df.getColumn("Id").toArray().stream()
//                    .map(value -> new Tutorial(String.valueOf(value)))
//                    .forEach(tutorials::add);
//
//            return tutorials;
//        } catch (IOException e) {
//            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
//        }
//    }
//
//    public static ByteArrayInputStream tutorialsToCSV(List<Tutorial> tutorials) {
//        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//            // Creating a DataFrame from the list of tutorials
//            DataFrame df = new DataFrame().addStringColumn("Id", tutorials.stream().map(Tutorial::getId));
//
//            // Writing the DataFrame to CSV format
//            Csv.write(out, df);
//
//            return new ByteArrayInputStream(out.toByteArray());
//        } catch (IOException e) {
//            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
//        }
//    }
//}
