package cat.botiga.botiga.online;
import dao.ClientDAO;
import dao.ProducteDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.Client;
import model.Producte;
import dao.ComandaDAO;
import dao.ConsultesDAO;
import java.util.ArrayList;
import model.LiniaComanda;
import dao.DescompteDAO;
import model.Descompte;
public class App {

    private static final Scanner sc = new Scanner(System.in);
    private static final ProducteDAO producteDAO = new ProducteDAO();
    private static final ClientDAO clientDAO = new ClientDAO();
    private static final ComandaDAO comandaDAO = new ComandaDAO();
    private static final ConsultesDAO consultesDAO = new ConsultesDAO();
    private static final DescompteDAO descompteDAO = new DescompteDAO();
    public static void main(String[] args) {
        int opcio;
        do {
            System.out.println("\n===== BOTIGA ONLINE =====");
            System.out.println("1. Gestionar Productes");
            System.out.println("2. Gestionar Clients");
            System.out.println("3. Crear Comanda");
            System.out.println("4. Llistar Comandes");
            System.out.println("5. Gestionar Descomptes");
            System.out.println("0. Sortir");
            opcio = llegirInt("Opció: ");

            switch (opcio) {
                case 1: menuProductes(); break;
                case 2: menuClients(); break;
                case 3: menuCrearComanda(); break;
               case 4: menuConsultes(); break;
               case 5: menuDescomptes(); break;
                case 0: System.out.println("Adéu!"); break;
                default: System.out.println("Opció no vàlida.");
            }
        } while (opcio != 0);
        sc.close();
    }

    // ---------- MENÚ PRODUCTES ----------
    private static void menuProductes() {
        int opcio;
        do {
            System.out.println("\n--- PRODUCTES ---");
            System.out.println("1. Llistar");
            System.out.println("2. Afegir");
            System.out.println("3. Modificar");
            System.out.println("4. Eliminar");
            System.out.println("0. Tornar");
            opcio = llegirInt("Opció: ");

            try {
                switch (opcio) {
                    case 1:
                        List<Producte> productes = producteDAO.llistar();
                        if (productes.isEmpty()) {
                            System.out.println("No hi ha productes.");
                        }
                        for (Producte p : productes) {
                            System.out.printf("%d - %s | %.2f € | estoc: %d%n",
                                    p.getId(), p.getNom(), p.getPreu(), p.getEstoc());
                        }
                        break;
                    case 2:
                        String nom = llegirText("Nom: ");
                        double preu = llegirDouble("Preu: ");
                        int estoc = llegirInt("Estoc: ");
                        producteDAO.inserir(new Producte(0, nom, preu, estoc));
                        System.out.println("Producte afegit.");
                        break;
                    case 3:
                        int idMod = llegirInt("Id del producte a modificar: ");
                        String nouNom = llegirText("Nou nom: ");
                        double nouPreu = llegirDouble("Nou preu: ");
                        int nouEstoc = llegirInt("Nou estoc: ");
                        producteDAO.actualitzar(new Producte(idMod, nouNom, nouPreu, nouEstoc));
                        System.out.println("Producte modificat (si l'id existia).");
                        break;
                    case 4:
                        int idDel = llegirInt("Id del producte a eliminar: ");
                        producteDAO.eliminar(idDel);
                        System.out.println("Producte eliminat (si l'id existia).");
                        break;
                    case 0: break;
                    default: System.out.println("Opció no vàlida.");
                }
            } catch (SQLException e) {
                gestionarError(e);
            }
        } while (opcio != 0);
    }

    // ---------- MENÚ CLIENTS ----------
    private static void menuClients() {
        int opcio;
        do {
            System.out.println("\n--- CLIENTS ---");
            System.out.println("1. Llistar");
            System.out.println("2. Afegir");
            System.out.println("3. Modificar");
            System.out.println("4. Eliminar");
            System.out.println("0. Tornar");
            opcio = llegirInt("Opció: ");

            try {
                switch (opcio) {
                    case 1:
                        List<Client> clients = clientDAO.llistar();
                        if (clients.isEmpty()) {
                            System.out.println("No hi ha clients.");
                        }
                        for (Client cl : clients) {
                            System.out.println(cl);
                        }
                        break;
                    case 2:
                        String nom = llegirText("Nom: ");
                        String correu = llegirText("Correu: ");
                        clientDAO.inserir(new Client(0, nom, correu));
                        System.out.println("Client afegit.");
                        break;
                    case 3:
                        int idMod = llegirInt("Id del client a modificar: ");
                        String nouNom = llegirText("Nou nom: ");
                        String nouCorreu = llegirText("Nou correu: ");
                        if (clientDAO.actualitzar(new Client(idMod, nouNom, nouCorreu))) {
                            System.out.println("Client modificat.");
                        } else {
                            System.out.println("No existeix cap client amb aquest id.");
                        }
                        break;
                    case 4:
                        int idDel = llegirInt("Id del client a eliminar: ");
                        if (clientDAO.eliminar(idDel)) {
                            System.out.println("Client eliminat.");
                        } else {
                            System.out.println("No existeix cap client amb aquest id.");
                        }
                        break;
                    case 0: break;
                    default: System.out.println("Opció no vàlida.");
                }
            } catch (SQLException e) {
                gestionarError(e);
            }
        } while (opcio != 0);
    }

