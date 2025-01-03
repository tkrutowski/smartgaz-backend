package net.focik.Smartgaz.utils.prints;

public class ConvertTimeUtil {

    private ConvertTimeUtil() {
    }

    public static String mapMinutesToTime(Integer dayOffMinutesPay) {
        return dayOffMinutesPay / 60 +
                ":" +
                String.format("%02d", dayOffMinutesPay % 60);
    }
}