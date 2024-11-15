package apifestivos.demo.Festivo;

import java.time.LocalDate;

public class FechasUtils {

    public static LocalDate calcularDomingoDePascua(int año) {
        int a = año % 19;
        int b = año % 4;
        int c = año % 7;
        int d = (19 * a + 24) % 30;
        int e = (2 * b + 4 * c + 6 * d + 5) % 7;
        int dias = d + e;

        return LocalDate.of(año, 3, 15).plusDays(dias + 7); // Domingo de Pascua
    }

    public static boolean esFestivoBasadoEnPascua(Festivos festivo, LocalDate fecha, int tipo) {
        LocalDate pascua = calcularDomingoDePascua(fecha.getYear()).plusDays(festivo.getDiasPascua());

        if (tipo == 4 && pascua.getDayOfWeek().getValue() != 1) {
            pascua = pascua.plusDays(7 - pascua.getDayOfWeek().getValue());
        }
        return fecha.isEqual(pascua);
    }

    public static boolean esPuenteFestivo(Festivos festivo, LocalDate fecha) {
        LocalDate festivoFecha = LocalDate.of(fecha.getYear(), festivo.getMes(), festivo.getDia());

        if (festivoFecha.getDayOfWeek().getValue() != 1) {
            festivoFecha = festivoFecha.plusDays(8 - festivoFecha.getDayOfWeek().getValue());
        }
        return fecha.isEqual(festivoFecha);
    }
}
