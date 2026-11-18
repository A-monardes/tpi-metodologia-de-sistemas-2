import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/** Corredor de pruebas minimalista: descubre por reflexion los metodos "test*" de cada clase. */
public class TestRunner {

    public static void main(String[] args) throws Exception {
        List<Object> suites = new ArrayList<>();
        suites.add(new ValidadorSolicitudTest());
        suites.add(new SolicitudServiceTest());
        suites.add(new ReporteServiceTest());

        int ok = 0;
        int fallos = 0;

        for (Object suite : suites) {
            for (Method metodo : suite.getClass().getMethods()) {
                if (!metodo.getName().startsWith("test") || metodo.getParameterCount() != 0) {
                    continue;
                }
                String nombre = suite.getClass().getSimpleName() + "." + metodo.getName();
                try {
                    metodo.invoke(suite);
                    System.out.println("OK    " + nombre);
                    ok++;
                } catch (Exception e) {
                    Throwable causa = (e.getCause() != null) ? e.getCause() : e;
                    System.out.println("FALLO " + nombre + " -> " + causa.getMessage());
                    fallos++;
                }
            }
        }

        System.out.println("-----------------------------------------");
        System.out.println("Total: " + (ok + fallos) + "  OK: " + ok + "  Fallos: " + fallos);

        if (fallos > 0) {
            System.exit(1);
        }
    }
}
