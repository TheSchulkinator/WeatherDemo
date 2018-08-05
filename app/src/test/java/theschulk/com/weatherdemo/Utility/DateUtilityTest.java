package theschulk.com.weatherdemo.Utility;

import org.junit.Test;
import static org.junit.Assert.*;

public class DateUtilityTest {

    @Test
            public void testEvaluateDate() {
        String unixTime1 = "1533497216";
        String unixTime2 = "1433497216";

        String expectedDate1 = "2018-08-05";
        String expectedDate2 = "2015-06-05";

        String actualDate1 = DateUtility.formatDate(unixTime1);
        String actualDate2 = DateUtility.formatDate(unixTime2);

        assertEquals(expectedDate1, actualDate1);
        assertEquals(expectedDate2,actualDate2);
    }

}