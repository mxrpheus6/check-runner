package ru.clevertec.check.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Check {
    private LocalDate date;
    private LocalTime time;

    private List<CheckItem> items;
    private DiscountCard discountCard;

    private Double totalPrice;
    private Double totalDiscountAmount;
    private Double totalWithDiscount;

    public Check(LocalDate date, LocalTime time, List<CheckItem> items, DiscountCard discountCard,
                 Double totalPrice, Double totalDiscountAmount, Double totalWithDiscount) {
        this.date = date;
        this.time = time;
        this.items = items;
        this.discountCard = discountCard;
        this.totalPrice = totalPrice;
        this.totalDiscountAmount = totalDiscountAmount;
        this.totalWithDiscount = totalWithDiscount;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public List<CheckItem> getItems() {
        return items;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Double getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public Double getTotalWithDiscount() {
        return totalWithDiscount;
    }
}
