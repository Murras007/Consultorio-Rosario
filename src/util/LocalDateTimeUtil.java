package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtil {

    public static String formatToHuman(LocalDateTime time) {
        if (time.toLocalDate().isEqual(LocalDate.now())) {
            if (time.getHour() == LocalTime.now().getHour()) {
                int tmin = LocalTime.now().getMinute() - time.getMinute();
                return (tmin<0?"Há " +Math.abs(tmin):"Dentro de "  +Math.abs(tmin)) + "min";
            }

            return "Hoje, às ".concat(time.format(DateTimeFormatter.ofPattern("HH:mm")));
        }

        if (time.toLocalDate().isEqual(LocalDate.now().plusDays(2L))) {
            return "Depois de amanhã às ".concat(time.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }

        if (time.toLocalDate().isEqual(LocalDate.now().plusDays(1L))) {
            return "Amanhã às ".concat(time.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }

        if (time.toLocalDate().isEqual(LocalDate.now().minusDays(1L))) {
            return "Ontem às ".concat(time.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }

        if (time.toLocalDate().isEqual(LocalDate.now().minusDays(2L))) {
            return "Anteontem às ".concat(time.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        return time.format(DateTimeFormatter.ofPattern("dd/MM, yyyy HH:mm"));
    }
}
