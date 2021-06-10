package chizhikov.utilitiesbot.database;

import java.time.LocalDate;

public class MonthData {
    private Integer id;
    private Integer electricity;
    private Integer hotWaterBath;
    private Integer coldWaterBath;
    private Integer hotWaterKitchen;
    private Integer coldWaterKitchen;
    private LocalDate date;

    public MonthData() {
    }

    public MonthData(Integer electricity, Integer hotWaterBath, Integer coldWaterBath, Integer hotWaterKitchen, Integer coldWaterKitchen, LocalDate date) {
        this.electricity = electricity;
        this.hotWaterBath = hotWaterBath;
        this.coldWaterBath = coldWaterBath;
        this.hotWaterKitchen = hotWaterKitchen;
        this.coldWaterKitchen = coldWaterKitchen;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getElectricity() {
        return electricity;
    }

    public void setElectricity(Integer electricity) {
        this.electricity = electricity;
    }

    public Integer getHotWaterBath() {
        return hotWaterBath;
    }

    public void setHotWaterBath(Integer hotWaterBath) {
        this.hotWaterBath = hotWaterBath;
    }

    public Integer getColdWaterBath() {
        return coldWaterBath;
    }

    public void setColdWaterBath(Integer coldWaterBath) {
        this.coldWaterBath = coldWaterBath;
    }

    public Integer getHotWaterKitchen() {
        return hotWaterKitchen;
    }

    public void setHotWaterKitchen(Integer hotWaterKitchen) {
        this.hotWaterKitchen = hotWaterKitchen;
    }

    public Integer getColdWaterKitchen() {
        return coldWaterKitchen;
    }

    public void setColdWaterKitchen(Integer coldWaterKitchen) {
        this.coldWaterKitchen = coldWaterKitchen;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

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
