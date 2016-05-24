package com.euro.typer.data.source.criteria;


import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by miras108 on 2016-05-22.
 */
public class DateHelperTest {

    @Test
    public void should() throws ParseException {
        String time = "20.06.2016 12:00";
        Date date = DateHelper.getDateWithTime(time);
    }
}