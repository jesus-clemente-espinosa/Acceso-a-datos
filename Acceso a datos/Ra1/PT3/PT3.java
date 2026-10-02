
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PT3 {
    private static final String FITXER = "juegos.dat";
    public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
         ArrayList<Juego> juegos=new ArrayList<>();
         int opcio ;
         do{
              System.out.println("\n--- Menú ---");
              System.out.println("1. Afegir videojoc");
              System.out.println("2. Llistar tots els videojocs");
              System.out.println("3. Cerca videojocs per titol");
              System.out.println("4. Actualitza un videojoc");
              System.out.println("5. Eliminar un videojoc");
              System.out.println("6. Sortir del programa");
              System.out.print("Tria una opció: ");
            opcio = sc.nextInt();
            sc.nextLine(); // netejar buffer
            switch (opcio){
                case 1:
                    añadirJuego(sc,juegos);
                    break;
                case 2:
                    verJuego(juegos);
                    break;
                case 3:
                    buscarJuego(juegos,sc);
                    break;
                case 4:
                    modificarJuego(juegos,sc);
                    break;
                case 5:
                    eliminarJuego();
            }
         }while (opcio != 6);
    }
    public static void añadirJuego(Scanner sc,ArrayList <Juego> juegos){
        System.out.println("titol");
        String titol=sc.nextLine();
        System.out.println("Genere");
        String genere=sc.nextLine();
        int anyLlançament=sc.nextInt();
        sc.nextLine();
        String plataforma=sc.nextLine();
        int preu=sc.nextInt();
        Juego j=new Juego(titol,genere,plataforma,anyLlançament,preu);
        juegos.add(j);
         try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER))) {
            oos.writeObject(juegos);
        } catch (IOException e) {
            System.out.println("Error desant persones: " + e.getMessage());
        }
    }
    public static ArrayList<Juego> verJuego(ArrayList <Juego> juegos){
        File fitxer=new File(FITXER);
        if (fitxer.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
                juegos= (ArrayList<Juego>) ois.readObject();
                for (Juego j : juegos) {
                    System.out.println(j);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error carregant persones: " + e.getMessage());
            }
        }
        return juegos;
    }
    public static void buscarJuego(ArrayList <Juego> juegos,Scanner sc){
        System.out.println("Dime que titulo quieres buscar:");
        String titulo=sc.nextLine();
        File fitxer=new File(FITXER);
        if (fitxer.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
                juegos= (ArrayList<Juego>) ois.readObject();
                for (Juego j : juegos) {
                    if(titulo.equals(j.getTitol())){
                        System.out.println(j);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error carregant persones: " + e.getMessage());
            }
        }
    }
    public static void modificarJuego(ArrayList <Juego> juegos,Scanner sc){
        System.out.println("Dime que titulo quieres buscar:");
        String titulo=sc.nextLine();
        File fitxer=new File(FITXER);
        if (fitxer.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
                juegos= (ArrayList<Juego>) ois.readObject();
                for (Juego j : juegos) {
                    if(titulo.equals(j.getTitol())){
                        System.out.println("Dime el nuevo genero");
                        String nuevoGenero=sc.nextLine();
                        j.setGenere(nuevoGenero);
                        System.out.println("Dime la nueva plataforma");
                        String nuevaPlataforma=sc.nextLine();
                        j.setPlataforma(nuevaPlataforma);
                        System.out.println("Dime el nuevo año de lanzamiento");
                        int nuevoLanzamiento=sc.nextInt();
                        j.setAnyLlançament(nuevoLanzamiento);
                        sc.nextLine();
                        System.out.println("Dime el nuevo precio");
                        double nuevopreu=sc.nextDouble();
                        j.setPreu(nuevopreu);
                        try (ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream(FITXER))) {
                            oos.writeObject(juegos);
                        }catch (IOException e) {
                            System.out.println("Error guardando juegos: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error carregant persones: " + e.getMessage());
            }
        }
    }
    
 }
