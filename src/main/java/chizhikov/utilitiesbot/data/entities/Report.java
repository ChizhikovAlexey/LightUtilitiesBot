package chizhikov.utilitiesbot.data.entities;

import java.time.LocalDate;

public class Report {
    private Integer electricity;
    private Integer hotWaterBath;
    private Integer coldWaterBath;
    private Integer hotWaterKitchen;
    private Integer coldWaterKitchen;
    private Integer drainage;
    private LocalDate date;

    private final MonthData firstMonthData;
    private final MonthData secondMonthData;

    private final Tariff tariff;

    public Report(MonthData firstMonthData, MonthData secondMonthData, Tariff tariff) {
        this.tariff = tariff;
        this.firstMonthData = firstMonthData;
        this.secondMonthData = secondMonthData;

        electricity = secondMonthData.getElectricity() - firstMonthData.getElectricity();
        hotWaterBath = secondMonthData.getHotWaterBath() - firstMonthData.getHotWaterBath();
        coldWaterBath = secondMonthData.getColdWaterBath() - firstMonthData.getColdWaterBath();
        hotWaterKitchen= secondMonthData.getHotWaterKitchen() - firstMonthData.getHotWaterKitchen();
        coldWaterKitchen = secondMonthData.getColdWaterKitchen() - firstMonthData.getColdWaterKitchen();
        drainage = hotWaterBath + coldWaterBath + hotWaterKitchen + coldWaterKitchen;
        date = secondMonthData.getDate();
    }

    public String getShortReport() {
        return  """
                Отчёт от %s
                Расход электроэнергии: %d -> %.2f рублей;
                Расход горячей воды: %d -> %.2f рублей;
                Расход холодной воды: %d -> %.2f рублей;
                
                ИТОГО: %.2f рублей.
                """
                .formatted(
                        date.toString(),
                        electricity, electricity * tariff.getElectricity(),
                        hotWaterBath + hotWaterKitchen, (hotWaterBath + hotWaterKitchen) * tariff.getHotWater(),
                        coldWaterBath + coldWaterKitchen, (coldWaterBath + coldWaterKitchen) * tariff.getColdWater(),
                        electricity * tariff.getElectricity() + (hotWaterBath + hotWaterKitchen) * tariff.getHotWater() + (coldWaterBath + coldWaterKitchen) * tariff.getColdWater()
                );
    }
}
