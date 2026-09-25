import java.io.*;
public class AccesoaDatos {

    public static void main(String[] args) {
        int linea = 0;
        int paraules = 0;
        int contador_caracteres = 0;
        int[] frequencia = new int[65536];
        File fitxer = new File("PT1");

        // Utilitzem BufferedReader per llegir línia a línia de manera eficient
        try (BufferedReader br = new BufferedReader(new FileReader(fitxer))) {
            String liniaText;
            
            while ((liniaText = br.readLine()) != null) {
                linea++; // Cada vegada que llegeix una línia amb readLine(), sumem una línia
                
                // Si el fitxer té salts de línia, el BufferedReader no inclou el \n al final, 
                // per tant sumem 1 caràcter pel salt de línia de cada línia processada (excepte potser l'última, 
                // o podem sumar la longitud de la línia + 1 pel \n).
                contador_caracteres += liniaText.length() + 1; 

                // Omplir freqüències amb els caràcters de la línia
                for (int i = 0; i < liniaText.length(); i++) {
                    char c = liniaText.charAt(i);
                    frequencia[c]++;
                }
                // Comptem el salt de línia('\n') a la freqüència
                frequencia['\n']++;

                // Comptar paraules separades per espais o tabuladors dins de la línia
                String liniaNeta = liniaText.trim();
                if (!liniaNeta.isEmpty()) {
                    // Partim la línia per un o més espais/tabuladors
                    String[] tokens = liniaNeta.split("\\s+");
                    paraules += tokens.length;
                }
            }
            
            // Resultats per comprovar que tot funciona
            System.5.out.println("Línies: " + linea);
            System.out.println("Paraules: " + paraules);
            System.out.println("Caràcters totals: " + contador_caracteres);

        } catch (IOException e) {
            System.err.println("S'ha produït un error en llegir el fitxer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}