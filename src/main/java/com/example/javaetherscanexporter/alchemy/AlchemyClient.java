package com.example.javaetherscanexporter.alchemy;

import com.example.javaetherscanexporter.alchemy.types.AssetTransfer;
import com.example.javaetherscanexporter.alchemy.types.GetAssetTransfersResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlchemyClient {
    private static final String API_URL = "https://eth-mainnet.g.alchemy.com/v2/";
    private final String apiKey;
    private final OkHttpClient httpClient;
    private final Gson gson;

    public AlchemyClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    public List<AssetTransfer> getAssetTransfers(String address) throws IOException {
        List<AssetTransfer> fromTransfers = getAssetTransfers(address, "fromAddress");
        List<AssetTransfer> toTransfers = getAssetTransfers(address, "toAddress");

        List<AssetTransfer> allTransfers = new ArrayList<>();
        allTransfers.addAll(fromTransfers);
        allTransfers.addAll(toTransfers);

        return allTransfers;
    }

    private List<AssetTransfer> getAssetTransfers(String address, String direction) throws IOException {
        List<AssetTransfer> allTransfers = new ArrayList<>();
        String pageKey = null;

        while (true) {
            JsonObject params = new JsonObject();
            params.addProperty(direction, address);
            params.addProperty("maxCount", "0x3e8");
            params.addProperty("excludeZeroValue", false);
            params.addProperty("withMetadata", true);
            JsonArray category = new JsonArray();
            category.add("external");
            category.add("internal");
            category.add("erc20");
            category.add("erc721");
            category.add("erc1155");
            params.add("category", category);

            if (pageKey != null) {
                params.addProperty("pageKey", pageKey);
            }

            JsonArray paramsArray = new JsonArray();
            paramsArray.add(params);

            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("jsonrpc", "2.0");
            jsonBody.addProperty("id", 0);
            jsonBody.addProperty("method", "alchemy_getAssetTransfers");
            jsonBody.add("params", paramsArray);

            RequestBody body = RequestBody.create(
                    gson.toJson(jsonBody),
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(API_URL + apiKey)
                    .post(body)
                    .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                GetAssetTransfersResponse apiResp = gson.fromJson(response.body().string(), GetAssetTransfersResponse.class);
                allTransfers.addAll(apiResp.result.transfers);

                if (apiResp.result.pageKey == null) {
                    break;
                }
                pageKey = apiResp.result.pageKey;
            }
        }

        return allTransfers;
    }
}
