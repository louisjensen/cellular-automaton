package view;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * @author:  Louis Jensen
 * This class reads in all the xml configuration files and stores the info in the map
 */

public class XMLParser {

    private String simulationType;
    private int xSize;
    private int ySize;
    private HashMap<String, Double> randomInfo = new HashMap<String, Double>();
    private ArrayList<String> states = new ArrayList<>();
    private ArrayList<Double> stateProportions = new ArrayList<>();
    private String basedOnStates;

    public XMLParser(String filepath){
        File file = new File(filepath);
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            basedOnStates = doc.getElementsByTagName("basedOnStates").item(0).getTextContent();
            simulationType = doc.getElementsByTagName("type").item(0).getTextContent();
            xSize = Integer.parseInt(doc.getElementsByTagName("horizontalSize").item(0).getTextContent());
            ySize = Integer.parseInt(doc.getElementsByTagName("verticalSize").item(0).getTextContent());
            NodeList list = doc.getElementsByTagName("property");
            for(int i = 0; i < list.getLength(); i++){
                String s = list.item(i).getTextContent();
                String[] split = s.split(" ");
                states.add(split[0]);
                stateProportions.add(Double.parseDouble(split[1]));
            }
            NodeList list2 = doc.getElementsByTagName("info");
            for(int i = 0; i < list2.getLength(); i++){
                String s2 = list2.item(i).getTextContent();
                String[] split2 = s2.split(" ");
                randomInfo.put(split2[0], Double.parseDouble(split2[1]));
            }
        }
        catch (Exception e) {
           //Should Never happen - no response
        }
    }

    public String getSimulationType(){
        return simulationType;
    }

    public int getGridX(){
        return xSize;
    }

    public int getGridY(){
        return ySize;
    }

    public HashMap<String, Double> getRandomInfo(){
        return randomInfo;
    }

    public ArrayList<String> getStates(){
        return states;
    }

    public ArrayList<Double> getStateProportions() {
        return stateProportions;
    }

    public boolean isItBasedOnStates(){
        return basedOnStates.equals("Yes");
    }
}