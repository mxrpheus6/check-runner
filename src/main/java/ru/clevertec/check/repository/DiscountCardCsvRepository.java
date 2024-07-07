package main.java.ru.clevertec.check.repository;

import main.java.ru.clevertec.check.model.DiscountCard;
import main.java.ru.clevertec.check.model.builder.DiscountCardBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class DiscountCardCsvRepository {
    private static final String CSV_DELIMITER = ";";
    private static final String DISCOUNT_CARDS_FILEPATH = "./src/main/resources/discountCards.csv";

    private DiscountCardCsvRepository() {}

    public static Optional<DiscountCard> findDiscountCardByNumber(String number) {
        Optional<DiscountCard> result = Optional.empty();
        try (BufferedReader reader = new BufferedReader(new FileReader(DISCOUNT_CARDS_FILEPATH))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(CSV_DELIMITER);
                if (data.length == 3 && data[1].equals(number)) {
                    DiscountCard discountCard = new DiscountCardBuilder()
                            .setId(Long.parseLong(data[0]))
                            .setNumber(data[1])
                            .setDiscountAmount(Integer.parseInt(data[2]))
                            .build();
                    result = Optional.of(discountCard);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
