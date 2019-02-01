
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public abstract class XMLParser {

    private Element simulationType;
    private String nameOfSimulation;
    private Element xSize;
    private int xLength;
    private Element ySize;
    private int yLength;
    private NodeList simData;

    private void readXML(){
        try {
            File file = new File("\\data\\SegregationConfiguration.XML");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            simulationType = doc.getElementById("type");
            nameOfSimulation = simulationType.getTextContent();
            xSize = doc.getElementById("xSize");
            xLength = Integer.parseInt(xSize.getTextContent());
            ySize = doc.getElementById("ySize");
            yLength = Integer.parseInt(ySize.getTextContent());

            simData = doc.getElementsByTagName("specifics");
            for(int i =0; i < simData.getLength(); i++){
                Element e = (Element) simData.item(i);
            }
        }

        catch (Exception e) {
           e.printStackTrace();
        }
    }
    
}
