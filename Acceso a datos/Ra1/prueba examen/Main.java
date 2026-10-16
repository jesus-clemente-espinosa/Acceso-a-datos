import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import org.w3c.dom.NodeList;

public class Main{
    private static final String FITXER = "estudiants.xml";
    private static final String FITXER2= "alumne.dat";
    public static void main(String[] args) {
        ArrayList<estudiant> estudiants=new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(FITXER);
            doc.getDocumentElement().normalize();
            NodeList listaEstudiants = doc.getElementsByTagName("estudiant");
            for(int i=0;i< listaEstudiants.getLength();i++){
                Element estudiant =(Element) listaEstudiants.item(i);
                int id=Integer.parseInt(estudiant.getAttribute("id"));
                String nom=estudiant.getElementsByTagName("nom").item(0).getTextContent();
                int edat=Integer.parseInt(estudiant.getElementsByTagName("edat").item(0).getTextContent());
                double nota=Double.parseDouble(estudiant.getElementsByTagName("nota").item(0).getTextContent());
                estudiant e=new estudiant(id,nom,edat,nota);
                estudiants.add(e);
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER2))) {
                    oos.writeObject(estudiants);
                } catch (IOException e) {
                    System.out.println("Error desant persones: " + e.getMessage());
                }
        } catch (Exception e) {
            // TODO: handle exception
        }
       
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FITXER2))) {
                estudiants= (ArrayList<estudiant>) ois.readObject();
                for (estudiant j : estudiants ) {
                    System.out.println(j);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error carregant persones: " + e.getMessage());
            }
        


    }
}