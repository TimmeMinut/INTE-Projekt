package org.example;

public class DiscountDecoratorBuyXPayForY extends ArticleDecorator implements Article{
    private ArticleGroup articleGroup;
    private int take;
    private int payFor;
    public DiscountDecoratorBuyXPayForY(ArticleGroup article, int take, int payFor) {
        super(article);
        this.articleGroup = article;
        this.take = take;
        this.payFor = payFor;
    }

    @Override
    public double getPrice() {
        int quantity = articleGroup.getQuantity();
        int notDiscountedQuantity = quantity / take * payFor; // 5/3 = 2, 2 * 2 = 4
        int restQuantity = quantity % take; // Q:7, T: 3, rest = 1

        double originalPrice = super.getPrice() / quantity;

        return originalPrice * ( notDiscountedQuantity + restQuantity ); // price * (4+1)
    }

    @Override
    public double getDiscountAmount() {
        return 0;
    }
}
