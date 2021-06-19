package chizhikov.utilitiesbot.data.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MonthData {
    private Integer id;
    private Integer electricity;
    private Integer hotWaterBath;
    private Integer coldWaterBath;
    private Integer hotWaterKitchen;
    private Integer coldWaterKitchen;
    private LocalDate date;

    @Override
    public String toString() {
        return "Данные от " + date + "\n" +
                "Электроэнергия: " + electricity + "\n" +
                "Горячая вода (санузел): " + hotWaterBath + "\n" +
                "Холодная вода (санузел): " + coldWaterBath + "\n" +
                "Горячая вода (кухня): " + hotWaterKitchen + "\n" +
                "Холодная вода (кухня): " + coldWaterKitchen;
    }
}
