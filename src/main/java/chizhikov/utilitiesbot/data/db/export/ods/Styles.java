package chizhikov.utilitiesbot.data.db.export.ods;

import com.github.miachm.sods.Style;

public class Styles {
    public static Style getCommonStyle() {
        return new Style(
                false,
                false,
                false,
                null,
                null,
                9,
                null,
                false);
    }

    public static Style getFirstRowStyle() {
        Style firstRowStyle = new Style(
                false,
                false,
                false,
                null,
                null,
                9,
                null,
                false);
        firstRowStyle.setTextAligment(Style.TEXT_ALIGMENT.Center);
        return firstRowStyle;
    }

    public static Style getDateStyle() {
        Style firstRowStyle = new Style(
                false,
                false,
                false,
                null,
                null,
                9,
                null,
                false);
        firstRowStyle.setTextAligment(Style.TEXT_ALIGMENT.Right);
        return firstRowStyle;
    }

    public static Style getTotalPriceStyle() {
        return new Style(
                true,
                false,
                false,
                null,
                null,
                11,
                null,
                false);
    }

    public static Style getPriceStyle() {
        return new Style(
                true,
                false,
                false,
                null,
                null,
                9,
                null,
                false);
    }

}
