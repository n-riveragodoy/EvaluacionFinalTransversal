package evaluacionfinaltransversal;

/**
 *
 * @author nrivera
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

class Teatro {
    
    //Variables de clase o estatica
    static int totalTeatros = 0;
    
    //Variables de instancia
    String nombre;
    int totalClientes;
    int totalEntradasVendidas;
    int totalIngresos;
    int totalAsientos;
    Map<String, Double> descuentos;
    List<Cliente> listaClientes;
    List<Integer> idsEntradas;
    List<Entrada> listaEntradasVendidas;
    List<Integer> asientosOcupados;
    List<Integer> asientosDisponibles;
    
    //Constructor de teatro
    Teatro(String nombre, Map<String, Double> descuentos) {
        
        this.nombre = nombre;
        this.totalClientes = 0;
        this.totalAsientos = 250;
        this.totalEntradasVendidas = 0;
        this.totalIngresos = 0;
        this.descuentos = descuentos;
        this.listaClientes = new ArrayList<>();
        this.idsEntradas = new ArrayList<>();
        this.listaEntradasVendidas = new ArrayList<>();
        this.asientosDisponibles = new ArrayList <>();
        this.asientosOcupados = new ArrayList <>();
        
        totalTeatros++;
    }
    
    // Métodos Teatro
    void mostrarMenu(Scanner scanner) {
        
        System.out.println("\nMenú principal");
        System.out.println("--------------");
        System.out.println("1.- Venta de entradas");
        System.out.println("2.- Ver descuentos disponibles");
        System.out.println("3.- Ver boleta de compra");
        System.out.println("4.- Buscar entrada por ID");
        System.out.println("5.- Eliminar entrada");
        System.out.println("6.- Lista entradas vendidas");
        System.out.println("7.- Ver estadísticas del teatro");
        System.out.println("8.- Salir del sistema");
        System.out.println("\nPresione el número que corresponde a su elección");
    }
    
    String seleccionarGenero(Scanner scanner) {
        String genero;

        System.out.print("Ingrese su género (masculino/femenino): ");
        genero = Validador.validarEntradaString(scanner.next(), scanner);

        while (!(genero.equalsIgnoreCase("masculino") || genero.equalsIgnoreCase("femenino"))) {
            System.out.println("Género no válido. Escriba 'masculino' o 'femenino': ");
            genero = Validador.validarEntradaString(scanner.next(), scanner);
        }

        return genero;
    }
    
    boolean esEstudiante(Scanner scanner) {
        String respuesta;

        System.out.print("¿Es estudiante? (sí/no): ");
        respuesta = Validador.validarSiONo(scanner.next(), scanner);

        return respuesta.equalsIgnoreCase("sí") || respuesta.equalsIgnoreCase("si");
    }
    
    int generarIdEntradaUnico(List<Integer> idsExistentes) {
    int id;

    do {
        id = (int) (Math.random() * 1000) + 1;
    } while (idsExistentes.contains(id));

    idsExistentes.add(id); 
    return id;
    
    }
    
    void mostrarTipoEntradasTarifas() {

        System.out.println("\nTarifas por sector");
        System.out.println("---------------------------");
        for (int j = 0; j < Entrada.sectores.length; j++) { 
            System.out.print((j + 1) + ".- " + Entrada.sectores[j] + "   $" + Entrada.tarifas[j]  + " CLP");
            System.out.print("   Asientos del " + ((totalAsientos / Entrada.sectores.length) * (j) + 1)+ " hasta el " + (totalAsientos / Entrada.sectores.length) * (j+1));
            System.out.println();

        }
    }
    
    String seleccionarSector(Scanner scanner) {
        String sector;
        
        mostrarTipoEntradasTarifas();
        
        System.out.println("\nEscriba el sector que desea");
        scanner.nextLine();
        sector = Validador.validarEntradaString(scanner.nextLine(), scanner);
        
        while (
            !(sector.equalsIgnoreCase("VIP") ||
              sector.equalsIgnoreCase("Palco") ||
              sector.equalsIgnoreCase("Platea baja") ||
              sector.equalsIgnoreCase("Platea alta") ||
              sector.equalsIgnoreCase("Galería") ||
              sector.equalsIgnoreCase("Galeria"))
            ) {
            System.out.println("\nTipo de entrada no válido.");
            System.out.println("Por favor escriba uno de los siguientes sectores: VIP, Palco, Platea baja, Platea alta o Galería.");
            sector = Validador.validarEntradaString(scanner.next(), scanner);     
        }    
        return sector;
    }
    
    int seleccionarAsiento(String sector, Scanner scanner) {
        int rangoInicio = 0;
        int rangoFin = 0;
        int asientoSeleccionado;
        int seccionTamaño;
        int contador = 0;

        seccionTamaño = totalAsientos / Entrada.sectores.length;
        
        switch (sector.toLowerCase()) {
            case "vip":
                rangoInicio = 1;
                rangoFin = seccionTamaño;
                break;
            case "palco":
                rangoInicio = seccionTamaño + 1;
                rangoFin = seccionTamaño * 2;
                break;
            case "platea baja":
                rangoInicio = seccionTamaño * 2 + 1;
                rangoFin = seccionTamaño * 3;
                break;
            case "platea alta":
                rangoInicio = seccionTamaño * 3 + 1;
                rangoFin = seccionTamaño * 4;
                break;
            case "galería":
            case "galeria":
                rangoInicio = seccionTamaño * 4 + 1;
                rangoFin = totalAsientos;
                break;
        }

        System.out.println("\nAsientos disponibles en el sector " + sector + ":\n");
        for (int num : asientosDisponibles) {
            if (num >= rangoInicio && num <= rangoFin) {
                System.out.print("|" + num + "| ");
                contador++;
                
                if (contador % 10 == 0) {
                    System.out.println();
                }
            }
        }
        System.out.println();

        System.out.println("\nSeleccione un asiento disponible entre " + rangoInicio + " y " + rangoFin + ":");
        asientoSeleccionado = Validador.validarEntradaInt(scanner.nextInt(), rangoInicio, rangoFin, scanner);

        while (!asientosDisponibles.contains(asientoSeleccionado)) {
            System.out.println("El asiento no está disponible. Intente con otro:");
            asientoSeleccionado = Validador.validarEntradaInt(scanner.nextInt(), rangoInicio, rangoFin, scanner);
        }

        asientosDisponibles.remove(Integer.valueOf(asientoSeleccionado));
        asientosOcupados.add(asientoSeleccionado);

        return asientoSeleccionado;
    }
    
    void ventaEntrada(Cliente cliente, Entrada entrada) {
        
        aplicarDescuento(cliente, entrada);
        listaEntradasVendidas.add(entrada);
        totalIngresos += entrada.tarifaFinal;
        totalEntradasVendidas++;
    }
    
    void aplicarDescuento(Cliente cliente, Entrada entrada) {
        String tipoCliente;
        double descuento;
        tipoCliente = cliente.getTipoCliente();
        descuento = descuentos.getOrDefault(tipoCliente, 0.0);
        entrada.descuento = (int) descuento;
        entrada.tarifaFinal = (int) (entrada.tarifaBase * (1 - (descuento / 100.0)));
    }
    
    void mostrarBoletaUltimaCompra(int cantidadEntradas) {
        int total = 0;
        int size;
        
        System.out.println("\nBoleta de compra");
        System.out.println("----------------");

        size = listaEntradasVendidas.size();
        List<Entrada> entradasRecientes = listaEntradasVendidas.subList(size - cantidadEntradas, size);

        for (Entrada entrada : entradasRecientes) {
            Cliente cliente = entrada.cliente;

            System.out.println("Nombre: " + cliente.nombre);
            System.out.println("Edad: " + cliente.edad);
            System.out.println("Género: " + cliente.genero);
            System.out.println();
            System.out.println("ID entrada: " + entrada.id);
            System.out.println("Sector: " + entrada.sector);
            System.out.println("Asiento: " + entrada.asiento);
            System.out.println("Precio base: $" + entrada.tarifaBase + " CLP");
            System.out.println("Descuento aplicado: " + entrada.descuento + "%");
            System.out.println("Precio final: $" + entrada.tarifaFinal + " CLP");
            System.out.println("-------------------------");

            total += entrada.tarifaFinal;
        }

        System.out.println("TOTAL: $" + total + " CLP");
    }
    
    void mostrarEstadisticas() {
        System.out.println("\nEstadísticas del Teatro");
        System.out.println("------------------------");

        System.out.println("Nombre del teatro: " + nombre);
        System.out.println("Total de asientos: " + totalAsientos);
        System.out.println("Entradas vendidas: " + totalEntradasVendidas);
        System.out.println("Asientos ocupados: " + asientosOcupados.size());
        System.out.println("Asientos disponibles: " + asientosDisponibles.size());
        System.out.println("Ingresos totales: $" + totalIngresos + " CLP");
    }
    
    void mostrarDescuentos() {
        
        int contador = 1;
        double descuento = 0.0;
        
        System.out.println("\nDescuentos disponibles");
        System.out.println("----------------------");

        if (descuentos.isEmpty()) {
            System.out.println("No hay descuentos para esta función.");
            return;
        }

        for (Map.Entry<String, Double> entry : descuentos.entrySet()) {
            descuento = entry.getValue();
            System.out.println(contador + ".- " + entry.getKey() + ": " + (int) descuento + "% de descuento");
            contador++;
        }

        System.out.println("\nLos descuentos se aplican automáticamente según el tipo de cliente.");
    }
    
    void mostrarEntradaPorId(int idBuscado) {
        boolean encontrada = false;

        for (Entrada entrada : listaEntradasVendidas) {
            if (entrada.id == idBuscado) {
                Cliente cliente = entrada.cliente;

                System.out.println("\nEntrada encontrada:");
                System.out.println("-------------------");
                System.out.println("ID de entrada: " + entrada.id);
                System.out.println("Nombre del cliente: " + cliente.nombre);
                System.out.println("Edad: " + cliente.edad);
                System.out.println("Género: " + cliente.genero);
                System.out.println();
                System.out.println("Sector: " + entrada.sector);
                System.out.println("Asiento: " + entrada.asiento);
                System.out.println("Precio final: $" + entrada.tarifaFinal + " CLP");
                System.out.println("---------------------");

                encontrada = true;
                break;
            }
        }

        if (!encontrada) {
            System.out.println("No se encontró ninguna entrada con ese ID.");
        }
    }
    
    void eliminarEntradaPorId(int idEliminar, Scanner scanner) {
        Entrada entradaAEliminar = null;
        
        for (Entrada entrada : listaEntradasVendidas) {
            if (entrada.id == idEliminar) {
                entradaAEliminar = entrada;
                break;
            }
        }

        if (entradaAEliminar == null) {
            System.out.println("\nNo se encontró ninguna entrada con ese ID.");
            return;
        }
        
        System.out.println("\nEntrada a eliminar");
        System.out.println("------------------");
        mostrarEntradaPorId(idEliminar);
        
        System.out.println("\n¿Desea eliminar esta entrada? (sí/no)");
        String confirmacion = Validador.validarSiONo(scanner.next(), scanner);
        if (!confirmacion.equalsIgnoreCase("sí") && !confirmacion.equalsIgnoreCase("si")) {
            System.out.println("Operación cancelada.");
            return;
        }
        
        listaEntradasVendidas.remove(entradaAEliminar);
        asientosOcupados.remove(Integer.valueOf(entradaAEliminar.asiento));
        asientosDisponibles.add(entradaAEliminar.asiento);
        idsEntradas.remove(Integer.valueOf(entradaAEliminar.id));
        totalEntradasVendidas--;
        totalIngresos -= entradaAEliminar.tarifaFinal;

        System.out.println("\nEntrada eliminada exitosamente.");
    }
    
    void mostrarListaEntradasVendidas() {
    if (listaEntradasVendidas.size() == 0) {
        System.out.println("\nNo hay entradas vendidas.");
        return;
    }

    System.out.println("\nEntradas vendidas");
    System.out.println("-----------------");

    for (Entrada entrada : listaEntradasVendidas) {
        Cliente cliente = entrada.cliente;

        System.out.println("Nombre del Cliente: " + cliente.nombre);
        System.out.println("Edad              : " + cliente.edad);
        System.out.println("Género            : " + cliente.genero);
        System.out.println();
        System.out.println("ID de Entrada     : " + entrada.id);
        System.out.println("Sector            : " + entrada.sector);
        System.out.println("Asiento           : " + entrada.asiento);
        System.out.println("Precio Pagado     : $" + entrada.tarifaFinal + " CLP");
        System.out.println("-------------------------");
    }
}
    
}

class Cliente {

    //Variables de instancia
    String nombre;
    String genero;
    int edad;
    boolean estudiante;

    //Constructor de cliente
    Cliente(String nombre, int edad, String genero, boolean estudiante) {
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;  
        this.estudiante = estudiante;
        
    }
    
    // Métodos cliente
    String getTipoCliente() {
        if (edad >= 65) {
            return "Tercera edad";
        } else if (genero.equalsIgnoreCase("femenino")) {
            return "Mujer";    
        } else if (estudiante) {
            return "Estudiante";
        } else if (edad <= 12) {
            return "Niño";
        }
        return null;
    }
}    

class Entrada {
    
    //Variables de clase o estatica
    static String[] sectores = {"VIP", "Palco", "Platea baja", "Platea alta", "Galería"};
    static int[] tarifas = {30000, 20000, 15000, 15000, 10000};

    //Variables de instancia
    int id;
    Cliente cliente;
    String sector;
    int asiento;
    int tarifaBase; 
    int descuento;
    int tarifaFinal;
    
    // Constructor de entrada
    Entrada(int id, Cliente cliente,String sector, int asiento) {
        this.id = id;
        this.cliente = cliente;
        this.sector = sector;
        this.asiento = asiento;
        this.descuento = 0;
        for (int i = 0; i < sectores.length; i++) {
            if (sector.equalsIgnoreCase(sectores[i])) {
                this.tarifaBase = tarifas[i];
                this.tarifaFinal = tarifas[i];
                break;
            }
        }
    }
    
}    

class Validador {
    public static int validarEntradaInt(int entradaInt, int limiteMin, int limiteMax, Scanner scanner) {
        while (entradaInt < limiteMin || entradaInt > limiteMax) {
            System.out.println("Entrada no válida");
            System.out.println("Ingrese un número entre " + limiteMin + " y " + limiteMax + ":");
            entradaInt = scanner.nextInt();
        }
        return entradaInt;
    }

    public static String validarEntradaString(String entradaString, Scanner scanner) {

        while (!entradaString.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.print("Ingrese texto válido (solo letras y espacios):\n");
            entradaString = scanner.nextLine();
        }

        entradaString = entradaString.trim();
        return entradaString.substring(0, 1).toUpperCase() + entradaString.substring(1).toLowerCase();
    }
    
    public static String validarSiONo(String entradaSiNo, Scanner scanner) {
        entradaSiNo = Validador.validarEntradaString(entradaSiNo, scanner);

        while (!(entradaSiNo.equalsIgnoreCase("sí") || 
                 entradaSiNo.equalsIgnoreCase("si") || 
                 entradaSiNo.equalsIgnoreCase("no"))) {
            System.out.println("Respuesta no válida. Escriba 'sí' o 'no': ");
            entradaSiNo = Validador.validarEntradaString(scanner.next(), scanner);
        }

        return entradaSiNo;
    }
}

class Pausar {
    public static void pausa(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            System.out.println("Error en la pausa");
        }
    }
}


public class EFT_S9_Nicolás_Rivera {
    
    public static void LlenarListaConNumerosAleatorios(List <Integer> lista, int cantidad, int maxLista) {
        
        //Variables locales
        int cantidadAsientosOcupados = (int) (Math.random() * cantidad) + 1;
        int aleatorio;
        
        for (int i = 0; i < cantidadAsientosOcupados; i++) {   
 
            do {
                aleatorio = (int) (Math.random() * maxLista) + 1;  
            } while (lista.contains(aleatorio));  
            
            lista.add(aleatorio);
        }
    
    }
    
    public static void main(String[] args) {
        
        // Nuevo Scanner
        Scanner scanner = new Scanner(System.in);
        
        // Variables operativas
        int opcion;
        boolean ejecutar = true;
        boolean ventaProcesada = false;
        int idBuscar;
        
        // Variables cliente
        String nombre;
        int edad;
        String genero;
        boolean estudiante;
        
        // Variables entradas
        int cantidadEntradas = 0;
        int id;
        String sector;
        int asiento; 
        
        // Mapa descuentos
        Map<String, Double> descuentos = new HashMap<>();
        descuentos.put("Tercera edad", 25.0);
        descuentos.put("Mujer", 20.0);
        descuentos.put("Estudiante", 15.0);
        descuentos.put("Niño", 10.0);
        
        // Creación del objeto teatro
        Teatro teatroMoro = new Teatro("TeatroMoro", descuentos);  
        
        //llenar lista con numeros aleatorios para simular los asientos que ya se vendieron
        LlenarListaConNumerosAleatorios(teatroMoro.asientosOcupados, 100, teatroMoro.totalAsientos);
        
        //Llenar la lista con los asientos disponibles
        for (int i = 1; i < teatroMoro.totalAsientos + 1; i++) {
            if (!(teatroMoro.asientosOcupados.contains(i))) {
                teatroMoro.asientosDisponibles.add(i);
            }
        }
        
        // Inicio programa
        System.out.println("Bienvenido al " + teatroMoro.nombre);
        System.out.println("Evento: Concierto de Bad Bunny");
        
        // Ciclo de ejecución
        while (ejecutar) {
            
            Pausar.pausa(4);    
            
            if (teatroMoro.asientosDisponibles.size() > 0) { 
                
                teatroMoro.mostrarMenu(scanner);                
                opcion = Validador.validarEntradaInt(scanner.nextInt(), 1, 8, scanner); // tener claro cuantos opciones tendra el menú
            } else {
                System.out.println("\nEstimado/a ya no quedan entradas disponibles");
                opcion = 8;
            }
        
            Pausar.pausa(2);    
        
            switch (opcion) {
                case 1:
                    System.out.println("\nVenta de entradas");
                    System.out.println("-------------------");
                    
                    System.out.println("\nIngrese la cantidad de entradas que desea (maximo 4 entradas por compra)");
                    cantidadEntradas = Validador.validarEntradaInt(scanner.nextInt(), 1, 4, scanner);
                    
                    for (int i = 1; i <= cantidadEntradas; i++) {
                        System.out.println("\nEntrada nº: " + i);
                        System.out.println("Datos usuario");
                        
                        scanner.nextLine();
                        System.out.print("Nombre: ");
                        nombre = Validador.validarEntradaString(scanner.next(), scanner);

                        System.out.print("Edad: ");
                        edad = Validador.validarEntradaInt(scanner.nextInt(), 1, 100, scanner);
                        
                        genero = teatroMoro.seleccionarGenero(scanner);
                        
                        estudiante = teatroMoro.esEstudiante(scanner);

                        Cliente cliente = new Cliente(nombre, edad, genero, estudiante);
                        
                        teatroMoro.listaClientes.add(cliente);
                        teatroMoro.totalClientes++;
                        
                        id = teatroMoro.generarIdEntradaUnico(teatroMoro.idsEntradas);
                        
                        sector = teatroMoro.seleccionarSector(scanner);

                        asiento = teatroMoro.seleccionarAsiento(sector, scanner);
                        
                        Entrada entrada = new Entrada (id, cliente, sector, asiento);
                        
                        teatroMoro.aplicarDescuento(cliente, entrada);
                        teatroMoro.ventaEntrada(cliente, entrada);
                    }
                    
                    Pausar.pausa(2); 
                    
                    System.out.println("\nCompra realiazada con exito");
                    
                    Pausar.pausa(2); 
                        
                    teatroMoro.mostrarBoletaUltimaCompra(cantidadEntradas);
                    
                    ventaProcesada = true;
                    break;
                case 2:
                    teatroMoro.mostrarDescuentos();
                    break;
                case 3:
                    if (ventaProcesada && teatroMoro.listaEntradasVendidas.size() > 0) {
                        teatroMoro.mostrarBoletaUltimaCompra(cantidadEntradas);
                    } else {
                        System.out.println("Aún no se realiza la compra");
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el ID de la entrada a buscar: ");
                    idBuscar = Validador.validarEntradaInt(scanner.nextInt(), 1, 1000, scanner);
                    teatroMoro.mostrarEntradaPorId(idBuscar);
                    break;
                case 5:
                    System.out.print("Ingrese el ID de la entrada a eliminar: ");
                    int idEliminar = Validador.validarEntradaInt(scanner.nextInt(), 1, 1000, scanner);
                    teatroMoro.eliminarEntradaPorId(idEliminar, scanner);
                    break;
                case 6:
                    teatroMoro.mostrarListaEntradasVendidas();
                    break;
                case 7:
                    teatroMoro.mostrarEstadisticas();
                    break;
                case 8:
                    System.out.println("\nGracias por vistar el Teatro Moro");
                    ejecutar = false;
                    break;
            }
            
        }
        
        /*
        // Crear clientes de prueba
        Cliente test1 = new Cliente("Diego", 68, "masculino", false); // Tercera edad
        Cliente test2 = new Cliente("Valentina", 30, "femenino", false);   // Mujer
        Cliente test3 = new Cliente("Felipe", 19, "masculino", true);   // Estudiante
        Cliente test4 = new Cliente("Cecilia", 10, "femenino", false); // Niña

        // Crear entradas de prueba
        Entrada prueba1 = new Entrada(teatroMoro.generarIdEntradaUnico(teatroMoro.idsEntradas), test1, "VIP", 101);
        Entrada prueba2 = new Entrada(teatroMoro.generarIdEntradaUnico(teatroMoro.idsEntradas), test2, "Platea alta", 135);
        Entrada prueba3 = new Entrada(teatroMoro.generarIdEntradaUnico(teatroMoro.idsEntradas), test3, "Palco", 65);
        Entrada prueba4 = new Entrada(teatroMoro.generarIdEntradaUnico(teatroMoro.idsEntradas), test4, "Galería", 200);

        // Aplicar descuentos y registrar venta
        teatroMoro.aplicarDescuento(test1, prueba1);
        teatroMoro.ventaEntrada(test1, prueba1);

        teatroMoro.aplicarDescuento(test2, prueba2);
        teatroMoro.ventaEntrada(test2, prueba2);

        teatroMoro.aplicarDescuento(test3, prueba3);
        teatroMoro.ventaEntrada(test3, prueba3);

        teatroMoro.aplicarDescuento(test4, prueba4);
        teatroMoro.ventaEntrada(test4, prueba4);

        // Mostrar entradas
        System.out.println("\nMostrar entradas por ID:");
        teatroMoro.mostrarEntradaPorId(prueba1.id);
        teatroMoro.mostrarEntradaPorId(prueba2.id);
        teatroMoro.mostrarEntradaPorId(prueba3.id);
        teatroMoro.mostrarEntradaPorId(prueba4.id);

        // Mostrar lista completa de entradas
        System.out.println("\nLista de todas las entradas vendidas:");
        teatroMoro.mostrarListaEntradasVendidas();

        // Mostrar estadísticas finales
        System.out.println("\nEstadísticas finales del teatro:");
        teatroMoro.mostrarEstadisticas();
        */

        // Cierre Scanner
        scanner.close(); 
    }
    
}
