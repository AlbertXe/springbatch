import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * HASEE
 * 2019/7/24 14:33
 */

public class LocalDateTimeTest {

    @Test
    public void test1() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        LocalDateTime start = LocalDateTime.now();
//        LocalDateTime end = LocalDateTime.of(2019, 7, 25, 14, 45);
        LocalDateTime end = start.plusDays(7).plusMonths(2);
        System.out.println(start.format(formatter));

        Period period = Period.between(start.toLocalDate(), end.toLocalDate());
        int months = period.getMonths();
        int days = period.getDays();
        System.out.println(months+"==="+days);

    }
}
