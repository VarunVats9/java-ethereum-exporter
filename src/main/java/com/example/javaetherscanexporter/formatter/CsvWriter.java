package com.example.javaetherscanexporter.formatter;

import com.example.javaetherscanexporter.formatter.types.CsvRecord;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    public static void writeCsv(String path, List<CsvRecord> records) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            String[] header = {
                    "Transaction Hash",
                    "Date & Time",
                    "From Address",
                    "To Address",
                    "Transaction Type",
                    "Asset Contract Address",
                    "Asset Symbol / Name",
                    "Token ID",
                    "Value / Amount",
                    "Gas Fee (ETH)"
            };
            writer.writeNext(header);

            for (CsvRecord record : records) {
                writer.writeNext(record.toStringArray());
            }
        }
    }
}
