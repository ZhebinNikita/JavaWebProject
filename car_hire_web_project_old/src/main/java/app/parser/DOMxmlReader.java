package app.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMxmlReader {

    private String filepath;



    public void Parse(){

        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Корневой элемент: " + doc.getDocumentElement().getNodeName());
            // получаем узлы с именем Language
            // теперь XML полностью загружен в память
            // в виде объекта Document
            NodeList nodeList = doc.getElementsByTagName("Car");

            // создадим из него список объектов Language
            List<CarDOM> carList = new ArrayList<CarDOM>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                carList.add(getCar(nodeList.item(i)));
            }

            // печатаем в консоль информацию по каждому объекту Language
            for (CarDOM car : carList) {
                System.out.println(car.toString());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }


    // создаем из узла документа объект Language
    private static CarDOM getCar(Node node) {
        CarDOM car = new CarDOM();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            car.setName(getTagValue("name", element));
            car.setDaily_rental_price(Double.parseDouble(getTagValue("daily_rental_price", element)));
            car.setCar_class(getTagValue("class", element));
            car.setBusyness(Integer.parseInt(getTagValue("busyness", element)));
        }
        return car;
    }

    // получаем значение элемента по указанному тегу
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }


}
