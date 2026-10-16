import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class CrearZoo {
    private static final String FITXER = "zoo.xml";

    public static void main(String[] args) {
        try {
            // 1. Crear el constructor de documents DOM
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // 2. Element arrel <zoo>
            Element rootElement = doc.createElement("zoo");
            doc.appendChild(rootElement);

            // Dades d'exemple dels animals
            // Animal 1
            afegirAnimal(doc, rootElement, "1", "Pingüí", "Aptenodytes forsteri", "Peix", "5");
            // Animal 2
            afegirAnimal(doc, rootElement, "2", "Lleó", "Panthera leo", "Carn", "8");

            // 3. Escriure/Transofrmar el document DOM a un fitxer XML físic
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Propietat per donar format i sagnia al fitxer XML (més llegible)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FITXER));
            transformer.transform(source, result);

            System.out.println("S'ha creat correctament el fitxer: " + FITXER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mètode auxiliar per facilitar la creació de cada animal
    private static void afegirAnimal(Document doc, Element root, String id, String nom, String especie, String aliment, String edat) {
        Element animal = doc.createElement("animal");
        animal.setAttribute("id", id);

        Element elemNom = doc.createElement("nom");
        elemNom.appendChild(doc.createTextNode(nom));
        animal.appendChild(elemNom);

        Element elemEspecie = doc.createElement("especie");
        elemEspecie.appendChild(doc.createTextNode(especie));
        animal.appendChild(elemEspecie);

        Element elemAliment = doc.createElement("aliment");
        elemAliment.appendChild(doc.createTextNode(aliment));
        animal.appendChild(elemAliment);

        Element elemEdat = doc.createElement("edat");
        elemEdat.appendChild(doc.createTextNode(edat));
        animal.appendChild(elemEdat);

        root.appendChild(animal);
    }
}
/*
PARA LEER EL XML
 try {

            File fichero = new File("zoo.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(fichero);

            doc.getDocumentElement().normalize();

            NodeList listaAnimales = doc.getElementsByTagName("animal");

            for (int i = 0; i < listaAnimales.getLength(); i++) {

                Element animal = (Element) listaAnimales.item(i);

                String id = animal.getAttribute("id");

                String nom = animal.getElementsByTagName("nom")
                                   .item(0)
                                   .getTextContent();

                String especie = animal.getElementsByTagName("especie")
                                       .item(0)
                                       .getTextContent();

                String aliment = animal.getElementsByTagName("aliment")
                                       .item(0)
                                       .getTextContent();

                String edat = animal.getElementsByTagName("edat")
                                    .item(0)
                                    .getTextContent();

                System.out.println("ID: " + id);
                System.out.println("Nom: " + nom);
                System.out.println("Espècie: " + especie);
                System.out.println("Aliment: " + aliment);
                System.out.println("Edat: " + edat);
                System.out.println("----------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
