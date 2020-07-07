package kentico.kontent.delivery.sample.dancinggoat.springboot;

import kentico.kontent.delivery.DeliveryClient;
import kentico.kontent.delivery.InlineContentItemsResolver;
import kentico.kontent.delivery.sample.dancinggoat.models.Tweet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KontentConfiguration {
    @Bean
    public DeliveryClient deliveryClient() {
        DeliveryClient client = new DeliveryClient("975bf280-fd91-488c-994c-2f04416e5ee3");
        client.scanClasspathForMappings("kentico.kontent.delivery.sample.dancinggoat.models");
        // TODO - following line is necessary because in
        //  ((java.util.HashMap.Node)client.stronglyTypedContentItemConverter.classToContentTypeMapping.entrySet().toArray()[0]).getKey()
        //  there is the key duplicated for tweet - dunno why
        client.registerType(Tweet.class);
        client.registerInlineContentItemsResolver(new InlineContentItemsResolver<Tweet>() {
            @Override
            public String resolve(Tweet data) {
                return "Inline resolution used for tweet!";
            }
        });
        return client;
    }
}
