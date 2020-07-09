package kentico.kontent.delivery.sample.dancinggoat.controllers;

import kentico.kontent.delivery.DeliveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

@Controller
public class HomeController {
    @Autowired
    DeliveryClient deliveryClient;

    @GetMapping("/")
    String index(Model model) throws ExecutionException, InterruptedException {

//        Home home = deliveryClient.getItem("home", Home.class, DeliveryParameterBuilder.params().linkedItemsDepth(2).build())
//                .toCompletableFuture()
//                .get();

//        model.addAttribute("home", home);
        return "home";
    }
}
