package com.euro.typer.data.source.entity;

/**
 * Created by miras108 on 2016-05-21.
 */
public enum Country {
    POLAND,
    GERMANY,
    FRANCE,
    ROMANIA,
    ALBANIA,
    SWITZERLAND,
    WALES,
    SLOVAKIA,
    ENGLAND,
    RUSSIA,
    TURKEY,
    CROATIA,
    NORTHERN_IRELAND,
    UKRAINE,
    SPAIN,
    CZECH_REPUBLIC,
    REPUBLIC_OF_IRELAND,
    SWEDEN,
    BELGIUM,
    ITALY,
    AUSTRIA,
    HUNGARY,
    PORTUGAL,
    ICELAND;

    public static Country getFromString(String str) {
        for (Country me : Country.values()) {
            if (me.name().equalsIgnoreCase(str))
                return me;
        }
        return null;
    }
}
