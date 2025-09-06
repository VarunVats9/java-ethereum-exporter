package com.example.ethereumexporter;

import com.example.ethereumexporter.alchemy.AlchemyClient;
import com.example.ethereumexporter.alchemy.types.AssetTransfer;
import com.example.ethereumexporter.formatter.CsvWriter;
import com.example.ethereumexporter.formatter.types.CsvRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String apiKey = "alcht_yFRsNRopCzkJt9isjdaTDM5sU0JdVX";

        if (args.length < 1) {
            System.err.println("Please provide an Ethereum address as an argument.");
            System.exit(1);
        }
        String address = args[0];

        long startTime = System.currentTimeMillis();

        AlchemyClient client = new AlchemyClient(apiKey);

        List<AssetTransfer> transfers = client.getAssetTransfers(address);

        long elapsedTime = System.currentTimeMillis() - startTime;
        int totalTxs = transfers.size();

        System.out.printf("Fetched %d transactions in %dms%n", totalTxs, elapsedTime);

        List<CsvRecord> records = new ArrayList<>();
        for (AssetTransfer tx : transfers) {
            records.add(new CsvRecord(
                    tx.hash,
                    tx.metadata.blockTimestamp, // Placeholder for timestamp
                    tx.from,
                    tx.to,
                    tx.category,
                    tx.asset,
                    "", // Placeholder
                    tx.erc721TokenId,
                    tx.value != null ? tx.value.toString() : "",
                    "" // Placeholder
            ));
        }

        String fileName = String.format("%s_transactions.csv", address);
        CsvWriter.writeCsv(fileName, records);

        System.out.println("Successfully exported transactions to " + fileName);
    }
}
