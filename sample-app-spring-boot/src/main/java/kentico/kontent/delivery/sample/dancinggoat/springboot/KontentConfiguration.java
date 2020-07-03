package kentico.kontent.delivery.sample.dancinggoat.springboot;

import kentico.kontent.delivery.DeliveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KontentConfiguration {
    @Bean
    public DeliveryClient deliveryClient() {
        DeliveryClient client = new DeliveryClient("975bf280-fd91-488c-994c-2f04416e5ee3");
        client.scanClasspathForMappings("kentico.kontent.delivery.sample.dancinggoat.models");
        return client;
    }
}
