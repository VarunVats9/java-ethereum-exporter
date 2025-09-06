package com.example.ethereumexporter.alchemy.types;

import com.google.gson.annotations.SerializedName;

public class AssetTransfer {
    @SerializedName("blockNum")
    public String blockNum;
    public String from;
    public String to;
    public Double value;
    @SerializedName("erc721TokenId")
    public String erc721TokenId;
    public String asset;
    public String hash;
    public String category;
    public Metadata metadata;
}
