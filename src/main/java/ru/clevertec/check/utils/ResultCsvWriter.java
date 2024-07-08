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
            writer.append("Date;Time;\n");
            writer.append(check.getDate().toString() + ";" + check.getTime()
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n\n");
            writer.append("QTY;DESCRIPTION;PRICE;TOTAL;DISCOUNT;PERCENT;TOTAL WITH DISCOUNT\n");
            for (CheckItem item: check.getItems()) {
                writer.append(String.format("%d;", item.getQuantity()));
                writer.append(item.getDescription() + ";");
                writer.append(String.format(FORMATTED_DOUBLE, item.getItemPrice()));
                writer.append(String.format(FORMATTED_DOUBLE, item.getTotalPrice()));
                writer.append(String.format(FORMATTED_DOUBLE, item.getDiscountAmount()));
                writer.append(String.format("%d%%;", item.getDiscountPercent()));
                writer.append(String.format(FORMATTED_DOUBLE, item.getTotalWithDiscount()));
                writer.append("\n");
            }
            writer.append("\n");
            DiscountCard discountCard = check.getDiscountCard();
            if (discountCard != null) {
                writer.append("DISCOUNT CARD;DISCOUNT PERCENTAGE;\n");
                writer.append(discountCard.getNumber() + ";");
                writer.append(discountCard.getDiscountAmount() + "%\n\n");
            }
            writer.append("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT;\n");
            writer.append(String.format(FORMATTED_DOUBLE, check.getTotalPrice()));
            writer.append(String.format(FORMATTED_DOUBLE, check.getTotalDiscountAmount()));
            writer.append(String.format(FORMATTED_DOUBLE, check.getTotalWithDiscount()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeError(String message) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}