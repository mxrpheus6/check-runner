package main.java.ru.clevertec.check.model.builder;

import main.java.ru.clevertec.check.model.Check;
import main.java.ru.clevertec.check.model.CheckItem;
import main.java.ru.clevertec.check.model.DiscountCard;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CheckBuilder {
    private LocalDate date;
    private LocalTime time;

    private List<CheckItem> items;
    private DiscountCard discountCard;

    private Double totalPrice;
    private Double totalDiscountAmount;
    private Double totalWithDiscount;

    public Check build() {
        return new Check(date, time, items, discountCard,
                totalPrice, totalDiscountAmount, totalWithDiscount);
    }

    public CheckBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public CheckBuilder setTime(LocalTime time) {
        this.time = time;
        return this;
    }

    public CheckBuilder setItems(List<CheckItem> items) {
        this.items = items;
        return this;
    }

    public CheckBuilder setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
        return this;
    }

    public CheckBuilder setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public CheckBuilder setTotalDiscountAmount(Double totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
        return this;
    }

    public CheckBuilder setTotalWithDiscount(Double totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
        return this;
    }
}
