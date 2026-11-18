/** Mini-utilitario de aserciones (sin JUnit disponible en este entorno). */
public class Assert {

    public static void assertEquals(Object esperado, Object real, String contexto) {
        boolean iguales = (esperado == null) ? (real == null) : esperado.equals(real);
        if (!iguales) {
            throw new AssertionError(contexto + " -> esperado=[" + esperado + "] real=[" + real + "]");
        }
    }

    public static void assertTrue(boolean condicion, String contexto) {
        if (!condicion) {
            throw new AssertionError(contexto + " -> se esperaba true");
        }
    }

    public static void assertNotNull(Object valor, String contexto) {
        if (valor == null) {
            throw new AssertionError(contexto + " -> se esperaba un valor no nulo");
        }
    }

    public static void fail(String contexto) {
        throw new AssertionError(contexto);
    }
}
