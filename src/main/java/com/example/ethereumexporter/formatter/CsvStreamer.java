package com.example.ethereumexporter.formatter;

import com.example.ethereumexporter.formatter.types.CsvRecord;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class CsvStreamer implements AutoCloseable {
    private final CSVWriter writer;

    public CsvStreamer(String fileName) throws IOException {
        writer = new CSVWriter(new FileWriter(fileName));
        // Write header
        writer.writeNext(new String[]{
                "Block Number",
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
        });
    }

    public void writeRecord(CsvRecord record) {
        writer.writeNext(new String[]{
                record.blockNumber(),
                record.transactionHash(),
                record.dateTime(),
                record.fromAddress(),
                record.toAddress(),
                record.transactionType(),
                record.assetContractAddress(),
                record.assetSymbol(),
                record.tokenId(),
                record.value(),
                record.gasFee()
        });
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
