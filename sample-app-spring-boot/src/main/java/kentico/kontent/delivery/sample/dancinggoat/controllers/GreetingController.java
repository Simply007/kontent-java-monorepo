package kentico.kontent.delivery.sample.dancinggoat.controllers;

import kentico.kontent.delivery.DeliveryClient;
import kentico.kontent.delivery.sample.dancinggoat.models.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ExecutionException;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
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