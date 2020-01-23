package repository;

import domain.Student;
import domain.validate.ValidationException;
import domain.validate.Validator;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Inet4Address;

public class StudentRepository extends AbstractRepository<Integer, Student> {
    private String filename;

    public StudentRepository(String filename,Validator<Student> validator){
        super(validator);
        this.filename=filename;
        loadFromFile();
    }

    private void loadFromFile(){
        try {

            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("student");

            //System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    int id = Integer.parseInt(eElement.getAttribute("id"));
                    String nume = eElement.getElementsByTagName("nume").item(0).getTextContent();
                    String prenume = eElement.getElementsByTagName("prenume").item(0).getTextContent();

                    int grupa = Integer.parseInt(eElement.getElementsByTagName("grupa").item(0).getTextContent());
                    String email = eElement.getElementsByTagName("email").item(0).getTextContent();
                    String cadruDidacticIndrumatorLab = eElement.getElementsByTagName("cadruDidacticIndrumatorLab").item(0).getTextContent();
                    Student student = new Student(id,nume,prenume,grupa,email,cadruDidacticIndrumatorLab);
                    try {
                        super.save(student);
                    } catch (ValidationException | IllegalArgumentException ex) {
                        System.out.println(ex.getMessage() + " la linia " + temp);
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
            Element root = document.createElement("studenti");
            document.appendChild(root);
            for(Student s:findAll()) {
                Element student = document.createElement("student");
                root.appendChild(student);

                Attr attr = document.createAttribute("id");
                attr.setValue(Integer.toString(s.getId()));
                student.setAttributeNode(attr);

                Element nume = document.createElement("nume");
                nume.appendChild(document.createTextNode(s.getNume()));
                student.appendChild(nume);


                Element prenume = document.createElement("prenume");
                prenume.appendChild(document.createTextNode(s.getPrenume()));
                student.appendChild(prenume);

                Element grupa = document.createElement("grupa");
                grupa.appendChild(document.createTextNode(Integer.toString(s.getGrupa())));
                student.appendChild(grupa);

                Element email = document.createElement("email");
                prenume.appendChild(document.createTextNode(s.getEmail()));
                student.appendChild(email);

                Element cadruDidacticIndrumatorLab = document.createElement("cadruDidacticIndrumatorLab");
                cadruDidacticIndrumatorLab.appendChild(document.createTextNode(s.getCadruDidacticIndrumatorLab()));
                student.appendChild(cadruDidacticIndrumatorLab);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filename));

            transformer.transform(domSource, streamResult);

            //System.out.println("Done creating XML File");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    @Override
    public Student save(Student entity) throws ValidationException, IllegalArgumentException {
        Student s=super.save(entity);
        saveToFile();
        return s;
    }

    @Override
    public Student update(Student entity) throws ValidationException, IllegalArgumentException {
        Student s=super.update(entity);
        saveToFile();
        return s;
    }

    @Override
    public Student delete(Integer integer) throws IllegalArgumentException {
        Student s=super.delete(integer);
        saveToFile();
        return s;
    }
}

/*
try(BufferedReader buffer=new BufferedReader(new FileReader(filename))){
            String linie;
            int i=-1;
            while((linie=buffer.readLine())!=null){
                String[] vec=linie.split(";");
                i++;
                if(vec.length!=5){
                    System.out.println("Linia "+i+" are un nr invalid de parametrii");
                }
                else {
                    Student stud=new Student(Integer.parseInt(vec[0]),vec[1],vec[2],Integer.parseInt(vec[3]),vec[4]);
                    try {
                        super.save(stud);
                    }catch (ValidationException |IllegalArgumentException ex){
                        System.out.println(ex.getMessage()+" la linia "+i);
                    }
                }
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
 */

/*
try(PrintWriter pw=new PrintWriter(filename)){
            for(Student s:findAll()){
                pw.println(s.getId()+";"+s.getNume()+";"+s.getPrenume()+";"+s.getGrupa()+";"+s.getCadruDidacticIndrumatorLab());
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
 */
