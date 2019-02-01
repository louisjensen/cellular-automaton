package view;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

public class XMLParser {

<<<<<<< HEAD
    private String simulationType;
    private int xSize;
    private int ySize;
    private HashMap<String, Double> myMap = new HashMap<String, Double>();

    public XMLParser(String filePath){
=======
    private Element simulationType;
    private String nameOfSimulation;
    private Element xSize;
    private int xLength;
    private Element ySize;
    private int yLength;
    private NodeList simData;

    private void readXML(){
>>>>>>> 1d30fa1f97640bc9a0b1b4492f37078631bf7077
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

<<<<<<< HEAD
            simulationType = doc.getElementsByTagName("type").item(0).getTextContent();
            xSize = Integer.parseInt(doc.getElementsByTagName("horizontalSize").item(0).getTextContent());
            ySize = Integer.parseInt(doc.getElementsByTagName("verticalSize").item(0).getTextContent());
            NodeList list = doc.getElementsByTagName("property");
            for(int i = 0; i < list.getLength(); i++){
                String s = list.item(i).getTextContent();
                String[] split = s.split(" ");
                myMap.put(split[0], Double.parseDouble(split[1]));
                System.out.println(split[0] + " space " + split[1]);
=======
            simulationType = doc.getElementById("type");
            nameOfSimulation = simulationType.getTextContent();
            xSize = doc.getElementById("xSize");
            xLength = Integer.parseInt(xSize.getTextContent());
            ySize = doc.getElementById("ySize");
            yLength = Integer.parseInt(ySize.getTextContent());

            simData = doc.getElementsByTagName("specifics");
            for(int i =0; i < simData.getLength(); i++){
                Element e = (Element) simData.item(i);
>>>>>>> 1d30fa1f97640bc9a0b1b4492f37078631bf7077
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public String getSimulationType(){
        return simulationType;
    }

    public int getGridX(){
        return xSize;
    }
=======
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
    } */
>>>>>>> 1d30fa1f97640bc9a0b1b4492f37078631bf7077

    public int getGridY(){
        return ySize;
    }

    public HashMap<String, Double> getSpecifics(){
        return myMap;
    }

<<<<<<< HEAD
}
=======
}
>>>>>>> 1d30fa1f97640bc9a0b1b4492f37078631bf7077
