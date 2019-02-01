package view;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public abstract class XMLParser {

    private Element simulationType;

    public void readXML(){
        try {
            File file = new File("\\data\\SegregationConfiguration.XML");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            simulationType = doc.getElementById("type");
            System.out.println(simulationType);
            String s = simulationType.getTextContent();
        }

        catch (Exception e) {
           e.printStackTrace();
        }
    }

     /*private void openFile(File file) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    FileLoader.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }*/


    
}
