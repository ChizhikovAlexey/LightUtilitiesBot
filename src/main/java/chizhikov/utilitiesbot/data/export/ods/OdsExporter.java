package chizhikov.utilitiesbot.data.export.ods;

import chizhikov.utilitiesbot.data.DataManager;
import chizhikov.utilitiesbot.data.entities.MonthData;
import chizhikov.utilitiesbot.data.entities.Tariff;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
public class OdsExporter {
    private final DataManager dataManager;

    @Autowired
    public OdsExporter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public File getOds() throws SQLException {
        List<Pair<MonthData, Tariff>> list = dataManager.getAllDataForOds();
        File result = new File("utilities.ods");
        try {
            int rows = 9 * list.size();
            int columns = 7;
            Sheet sheet = new Sheet("A", rows, columns);
            sheet.setColumnWidth(0, 45.0);
            sheet.setColumnWidths(1, 6, 28.0);

            for (int i = 1; i < list.size(); i++) {
                int firstRow = (i - 1) * 9;

                MonthData monthData = list.get(i).getFirst();
                Tariff tariff = list.get(i).getSecond();

                sheet.setRowHeight(firstRow + 7, 7.5);
                sheet.getRange(firstRow + 1, 5, 7, 1).setStyle(Styles.getPriceStyle());
                sheet.getRange(firstRow + 7, 5).setStyle(Styles.getTotalPriceStyle());
                sheet.getRange(firstRow + 1, 0, 6, 5).setStyle(Styles.getCommonStyle());
                sheet.getRange(firstRow + 1, 6).setStyle(Styles.getDateStyle());
                sheet.getRange(firstRow + 7, 0, 1, 5).merge();
                sheet.getRange(firstRow, 0, 1, 7).setStyle(Styles.getFirstRowStyle());

                sheet.getRange(firstRow,
                        0, 7).setValues(
                        "",
                        "электричество",
                        "гор вода 848497 (санузел)",
                        "хол вода 848198 (санузел)",
                        "гор вода 848565 (кухня)",
                        "хол вода 858292 (кухня)",
                        "водоотведение");

                sheet.getRange(firstRow,
                        1, 7).setValues(
                        "новые п-я",
                        monthData.getElectricity(),
                        monthData.getHotWaterBath(),
                        monthData.getColdWaterBath(),
                        monthData.getHotWaterKitchen(),
                        monthData.getColdWaterKitchen(),
                        "");
                if (i == 1) {
                    MonthData firstMonth = list.get(0).getFirst();
                    sheet.getRange(firstRow,
                            2, 6).setValues(
                            "предыдущие показания",
                            firstMonth.getElectricity(),
                            firstMonth.getHotWaterBath(),
                            firstMonth.getColdWaterBath(),
                            firstMonth.getHotWaterKitchen(),
                            firstMonth.getColdWaterKitchen()
                    );
                } else {
                    sheet.getRange(firstRow,
                            2, 6).setFormulas(
                            "предыдущие показания",
                            "=B" + (firstRow - 7),
                            "=B" + (firstRow - 6),
                            "=B" + (firstRow - 5),
                            "=B" + (firstRow - 4),
                            "=B" + (firstRow - 3)
                    );
                }

                //В формулах индексация начинается с единицы!
                sheet.getRange(firstRow,
                        3, 7).setFormulas(
                        "расход",
                        "=B" + (firstRow + 2) + " - C" + (firstRow + 2),
                        "=B" + (firstRow + 3) + " - C" + (firstRow + 3),
                        "=B" + (firstRow + 4) + " - C" + (firstRow + 4),
                        "=B" + (firstRow + 5) + " - C" + (firstRow + 5),
                        "=B" + (firstRow + 6) + " - C" + (firstRow + 6),
                        "=SUM(D" + (firstRow + 3) + ":D" + (firstRow + 6)
                );

                sheet.getRange(firstRow,
                        4, 7).setValues(
                        "тариф",
                        tariff.getElectricity(),
                        tariff.getHotWater(),
                        tariff.getColdWater(),
                        tariff.getHotWater(),
                        tariff.getColdWater(),
                        tariff.getDrainage());

                sheet.getRange(firstRow,
                        5, 8).setFormulas(
                        "к оплате",
                        "= D" + (firstRow + 2) + " * E" + (firstRow + 2),
                        "= D" + (firstRow + 3) + " * E" + (firstRow + 3),
                        "= D" + (firstRow + 4) + " * E" + (firstRow + 4),
                        "= D" + (firstRow + 5) + " * E" + (firstRow + 5),
                        "= D" + (firstRow + 6) + " * E" + (firstRow + 6),
                        "= D" + (firstRow + 7) + " * E" + (firstRow + 7),
                        "= SUM(F" + (firstRow + 2) + ":F" + (firstRow + 7) + ")"
                );

                sheet.getRange(firstRow,
                        6, 2).setValues(
                        "дата показаний",
                        monthData.getDate()
                );
            }

            SpreadSheet spread = new SpreadSheet();
            spread.appendSheet(sheet);
            spread.save(result);
        } catch (Exception e) {
            log.error("Error while creating .ods!" + e.getMessage(), e);
        }
        return result;
    }
}
