import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionModoInterfaz implements ModoInterfaz {
    private List<Usuario> usuarios;
    private List<Reserva> reservas;
    private List<Confirmacion> confirmaciones;

    public ImplementacionModoInterfaz() {
        usuarios = new ArrayList<>();
        reservas = new ArrayList<>();
        confirmaciones = new ArrayList<>();

        if (usuarios.isEmpty() && reservas.isEmpty()) {
            leerUsuario();
            leerReserva();
        }
    }

    @Override
    public Usuario login(String username, String password) {
        // Lógica de inicio de sesión
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null; // Usuario no encontrado
    }

    @Override
    public void guardarReserva() throws IOException {
        // Leer el contenido actual del archivo
        String contenidoActual = CSVManager.leerCSV("reservas.csv");
    
        // Agregar las nuevas reservas al contenido existente
        try (FileWriter writer = new FileWriter("reservas.csv")) {
            // Escribir el contenido actual
            writer.write(contenidoActual);
    
            // Agregar las nuevas reservas
            for (Reserva reserva : reservas) {
                writer.write(reserva.getNombreUsuario() + ",");
                writer.write(reserva.getFecha() + ",");
                writer.write(reserva.getTipoVueloString() + ",");
                writer.write(reserva.getCantidadBoletos() + ",");
                writer.write(reserva.getAerolinea() + ",");
                writer.write("\n");
            }
        }
    }

    
    @Override
    public void registroUsuario(String username, String password, String tipo) {
        // Lógica de registro de usuario
        if (usuarios.stream().noneMatch(user -> user.getUsername().equals(username))) {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(username);
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setTipo(tipo);
            usuarios.add(nuevoUsuario);
    
            // Guardar la lista actualizada en el CSV
            try {
                guardarUsuario();
            } catch (IOException e) {
                // Manejar la excepción según sea necesario
                e.printStackTrace();
            }
        } else {
            System.out.println("El usuario ya existe. Inténtelo de nuevo con un nombre diferente.");
        }
    }

    @Override
    public void guardarUsuario() throws IOException {
        try (FileWriter writer = new FileWriter("usuarios.csv")) {
            for (Usuario user : usuarios) {
                writer.write(user.getUsername() + "," + user.getPassword() + ",");
                writer.write(user.getTipo() != null ? user.getTipo() : ""); // Cambiar "null" por cadena vacía
                writer.write("\n");
            }
        }
    }
    


    @Override
    public void leerUsuario() {
        // Limpiamos la lista de usuarios antes de cargar desde el CSV
        usuarios.clear();
        try {
            String contenido = CSVManager.leerCSV("usuarios.csv");
            String[] lineas = contenido.split("\n");
            for (String linea : lineas) {
                String[] partes = linea.split(",");
                if (partes.length >= 3) { // Verificar que hay al menos 3 partes
                    Usuario usuario = new Usuario();
                    usuario.setUsername(partes[0]);
                    usuario.setPassword(partes[1]);
                    usuario.setTipo(partes[2].isEmpty() ? null : partes[2]); // Cambiar aquí
                    usuarios.add(usuario);
                } else if (!linea.trim().isEmpty()) {
                    // Manejar la situación donde no hay suficientes partes en la línea
                    System.out.println("Error al leer usuario: " + linea);
                }
            }
        } catch (IOException e) {
            // Manejar la excepción según sea necesario
            e.printStackTrace();
        }
    }
    


    @Override
    public void cambiarPassword(String nuevaPassword) {
        // Lógica de cambio de contraseña
    }

    @Override
    public void cambiarTipoUsuario() {
        // Lógica de cambio de tipo de usuario
    }

    @Override
    public Usuario buscarUsuarioPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null; // Usuario no encontrado
    }



    @Override
    public void reservacion(String fechaVuelo, boolean tipoVuelo, int cantidadBoletos, String aerolinea, String username) {
        // Crear la reserva
        Reserva reserva = new Reserva(username, fechaVuelo, tipoVuelo, cantidadBoletos, aerolinea);

        // Agregar la reserva a la lista
        reservas.add(reserva);

        // Guardar la reserva en el CSV
        try {
            guardarReserva();
        } catch (IOException e) {
            // Manejar la excepción según sea necesario
            e.printStackTrace();
        }
    }
    

    @Override
    public void confirmacion(String numeroTarjeta, int cuotas, String claseVuelo, String numeroAsiento, int cantidadMaletas, String nombreUsuario) {
        // Crear la confirmación de vuelo
        Confirmacion confirmacion = new Confirmacion(numeroTarjeta, cuotas, claseVuelo, numeroAsiento, cantidadMaletas);
        
        // Agregar la confirmación a la lista de confirmaciones
        confirmaciones.add(confirmacion);
        
        // Guardar la confirmación en el CSV
        try {
            guardarConfirmacion(confirmacion, nombreUsuario);
        } catch (IOException e) {
            // Manejar la excepción según sea necesario
            e.printStackTrace();
        }
    }
    
    @Override
    public void guardarConfirmacion(Confirmacion confirmacion, String nombreUsuario) throws IOException {
        // Verificar si el archivo existe, si no, crearlo
        File confirmacionesFile = new File("confirmaciones.csv");
        if (!confirmacionesFile.exists()) {
            // Crear el archivo si no existe
            confirmacionesFile.createNewFile();
        }
    
        // Agregar la nueva confirmación al contenido existente
        try (FileWriter writer = new FileWriter(confirmacionesFile, true)) {
            // Agregar el nombre del usuario que inició la sesión
            writer.write(nombreUsuario + ",");
    
            // Agregar la nueva confirmación
            writer.write(confirmacion.getNumeroTarjeta() + ",");
            writer.write(confirmacion.getCuotas() + ",");
            writer.write(confirmacion.getClaseVuelo() + ",");
            writer.write(confirmacion.getNumeroAsiento() + ",");
            writer.write(confirmacion.getCantidadMaletas() + ",");
            writer.write("\n");
        }
    }

    @Override
    public String itinerario() {
        return null;
        // Lógica de generación de itinerario
    }

    @SuppressWarnings("unused")
    @Override
    public void leerReserva() {
        reservas.clear();
        try {
            String contenido = CSVManager.leerCSV("reservas.csv");
            String[] lineas = contenido.split("\n");
            for (String linea : lineas) {
                // (código existente)
            }
        } catch (IOException e) {
            // (código existente)
        }
    }
}
