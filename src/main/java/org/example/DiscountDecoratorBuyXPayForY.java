package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class DiscountDecoratorBuyXPayForY extends ArticleDecorator implements Article{
    private ArticleGroup articleGroup;
    private int buy;
    private int payFor;
    public DiscountDecoratorBuyXPayForY(ArticleGroup article, int buy, int payFor) {
        super(article);
        this.articleGroup = article;
        this.buy = buy;
        this.payFor = payFor;
    }

    @Override
    public double getPrice() {
        ArrayList<Article> articlesSorted = getArticlesSorted();

        int totalQuantity = articleGroup.getQuantity(); // TODO Throw away since articleGroup.getArticles()?
        int notDiscountedQuantity = totalQuantity / buy * payFor;
        int restQuantity = totalQuantity % buy;

        // sätta rabatt på notdiscountedQuantity: de sista i listan
        for ( int i = articlesSorted.size(); i > notDiscountedQuantity; i--) {
            Article article = articlesSorted.get(i);
            article.
        }


        double originalPrice = super.getPrice() / totalQuantity;

        return originalPrice * ( notDiscountedQuantity + restQuantity );
    }

    private ArrayList<Article> getArticlesSorted() {
        ArrayList<Article> articles = articleGroup.getArticles();

        Collections.sort(articles, (article1, article2) -> {
            double price1 = article1.getPrice();
            double price2 = article2.getPrice();

            return Double.compare(price1, price2);
        });
        return articles;
    }

    @Override
    public double getDiscountAmount() {
        return 0;
    }

    @Override
    public void setDiscountAMount() {

    }
}
