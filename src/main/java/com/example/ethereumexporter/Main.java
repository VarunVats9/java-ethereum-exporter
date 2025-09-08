package com.example.ethereumexporter;

import com.example.ethereumexporter.alchemy.AlchemyClient;
import com.example.ethereumexporter.formatter.CsvStreamer;
import com.example.ethereumexporter.formatter.types.CsvRecord;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String apiKey = System.getenv("ALCHEMY_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = "alcht_yFRsNRopCzkJt9isjdaTDM5sU0JdVX";
        }

        if (args.length < 1) {
            System.err.println("Please provide an Ethereum address as an argument.");
            System.exit(1);
        }
        String address = args[0];

        long startTime = System.currentTimeMillis();

        AlchemyClient client = new AlchemyClient(apiKey);
        String fileName = String.format("%s_transactions.csv", address);

        int totalTxs;
        try (CsvStreamer csvStreamer = new CsvStreamer(fileName)) {
            totalTxs = client.streamAssetTransfers(address, tx -> {
                String value = "";
                if (tx.value != null) {
                    value = tx.value.toString();
                }

                CsvRecord record = new CsvRecord(
                        tx.blockNum,
                        tx.hash,
                        "",
                        tx.from,
                        tx.to,
                        tx.category,
                        tx.asset,
                        "",
                        tx.erc721TokenId,
                        value,
                        ""
                );
                csvStreamer.writeRecord(record);
            });
        } catch (IOException e) {
            System.err.println("Error during CSV export: " + e.getMessage());
            return;
        }

        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.printf("Fetched %d transactions in %dms%n", totalTxs, elapsedTime);
        System.out.println("Successfully exported transactions to " + fileName);
    }
}