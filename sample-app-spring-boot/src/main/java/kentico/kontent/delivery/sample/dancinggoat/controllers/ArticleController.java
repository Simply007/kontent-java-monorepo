package kentico.kontent.delivery.sample.dancinggoat.controllers;

import kentico.kontent.delivery.DeliveryClient;
import kentico.kontent.delivery.DeliveryParameterBuilder;
import kentico.kontent.delivery.sample.dancinggoat.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class ArticleController {
    @Autowired
    DeliveryClient deliveryClient;

    @GetMapping("/articles")
    public String getArticles(Model model) throws ExecutionException, InterruptedException {
        List<Article> articles = deliveryClient
                .getItems(Article.class, DeliveryParameterBuilder
                        .params()
                        .page(0, 10)
                        .orderByDesc("elements.post_date")
                        .build())
                .toCompletableFuture()
                .get();

        model.addAttribute("articles", articles);
        return "articles";
    }

    @GetMapping("/articles/{codename}")
    public String getArticle(Model model, @PathVariable("codename") String codename) throws ExecutionException, InterruptedException {
        Article article = deliveryClient.getItem(codename, Article.class).toCompletableFuture().get();
        model.addAttribute("article", article);
        return "article";
    }
}
