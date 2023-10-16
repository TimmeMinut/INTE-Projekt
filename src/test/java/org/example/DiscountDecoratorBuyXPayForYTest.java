package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountDecoratorBuyXPayForYTest {
    static ArticleGroup getArticleGroup(int numberOfItems, double pricePerItem) {
        ArticleGroup articleGroup = new ArticleGroup();
        for (int i = 0; i < numberOfItems; i++) {
            articleGroup.addArticle(new ConcreteArticle(pricePerItem));
        }
        return articleGroup;
    }

    @Test
    void Buy_3_pay_for_2_with_even_number_of_products() {
        // given
        ArticleGroup articleGroup = getArticleGroup(3, 100.0);

        // when
        Article discountedGroup = new DiscountDecoratorBuyXPayForY(articleGroup, 3, 2);

        // then
        assertEquals(200, discountedGroup.getPrice());
    }

    @Test
    void Buy_3_pay_for_2_with_odd_number_of_products() {
        // given
        ArticleGroup articleGroup = getArticleGroup(5, 100.0);

        // when
        Article discountedGroup = new DiscountDecoratorBuyXPayForY(articleGroup, 3, 2);

        // then
        assertEquals(400, discountedGroup.getPrice());
    }

    @Test
    void Buy_3_pay_for_2_with_too_low_number_of_products() {
        // given
        ArticleGroup articleGroup = getArticleGroup(2, 100.0);

        // when
        Article discountedGroup = new DiscountDecoratorBuyXPayForY(articleGroup, 3, 2);

        // then
        assertEquals(200, discountedGroup.getPrice());
    }

    @Test
    void Buy_5_pay_for_3_with_odd_number_of_products() {
        // given
        ArticleGroup articleGroup = getArticleGroup(12, 100.0);

        // when
        Article discountedGroup = new DiscountDecoratorBuyXPayForY(articleGroup, 5, 3);

        // then
        assertEquals(800, discountedGroup.getPrice());
    }
}
