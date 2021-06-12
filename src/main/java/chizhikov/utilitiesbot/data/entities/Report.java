package chizhikov.utilitiesbot.data.entities;

import java.time.LocalDate;

public class Report {
    private final Integer electricity;
    private final Integer hotWaterBath;
    private final Integer coldWaterBath;
    private final Integer hotWaterKitchen;
    private final Integer coldWaterKitchen;
    private final Integer drainage;
    private final LocalDate date;
    private final Tariff tariff;

    public Report(MonthData firstMonthData, MonthData secondMonthData, Tariff tariff) {
        this.tariff = tariff;

        electricity = secondMonthData.getElectricity() - firstMonthData.getElectricity();
        hotWaterBath = secondMonthData.getHotWaterBath() - firstMonthData.getHotWaterBath();
        coldWaterBath = secondMonthData.getColdWaterBath() - firstMonthData.getColdWaterBath();
        hotWaterKitchen = secondMonthData.getHotWaterKitchen() - firstMonthData.getHotWaterKitchen();
        coldWaterKitchen = secondMonthData.getColdWaterKitchen() - firstMonthData.getColdWaterKitchen();
        drainage = hotWaterBath + coldWaterBath + hotWaterKitchen + coldWaterKitchen;
        date = secondMonthData.getDate();
    }

    public String getShortReport() {
        return """
                Отчёт от %s
                Электроэнергия: %d -> %.2f рублей;
                Горячая вода: %d -> %.2f рублей;
                Холодная вода: %d -> %.2f рублей;
                Водоотведение: %d -> %.2f рублей;
                                
                ИТОГО: %.2f рублей.
                """
                .formatted(
                        date.toString(),
                        electricity, electricity * tariff.getElectricity(),
                        hotWaterBath + hotWaterKitchen, (hotWaterBath + hotWaterKitchen) * tariff.getHotWater(),
                        coldWaterBath + coldWaterKitchen, (coldWaterBath + coldWaterKitchen) * tariff.getColdWater(),
                        drainage, drainage * tariff.getDrainage(),
                        electricity * tariff.getElectricity() + (hotWaterBath + hotWaterKitchen) * tariff.getHotWater() + (coldWaterBath + coldWaterKitchen) * tariff.getColdWater()
                );
    }
}
