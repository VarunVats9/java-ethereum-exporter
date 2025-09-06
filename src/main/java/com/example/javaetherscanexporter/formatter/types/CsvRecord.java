package com.example.javaetherscanexporter.formatter.types;

public class CsvRecord {
    public String transactionHash;
    public String dateTime;
    public String fromAddress;
    public String toAddress;
    public String transactionType;
    public String assetContractAddress;
    public String assetSymbol;
    public String tokenID;
    public String value;
    public String gasFee;

    public CsvRecord(String transactionHash, String dateTime, String fromAddress, String toAddress, String transactionType, String assetContractAddress, String assetSymbol, String tokenID, String value, String gasFee) {
        this.transactionHash = transactionHash;
        this.dateTime = dateTime;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.transactionType = transactionType;
        this.assetContractAddress = assetContractAddress;
        this.assetSymbol = assetSymbol;
        this.tokenID = tokenID;
        this.value = value;
        this.gasFee = gasFee;
    }

    public String[] toStringArray() {
        return new String[]{
                transactionHash,
                dateTime,
                fromAddress,
                toAddress,
                transactionType,
                assetContractAddress,
                assetSymbol,
                tokenID,
                value,
                gasFee
        };
    }
}
