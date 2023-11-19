import java.io.IOException;

public interface ModoInterfaz {
    Usuario login(String username, String password);
    Usuario buscarUsuarioPorUsername(String username);
    void registroUsuario(String username, String password, String tipo);
    void cambiarPassword(String nuevaPassword);
    void cambiarTipoUsuario();
    void reservacion(String fechaVuelo, boolean tipoVuelo, int cantidadBoletos, String aerolinea, String username);
    void confirmacion(String numeroTarjeta, int cuotas, String claseVuelo, String numeroAsiento, int cantidadMaletas, String nombreUsuario);
    String itinerario();
    void guardarUsuario() throws IOException;
    void guardarReserva() throws IOException;
    void leerUsuario();
    void leerReserva();
    void guardarConfirmacion(Confirmacion confirmacion, String nombreUsuario) throws IOException;
}
