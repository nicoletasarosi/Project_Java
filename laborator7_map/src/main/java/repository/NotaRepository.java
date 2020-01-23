package repository;

import domain.Nota;
import domain.validate.ValidationException;
import domain.validate.Validator;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NotaRepository extends AbstractRepository<Integer, Nota> {
    private String fileName;

    public NotaRepository(String fileName, Validator<Nota> validator) {
        super(validator);
        this.fileName = fileName;
        loadFromFile();
    }

    private void loadFromFile(){
        try{

            File fXmlFile= new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("nota");

            //System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    int id=Integer.parseInt(eElement.getAttribute("id"));
                    int studentID=Integer.parseInt(eElement.getElementsByTagName("studentID").item(0).getTextContent());
                    int temaID=Integer.parseInt(eElement.getElementsByTagName("temaID").item(0).getTextContent());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date myDate = format.parse(eElement.getElementsByTagName("dataPredare").item(0).getTextContent());
                        String profesor=eElement.getElementsByTagName("profesor").item(0).getTextContent();
                        String feedback=eElement.getElementsByTagName("feedback").item(0).getTextContent();
                        float penalizari=Float.parseFloat(eElement.getElementsByTagName("penalizari").item(0).getTextContent());
                        Nota nota= new Nota(id,studentID,temaID,myDate,profesor,feedback,penalizari);
                        try {
                            super.save(nota);
                        }catch (ValidationException | IllegalArgumentException ex) {
                            System.out.println(ex.getMessage() + " la linia " + temp);
                        }
                    }catch (ParseException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void saveToFile(){
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("note");
            document.appendChild(root);
            for(Nota n:findAll()) {
                Element nota = document.createElement("nota");
                root.appendChild(nota);

                Attr attr = document.createAttribute("id");
                attr.setValue(Integer.toString(n.getId()));
                nota.setAttributeNode(attr);

                Element studentID = document.createElement("studentID");
                studentID.appendChild(document.createTextNode(Integer.toString(n.getStudentID())));
                nota.appendChild(studentID);


                Element temaID = document.createElement("temaID");
                temaID.appendChild(document.createTextNode(Integer.toString(n.getTemaID())));
                nota.appendChild(temaID);

                Date data=n.getDataPredare();
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                String d=dateFormat.format(data);

                Element dataPredare = document.createElement("dataPredare");
                dataPredare.appendChild(document.createTextNode(d));
                nota.appendChild(dataPredare);


                Element profesor = document.createElement("profesor");
                profesor.appendChild(document.createTextNode(n.getProfesor()));
                nota.appendChild(profesor);

                Element feedback = document.createElement("feedback");
                feedback.appendChild(document.createTextNode(n.getFeedback()));
                nota.appendChild(feedback);

                Element penalizari = document.createElement("penalizari");
                penalizari.appendChild(document.createTextNode(Float.toString(n.getPenalizari())));
                nota.appendChild(penalizari);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(fileName));

            transformer.transform(domSource, streamResult);

            //System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    @Override
    public Nota save(Nota entity) throws ValidationException, IllegalArgumentException {
        Nota n=super.save(entity);
        saveToFile();
        return n;
    }

    @Override
    public Nota update(Nota entity) throws ValidationException, IllegalArgumentException {
        Nota n=super.update(entity);
        saveToFile();
        return n;
    }

    @Override
    public Nota delete(Integer integer) throws IllegalArgumentException {
        Nota n=super.delete(integer);
        saveToFile();
        return n;
    }

    public Iterable<Nota> getNoteStudent(Integer id){
        HashMap<Integer, Nota> m=new HashMap<>();
        for(Nota n:findAll()){
            if(n.getStudentID()==id)
                m.put(n.getId(),n);
        }
        return m.values();
    }
}

