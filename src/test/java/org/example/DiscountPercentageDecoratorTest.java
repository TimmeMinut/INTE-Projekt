package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountPercentageDecoratorTest {

    @Test
    void getPrice() {

    }

    @Test
    void getDiscountAmount_with_one_product() {
        // given
        Article article = new ConcreteArticle(200);

        // when
        Article decorator = new DiscountPercentageDecorator(article, 0.2);

        // then
        assertEquals(40, decorator.getDiscountAmount());
    }
    @Test
    void getDiscountAmount_with_multiple_products() {
        // given
        Article article1 = new ConcreteArticle(100);
        Article article2 = new ConcreteArticle(200);
        ArticleGroup group = new ArticleGroup();
        group.addArticle(article1);
        group.addArticle(article2);

        // when
        Article decorator = new DiscountPercentageDecorator(group, 0.2);

        // then
        assertEquals(60, decorator.getDiscountAmount());
    }
}