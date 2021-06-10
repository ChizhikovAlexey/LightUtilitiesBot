package chizhikov.utilitiesbot.database;

import java.time.LocalDate;

public class Tariff {
    private Integer id;
    private Double electricity;
    private Double hotWater;
    private Double coldWater;
    private Double drainage;
    private LocalDate date;

    public Tariff() {
    }

    public Tariff(Double electricity, Double hot_water, Double cold_water, Double drainage, LocalDate date) {
        this.electricity = electricity;
        this.hotWater = hot_water;
        this.coldWater = cold_water;
        this.drainage = drainage;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getElectricity() {
        return electricity;
    }

    public void setElectricity(Double electricity) {
        this.electricity = electricity;
    }

    public Double getHotWater() {
        return hotWater;
    }

    public void setHotWater(Double hotWater) {
        this.hotWater = hotWater;
    }

    public Double getColdWater() {
        return coldWater;
    }

    public void setColdWater(Double coldWater) {
        this.coldWater = coldWater;
    }

    public Double getDrainage() {
        return drainage;
    }

    public void setDrainage(Double drainage) {
        this.drainage = drainage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Тариф от " + date + "\n" +
                "Электроэнергия: " + electricity + "\n" +
                "Горячая вода: " + hotWater + "\n" +
                "Холодная вода: " + coldWater + "\n" +
                "Водоотведение: " + drainage;
    }
}
