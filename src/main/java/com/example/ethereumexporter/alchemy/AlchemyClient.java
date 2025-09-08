package com.example.ethereumexporter.alchemy;

import com.example.ethereumexporter.alchemy.types.AssetTransfer;
import com.example.ethereumexporter.alchemy.types.GetAssetTransfersResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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

    public int streamAssetTransfers(String address, Consumer<AssetTransfer> callback) throws IOException {
        AtomicInteger totalTxs = new AtomicInteger();
        String pageKey = null;

        while (true) {
            JsonObject params = new JsonObject();
            params.addProperty("fromAddress", address);
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
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                GetAssetTransfersResponse apiResp = gson.fromJson(response.body().string(), GetAssetTransfersResponse.class);
                if (apiResp != null && apiResp.result != null && apiResp.result.transfers != null) {
                    apiResp.result.transfers.forEach(callback);
                    totalTxs.addAndGet(apiResp.result.transfers.size());
                }


                if (apiResp.result.pageKey == null) {
                    break;
                }
                pageKey = apiResp.result.pageKey;
            }
        }
        return totalTxs.get();
    }
}