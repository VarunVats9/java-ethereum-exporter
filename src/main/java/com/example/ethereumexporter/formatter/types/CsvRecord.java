package com.example.ethereumexporter.formatter.types;

public record CsvRecord(
    String blockNumber,
    String transactionHash,
    String dateTime,
    String fromAddress,
    String toAddress,
    String transactionType,
    String assetContractAddress,
    String assetSymbol,
    String tokenId,
    String value,
    String gasFee
) {}