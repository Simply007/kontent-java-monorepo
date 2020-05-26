package kentico.kontent.delivery.testing.app;

import kentico.kontent.delivery.TempAsyncDeliveryClient;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(final String[] args) throws ExecutionException, InterruptedException {
        final TempAsyncDeliveryClient client = new TempAsyncDeliveryClient("975bf280-fd91-488c-994c-2f04416e5ee3");

        CompletionStage<String> about_us = client.getItem("about_us");
        about_us.toCompletableFuture().get();
//                .thenAccept(item -> System.out.println(item));
    }
}