    // ---------- GESTIÓ D'ERRORS ----------
    private static void gestionarError(SQLException e) {
        switch (e.getErrorCode()) {
            case 1062:
                System.out.println("Error: ja existeix un registre amb aquest valor (correu duplicat).");
                break;
            case 1451:
                System.out.println("Error: no es pot eliminar, hi ha dades que en depenen (comandes o descomptes).");
                break;
            default:
                System.out.println("Error de base de dades: " + e.getMessage());
        }
    }

    // ---------- LECTURA SEGURA ----------
    private static String llegirText(String missatge) {
        System.out.print(missatge);
        return sc.nextLine().trim();
    }

    private static int llegirInt(String missatge) {
        while (true) {
            try {
                return Integer.parseInt(llegirText(missatge));
            } catch (NumberFormatException e) {
                System.out.println("Introdueix un número enter vàlid.");
            }
        }
    }

    private static double llegirDouble(String missatge) {
        while (true) {
            try {
                return Double.parseDouble(llegirText(missatge).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Introdueix un número vàlid (ex: 19.99).");
            }
        }
    }
    private static void menuCrearComanda() {
        try {
            int clientId = llegirInt("Id del client: ");
            if (clientDAO.buscarPerId(clientId) == null) {
                System.out.println("No existeix aquest client.");
                return;
            }

            List<LiniaComanda> linies = new ArrayList<>();
            String seguir;
            do {
                int producteId = llegirInt("Id del producte: ");
                int quantitat = llegirInt("Quantitat: ");
                LiniaComanda l = new LiniaComanda();
                l.setProducteId(producteId);
                l.setQuantitat(quantitat);
                linies.add(l);
                seguir = llegirText("Afegir un altre producte? (s/n): ");
            } while (seguir.equalsIgnoreCase("s"));

            int id = comandaDAO.crearComanda(clientId, linies);
            System.out.println("Comanda " + id + " creada correctament.");

        } catch (SQLException e) {
            System.out.println("Comanda cancel·lada (rollback): " + e.getMessage());
        }
    }
    private static void menuConsultes() {
        System.out.println("\n--- CONSULTES ---");
        System.out.println("1. Comandes d'un client");
        System.out.println("2. Totals de totes les comandes");
        System.out.println("0. Tornar");
        int opcio = llegirInt("Opció: ");

        try {
            switch (opcio) {
                case 1:
                    int clientId = llegirInt("Id del client: ");
                    consultesDAO.llistarComandesClient(clientId);
                    break;
                case 2:
                    consultesDAO.llistarTotalsComandes();
                    break;
                case 0: break;
                default: System.out.println("Opció no vàlida.");
            }
        } catch (SQLException e) {
            System.out.println("Error de base de dades: " + e.getMessage());
        }
    }
    private static void menuDescomptes() {
    int opcio;
    do {
        System.out.println("\n--- DESCOMPTES ---");
        System.out.println("1. Llistar");
        System.out.println("2. Afegir");
        System.out.println("3. Modificar");
        System.out.println("4. Eliminar");
        System.out.println("0. Tornar");
        opcio = llegirInt("Opció: ");

        try {
            switch (opcio) {
                case 1:
                    List<Descompte> descomptes = descompteDAO.llistar();
                    if (descomptes.isEmpty()) {
                        System.out.println("No hi ha descomptes.");
                    }
                    for (Descompte d : descomptes) {
                        System.out.println(d);
                    }
                    break;
                case 2:
                    int prodId = llegirInt("Id del producte: ");
                    String tipus = llegirTipus();
                    double valor = llegirValor(tipus);
                    descompteDAO.inserir(new Descompte(0, prodId, tipus, valor));
                    System.out.println("Descompte afegit.");
                    break;
                case 3:
                    int idMod = llegirInt("Id del descompte a modificar: ");
                    int nouProd = llegirInt("Nou id de producte: ");
                    String nouTipus = llegirTipus();
                    double nouValor = llegirValor(nouTipus);
                    if (descompteDAO.actualitzar(new Descompte(idMod, nouProd, nouTipus, nouValor))) {
                        System.out.println("Descompte modificat.");
                    } else {
                        System.out.println("No existeix cap descompte amb aquest id.");
                    }
                    break;
                case 4:
                    int idDel = llegirInt("Id del descompte a eliminar: ");
                    if (descompteDAO.eliminar(idDel)) {
                        System.out.println("Descompte eliminat.");
                    } else {
                        System.out.println("No existeix cap descompte amb aquest id.");
                    }
                    break;
                case 0: break;
                default: System.out.println("Opció no vàlida.");
            }
        } catch (SQLException e) {
            gestionarError(e);
        }
    } while (opcio != 0);
}

private static String llegirTipus() {
    while (true) {
        String t = llegirText("Tipus (% o €): ");
        if (t.equals("%") || t.equals("€")) {
            return t;
        }
        System.out.println("El tipus ha de ser % o €.");
    }
}

private static double llegirValor(String tipus) {
    while (true) {
        double v = llegirDouble("Valor: ");
        if (v <= 0) {
            System.out.println("El valor ha de ser positiu.");
        } else if (tipus.equals("%") && v > 100) {
            System.out.println("Un percentatge no pot superar 100.");
        } else {
            return v;
        }
    }
}
}