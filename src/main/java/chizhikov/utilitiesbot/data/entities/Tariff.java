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
public class Tariff {
    private Integer id;
    private Double electricity;
    private Double hotWater;
    private Double coldWater;
    private Double drainage;
    private LocalDate date;

    @Override
    public String toString() {
        return "Тариф от " + date + "\n" +
                "Электроэнергия: " + electricity + "\n" +
                "Горячая вода: " + hotWater + "\n" +
                "Холодная вода: " + coldWater + "\n" +
                "Водоотведение: " + drainage;
    }
}
