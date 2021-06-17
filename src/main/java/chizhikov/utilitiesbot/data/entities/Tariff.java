package chizhikov.utilitiesbot.data.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class Tariff {
    private Integer id;
    private Double electricity;
    private Double hotWater;
    private Double coldWater;
    private Double drainage;
    private LocalDate date;

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
