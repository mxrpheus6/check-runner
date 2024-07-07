package main.java.ru.clevertec.check.model;

public class DiscountCard {
    private Long id;
    private String number;
    private int discountAmount;

    public DiscountCard(Long id, String number, int discountAmount) {
        this.id = id;
        this.number = number;
        this.discountAmount = discountAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }
}
