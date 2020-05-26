package kentico.kontent.delivery;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Executes requests against the Kentico Kontent Delivery API.
 *
 * @see <a href="https://docs.kontent.ai/reference/delivery-api#section/Authentication">
 * KenticoKontent API reference - Authentication</a>
 * @see <a href="https://docs.kontent.ai/reference/delivery-api">
 * KenticoKontent API reference - Delivery API</a>
 */
public class TempAsyncDeliveryClient {

    private final OkHttpClient client = new OkHttpClient();
    private String projectId;

    public TempAsyncDeliveryClient(String projectId) {
        this.projectId = projectId;
    }

        public CompletionStage<String> getItem(String contentItemCodename) {
        Request.Builder builder = new Request.Builder()
                .url("https://deliver.kontent.ai/" + projectId + "/items/" + contentItemCodename);

        Request request = builder.build();

        return send(request)
                .thenApply((response) -> {
                    try {
                        return response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                });

    }

    private CompletionStage<Response> send(Request request) {
        final CompletableFuture<Response> future = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                future.complete(response);
            }
        });
        return future;
    }
}
