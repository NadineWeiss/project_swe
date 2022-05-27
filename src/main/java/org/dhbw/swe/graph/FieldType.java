package org.dhbw.swe.graph;

import java.awt.*;

public enum FieldType {

    NEUTRAL,
    BLUEINIT(Color.BLUE, true, false, false, false),
    BLUESTART(Color.BLUE, false, true, false, false),
    BLUEEND(Color.BLUE, false, false, true, false),
    BLUETARGET(Color.BLUE, false, false, false, true),
    REDINIT(Color.RED, true, false, false, false),
    REDSTART(Color.RED, false, true, false, false),
    REDEND(Color.RED, false, false, true, false),
    REDTARGET(Color.RED, false, false, false, true),
    GREENINIT(Color.GREEN, true, false, false, false),
    GREENSTART(Color.GREEN, false, true, false, false),
    GREENEND(Color.GREEN, false, false, true, false),
    GREENTARGET(Color.GREEN, false, false, false, true),
    YELLOWINIT(Color.YELLOW, true, false, false, false),
    YELLOWSTART(Color.YELLOW, false, true, false, false),
    YELLOWEND(Color.YELLOW, false, false, true, false),
    YELLOWTARGET(Color.YELLOW, false, false, false, true);

    private Color color;
    private boolean init = false;
    private boolean start = false;
    private boolean end = false;
    private boolean target = false;

    FieldType(Color color, boolean init, boolean start, boolean end, boolean target) {

        this.color = color;
        this.init = init;
        this.start = start;
        this.end = end;
        this.target = target;

    }

    FieldType() {
    }

    public static FieldType getInitType(Color color){

        FieldType result = null;

        for (FieldType fieldType : values()) {
            if (color == fieldType.color && fieldType.init == true) {
                result = fieldType;
            }
        }

        return result;
    }

    public static FieldType getStartType(Color color) {

        FieldType result = null;

        for (FieldType fieldType : values()) {
            if (color == fieldType.color && fieldType.start == true) {
                result = fieldType;
            }
        }

        return result;
    }

    public static FieldType getEndType(Color color) {

        FieldType result = null;

        for (FieldType fieldType : values()) {
            if (color == fieldType.color && fieldType.end == true) {
                result = fieldType;
            }
        }

        return result;
    }

    public static FieldType getTargetType(Color color) {

        FieldType result = null;

        for (FieldType fieldType : values()) {
            if (color == fieldType.color && fieldType.target == true) {
                result = fieldType;
            }
        }

        return result;
    }

}
