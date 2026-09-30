import java.io.*;
import java.util.Scanner;

public class RA1PTA2 {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        System.out.print("Introduce la clave: ");
        int clave = teclado.nextInt();

        File entrada = new File("C:\\Users\\jclemente\\Desktop\\entrada.txt");
        File cifrat = new File("C:\\Users\\jclemente\\Desktop\\xifrat.txt");
        File desxifrat = new File("C:\\Users\\jclemente\\Desktop\\desxifrat.txt");

        // CIFRAR
        try (
            BufferedReader br = new BufferedReader(new FileReader(entrada));
            BufferedWriter bw = new BufferedWriter(new FileWriter(cifrat))
        ) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String invertida = new StringBuilder(linea)
                        .reverse()
                        .toString();

                for (int i = 0; i < invertida.length(); i++) {

                    char letra = invertida.charAt(i);
                    char nuevaLetra = (char) (letra + clave);

                    bw.write(nuevaLetra);
                }

                bw.newLine();
            }

            System.out.println("Fichero cifrado correctamente.");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // DESCIFRAR
        try (
            BufferedReader br = new BufferedReader(new FileReader(cifrat));
            BufferedWriter bw = new BufferedWriter(new FileWriter(desxifrat))
        ) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String texto = "";

                for (int i = 0; i < linea.length(); i++) {

                    char letra = linea.charAt(i);
                    char nuevaLetra = (char) (letra - clave);

                    texto += nuevaLetra;
                }

                String original = new StringBuilder(texto)
                        .reverse()
                        .toString();

                bw.write(original);
                bw.newLine();
            }

            System.out.println("Fichero descifrado correctamente.");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        teclado.close();
    }
}