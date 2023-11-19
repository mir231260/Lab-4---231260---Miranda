public class ReservaPremium extends Reserva {
    private String otraColumna;

    public ReservaPremium(String nombreUsuario, String fecha, boolean tipoVuelo, int cantidadBoletos, String aerolinea, String otraColumna) {
        super(nombreUsuario, fecha, tipoVuelo, cantidadBoletos, aerolinea);
        this.otraColumna = otraColumna;
    }

    public String getOtraColumna() {
        return otraColumna;
    }

    public void setOtraColumna(String otraColumna) {
        this.otraColumna = otraColumna;
    }
}
