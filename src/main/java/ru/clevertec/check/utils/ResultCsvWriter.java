package main.java.ru.clevertec.check.utils;

import main.java.ru.clevertec.check.model.Check;
import main.java.ru.clevertec.check.model.CheckItem;
import main.java.ru.clevertec.check.model.DiscountCard;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ResultCsvWriter {
    private static final String CSV_RESULT_FILENAME = "result.csv";
    private final String FORMATTED_DOUBLE = "%.2f$;";

    private final String filePath;
    private Check check;

    public ResultCsvWriter(String filePath) {
        if (filePath == null) {
            this.filePath = CSV_RESULT_FILENAME;
        } else {
            this.filePath = filePath;
        }
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public void writeResult() {
        try (FileWriter writer = new FileWriter(filePath)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Date;Time\n")
                    .append(check.getDate().toString())
                    .append(";")
                    .append(check.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    .append("\n\n")
                    .append("QTY;DESCRIPTION;PRICE;TOTAL;DISCOUNT;PERCENT;TOTAL WITH DISCOUNT\n");

            for (CheckItem item : check.getItems()) {
                sb.append(formatCheckItem(item));
            }
            sb.append("\n");

            DiscountCard discountCard = check.getDiscountCard();
            if (discountCard != null) {
                sb.append("DISCOUNT CARD;DISCOUNT PERCENTAGE\n")
                        .append(discountCard.getNumber())
                        .append(";")
                        .append(discountCard.getDiscountAmount())
                        .append("%\n\n");
            }

            sb.append("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n")
                    .append(String.format(FORMATTED_DOUBLE, check.getTotalPrice()))
                    .append(String.format(FORMATTED_DOUBLE, check.getTotalDiscountAmount()))
                    .append(String.format("%.2f$", check.getTotalWithDiscount()));

            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatCheckItem(CheckItem item) {
        return String.format("%d;%s;%.2f$;%.2f$;%.2f$;%d%%;%.2f$\n",
                item.getQuantity(),
                item.getDescription(),
                item.getItemPrice(),
                item.getTotalPrice(),
                item.getDiscountAmount(),
                item.getDiscountPercent(),
                item.getTotalWithDiscount());
    }

    public void writeError(String message) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}