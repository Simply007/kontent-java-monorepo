package kentico.kontent.delivery.sample.springboot;

import com.dancinggoat.models.Article;
import kentico.kontent.delivery.DeliveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) throws ExecutionException, InterruptedException {
        final DeliveryClient client = new DeliveryClient("975bf280-fd91-488c-994c-2f04416e5ee3");

        Article onRoasts = client.getItem("on_roasts", Article.class)
                .toCompletableFuture()
                .get();
        return onRoasts.getBodyCopy();
    }
}
