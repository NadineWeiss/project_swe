package org.dhbw.swe.graph;

import java.awt.*;

public class GraphUtilities {

    public static FieldType getInitType(final Color color) {
        if (color.equals(Color.RED)) {
            return FieldType.REDINIT;
        }
        if (color.equals(Color.BLUE)) {
            return FieldType.BLUEINIT;
        }
        if (color.equals(Color.GREEN)) {
            return FieldType.GREENINIT;
        }
        return FieldType.YELLOWINIT;
    }

    public static FieldType getStartType(final Color color) {
        if (color.equals(Color.RED)) {
            return FieldType.REDSTART;
        }
        if (color.equals(Color.BLUE)) {
            return FieldType.BLUESTART;
        }
        if (color.equals(Color.GREEN)) {
            return FieldType.GREENSTART;
        }
        return FieldType.YELLOWSTART;
    }

    public static FieldType getEndType(final Color color) {
        if (color.equals(Color.RED)) {
            return FieldType.REDEND;
        }
        if (color.equals(Color.BLUE)) {
            return FieldType.BLUEEND;
        }
        if (color.equals(Color.GREEN)) {
            return FieldType.GREENEND;
        }
        return FieldType.YELLOWEND;
    }

    public static FieldType getTargetType(final Color color) {
        if (color.equals(Color.RED)) {
            return FieldType.REDTARGET;
        }
        if (color.equals(Color.BLUE)) {
            return FieldType.BLUETARGET;
        }
        if (color.equals(Color.GREEN)) {
            return FieldType.GREENTARGET;
        }
        return FieldType.YELLOWTARGET;
    }

}
