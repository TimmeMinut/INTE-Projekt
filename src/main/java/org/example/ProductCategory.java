package org.example;

public enum ProductCategory {
        BOOK(0.06),
        FOOD(0.12),
        STANDARD(0.25);

        public final double VATRate;

        ProductCategory(double VATRate) {
            this.VATRate = VATRate;
        }

        public double getVATRate() {
            return VATRate;
        }
}

