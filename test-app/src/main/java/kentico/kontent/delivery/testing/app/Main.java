package kentico.kontent.delivery.testing.app;

import kentico.kontent.delivery.AsyncDeliveryClient;

public class Main {
    public static void main(final String[] args) {
        final AsyncDeliveryClient client = new AsyncDeliveryClient("09fc0115-dd4d-00c7-5bd9-5f73836aee81");

        System.out.println(client.getItem("test-codename"));
    }
}