public class Reserva {
    private String nombreUsuario;
    private String fecha;
    private boolean tipoVuelo;
    private int cantidadBoletos;
    private String aerolinea;
    private String tipoVueloString;

    public Reserva() {

    }

    public Reserva(String nombreUsuario, String fecha, boolean tipoVuelo, int cantidadBoletos, String aerolinea) {
        this.nombreUsuario = nombreUsuario;
        this.fecha = fecha;
        this.tipoVuelo = tipoVuelo;
        this.tipoVueloString = tipoVuelo ? "ida/vuelta" : "ida"; // Asignamos la cadena según el valor booleano
        this.cantidadBoletos = cantidadBoletos;
        this.aerolinea = aerolinea;
    }

    public Reserva(String fechaVuelo, boolean tipoVuelo2, int cantidadBoletos2, String aerolinea2, String username) {
    }

    public String getTipoVueloString() {
        return tipoVueloString;
    }
    public void setTipoVueloSring(String tipoVueloString) {
        this.tipoVueloString = tipoVueloString;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isTipoVuelo() {
        return tipoVuelo;
    }

    public void setTipoVuelo(boolean tipoVuelo) {
        this.tipoVuelo = tipoVuelo;
    }

    public int getCantidadBoletos() {
        return cantidadBoletos;
    }

    public void setCantidadBoletos(int cantidadBoletos) {
        this.cantidadBoletos = cantidadBoletos;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

}