package com.example.javaetherscanexporter.alchemy.types;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAssetTransfersResult {
    public List<AssetTransfer> transfers;
    @SerializedName("pageKey")
    public String pageKey;
}
