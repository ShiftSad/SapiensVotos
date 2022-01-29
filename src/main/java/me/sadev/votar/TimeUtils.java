package me.sadev.votar;

@SuppressWarnings("unused")
public class TimeUtils {

    private static final long SEGUNDOS = 1000L;
    private static final long MINUTO = SEGUNDOS * 60L;
    private static final long HORA = MINUTO * 60L;
    private static final long DIA = HORA * 24L;
    private static final long SEMANA = DIA * 7L;

    public static String formatTimeSeconds(long timeSeconds) {
        return formatTimeMs(timeSeconds*1000);
    }

    public static String formatTimeMs(long timeMs) {
        int weeks = (int) (timeMs / SEMANA);
        int days = (int) ((timeMs % SEMANA) / DIA);
        int hours = (int) ((timeMs % DIA) / HORA);
        int minutes = (int) ((timeMs % HORA) / MINUTO);
        int seconds = (int) ((timeMs % MINUTO) / SEGUNDOS);

        String weekss = weeks != 0 ? weeks+"w " : "";
        String dayss = days != 0 ? days+"d " : "";
        String hourss = hours != 0 ? hours+"h " : "";
        String minutess = minutes != 0 ? minutes+"m " : "";
        String secondss = seconds+"s";

        return weekss+dayss+hourss+minutess+secondss;
    }
}
