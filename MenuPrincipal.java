import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MenuPrincipal {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            ImplementacionModoInterfaz modo = new ImplementacionModoInterfaz();
            modo.leerUsuario();
            modo.leerReserva();

            // Crear el archivo CSV si no existe
            File file = new File("usuarios.csv");
            if (!file.exists()) {
                file.createNewFile();
            }

            // Menú principal
            while (true) {
                System.out.println("1. Registrar usuario");
                System.out.println("2. Login");
                System.out.println("3. Salir");

                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer del scanner

                switch (opcion) {
                    case 1:
                        // Registrar usuario
                        System.out.println("Ingrese un nuevo nombre de usuario:");
                        String nuevoUsername = scanner.nextLine();
                        System.out.println("Ingrese una nueva contraseña:");
                        String nuevaPassword = scanner.nextLine();
                        System.out.println("Seleccione el tipo de plan (gratis o VIP):");
                        String tipoPlan = scanner.nextLine();

                        modo.registroUsuario(nuevoUsername, nuevaPassword, tipoPlan);
                        break;
                    case 2:
                        // Login
                        System.out.println("Ingrese su nombre de usuario:");
                        String username = scanner.nextLine();
                        System.out.println("Ingrese su contraseña:");
                        String password = scanner.nextLine();
                    
                        Usuario usuario = modo.login(username, password);
                        if (usuario != null) {
                            // Verificar si es premium o no y cargar el menú correspondiente
                            if (usuario.getTipo() != null && usuario.getTipo().equalsIgnoreCase("VIP")) {
                                menuPremium(modo, usuario, scanner);
                            } else {
                                menuNoPremium(modo, usuario, scanner);
                            }
                        } else {
                            System.out.println("Nombre de usuario o contraseña incorrectos. Inténtelo de nuevo.");
                        }
                        break;
                    case 3:
                        // Salir
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            }
        }
    }

    private static void menuPremium(ImplementacionModoInterfaz modo, Usuario usuario, Scanner scanner) {
        while (true) {
            System.out.println("Menú Premium");
            System.out.println("1. Modo Reservas");
            System.out.println("2. Modo Confirmación");
            System.out.println("3. Modo Perfil");
            System.out.println("4. Cerrar Sesión");
    
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner
    
            switch (opcion) {
                case 1:
                    modoReservasPremium(modo, usuario, scanner);
                    break;
                case 2:
                    modoConfirmacionPremium(modo, usuario, scanner);
                    break;
                case 3:
                    modoPerfilPremium(modo, usuario, scanner);
                    break;
                case 4:
                    // Cerrar Sesión
                    return;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private static void modoReservasPremium(ImplementacionModoInterfaz modo, Usuario usuario, Scanner scanner) {
        // Recopilar información
        System.out.println("Modo Reservas - Premium");
        System.out.print("a. Definir fecha de viaje: ");
        String fechaViaje = scanner.nextLine();
    
        System.out.print("b. Definir si es ida y vuelta o solo ida (1: ida, 2: vuelta, 3: ambos): ");
        int tipoVueloOption = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        
        String tipoVuelo;
        switch (tipoVueloOption) {
            case 1:
                tipoVuelo = "ida";
                break;
            case 2:
                tipoVuelo = "vuelta";
                break;
            case 3:
                tipoVuelo = "ambos";
                break;
            default:
                System.out.println("Opción no válida. Se establecerá como 'ida'.");
                tipoVuelo = "ida";
                break;
        }
    
        System.out.print("c. Definir cantidad de boletos: ");
        int cantidadBoletos = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
    
        System.out.print("d. Definir aerolínea: ");
        String aerolinea = scanner.nextLine();
    
        // Crear la reserva y guardar en el CSV
        modo.reservacion(fechaViaje, tipoVuelo.equalsIgnoreCase("ida/vuelta"), cantidadBoletos, aerolinea, usuario.getUsername());
    }
    
    private static void modoConfirmacionPremium(ImplementacionModoInterfaz modo, Usuario usuario, Scanner scanner) {
        System.out.println("Modo Confirmación - Premium");
        
        // a. Definir número de tarjeta.
        System.out.print("a. Definir número de tarjeta: ");
        String numeroTarjeta = scanner.nextLine();
    
        // b. Definir cantidad de cuotas (1 = un solo pago, hasta 24 cuotas).
        System.out.print("b. Definir cantidad de cuotas (1-24): ");
        int cuotas = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        
        // c. Definir clase para vuelo (Coach o Primera Clase).
        System.out.print("c. Definir clase para vuelo (Coach o Primera Clase): ");
        String claseVuelo = scanner.nextLine();
    
        // d. Imprimir itinerario.
        String itinerario = modo.itinerario();
        System.out.println("Itinerario:\n" + itinerario);
    
        // e. Seleccionar número de asiento (Premium).
        System.out.print("e. Seleccionar número de asiento: ");
        String numeroAsiento = scanner.nextLine();
    
        // f. Definir cantidad de maletas (Premium).
        System.out.print("f. Definir cantidad de maletas: ");
        int cantidadMaletas = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
    
        // Lógica para confirmación de vuelo (puedes agregar más según sea necesario).
        modo.confirmacion(numeroTarjeta, cuotas, claseVuelo, numeroAsiento, cantidadMaletas, numeroAsiento);

        Confirmacion confirmacionPremium = new Confirmacion(numeroTarjeta, cuotas, claseVuelo, numeroAsiento, cantidadMaletas);
        modo.confirmacion(numeroTarjeta, cuotas, claseVuelo, numeroAsiento, cantidadMaletas, numeroAsiento); // Ajuste aquí

        // Guardar la confirmación en el CSV
        try {
            modo.guardarConfirmacion(confirmacionPremium, numeroAsiento);
        } catch (IOException e) {
            // Manejar la excepción según sea necesario
            e.printStackTrace();
        }
    }
    
    private static void modoPerfilPremium(ImplementacionModoInterfaz modo, Usuario usuario, Scanner scanner) {
        // Implementar lógica de Modo Perfil para usuarios premium
    }

    // Menú para usuarios no premium
    private static void menuNoPremium(ImplementacionModoInterfaz modo, Usuario usuario, Scanner scanner) {
        while (true) {
            System.out.println("Menú No Premium");
            System.out.println("1. Modo Reservas");
            System.out.println("2. Modo Confirmación");
            System.out.println("3. Modo Perfil");
            System.out.println("4. Cerrar Sesión");
    
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner
    
            switch (opcion) {
                case 1:
                    modoReservasNoPremium(modo, usuario, scanner);
                    break;
                case 2:
                    modoConfirmacionNoPremium(modo, usuario, scanner);
                    break;
                case 3:
                    modoPerfilNoPremium(modo, usuario, scanner);
                    break;
                case 4:
                    // Cerrar Sesión
                    return;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }
    private static void modoReservasNoPremium(ImplementacionModoInterfaz modo, Usuario usuario, Scanner scanner) {
        // Recopilar información
        System.out.println("Modo Reservas - No Premium");
        System.out.print("a. Definir fecha de viaje: ");
        String fechaViaje = scanner.nextLine();
    
        System.out.print("b. Definir si es ida y vuelta o solo ida (1: ida, 2: vuelta, 3: ambos): ");
        int tipoVueloOption = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
    
        String tipoVuelo;
        switch (tipoVueloOption) {
            case 1:
                tipoVuelo = "ida";
                break;
            case 2:
                tipoVuelo = "vuelta";
                break;
            case 3:
                tipoVuelo = "ambos";
                break;
            default:
                System.out.println("Opción no válida. Se establecerá como 'ida'.");
                tipoVuelo = "ida";
                break;
        }
    
        System.out.print("c. Definir cantidad de boletos: ");
        int cantidadBoletos = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
    
        System.out.print("d. Definir aerolínea: ");
        String aerolinea = scanner.nextLine();
    
        // Crear la reserva y guardar en el CSV
        modo.reservacion(fechaViaje, tipoVuelo.equalsIgnoreCase("ida/vuelta"), cantidadBoletos, aerolinea, usuario.getUsername());
    }
    private static void modoConfirmacionNoPremium(ImplementacionModoInterfaz modo, Usuario usuario, Scanner scanner) {
        System.out.println("Modo Confirmación - No Premium");
        
        // a. Definir número de tarjeta.
        System.out.print("a. Definir número de tarjeta: ");
        String numeroTarjeta = scanner.nextLine();
    
        // b. Definir cantidad de cuotas (1 = un solo pago, hasta 24 cuotas).
        System.out.print("b. Definir cantidad de cuotas (1-24): ");
        int cuotas = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        
        // c. Definir clase para vuelo (Coach o Primera Clase).
        System.out.print("c. Definir clase para vuelo (Coach o Primera Clase): ");
        String claseVuelo = scanner.nextLine();
    
        // d. Imprimir itinerario.
        String itinerario = modo.itinerario();
        System.out.println("Itinerario:\n" + itinerario);
    
        // Lógica para confirmación de vuelo (puedes agregar más según sea necesario).
        modo.confirmacion(numeroTarjeta, cuotas, claseVuelo, null, 1, itinerario); // Asiento y maletas no aplican para usuarios no premium.

        Confirmacion confirmacionNoPremium = new Confirmacion(numeroTarjeta, cuotas, claseVuelo, null, 1);
        modo.confirmacion(numeroTarjeta, cuotas, claseVuelo, null, 1, itinerario); // Ajuste aquí

        // Guardar la confirmación en el CSV
        try {
            modo.guardarConfirmacion(confirmacionNoPremium, itinerario);
        } catch (IOException e) {
            // Manejar la excepción según sea necesario
            e.printStackTrace();
        }
    }
    
    private static void modoPerfilNoPremium(ImplementacionModoInterfaz modo, Usuario usuario, Scanner scanner) {
        // Implementar lógica de Modo Perfil para usuarios no premium
    }

}
