package repository;

import domain.Tema;
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
import java.io.File;

public class TemaRepository extends AbstractRepository<Integer, Tema> {
    private String filename;

    public TemaRepository(String filename, Validator<Tema> validator){
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

            NodeList nList = doc.getElementsByTagName("tema");

            //System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    int id = Integer.parseInt(eElement.getAttribute("id"));
                    String descriere = eElement.getElementsByTagName("descriere").item(0).getTextContent();
                    int startWeek = Integer.parseInt(eElement.getElementsByTagName("startWeek").item(0).getTextContent());

                    int deadlineWeek = Integer.parseInt(eElement.getElementsByTagName("deadlineWeek").item(0).getTextContent());
                    Tema tema = new Tema(id, descriere,startWeek,deadlineWeek);
                    try {
                        super.save(tema);
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
            Element root = document.createElement("teme");
            document.appendChild(root);
            for(Tema t:findAll()) {
                Element tema = document.createElement("tema");
                root.appendChild(tema);

                Attr attr = document.createAttribute("id");
                attr.setValue(Integer.toString(t.getId()));
                tema.setAttributeNode(attr);

                Element descriere = document.createElement("descriere");
                descriere.appendChild(document.createTextNode(t.getDescriere()));
                tema.appendChild(descriere);


                Element startWeek = document.createElement("startWeek");
                startWeek.appendChild(document.createTextNode(Integer.toString(t.getStartWeek())));
                tema.appendChild(startWeek);

                Element deadlineWeek = document.createElement("deadlineWeek");
                deadlineWeek.appendChild(document.createTextNode(Integer.toString(t.getDeadlineWeek())));
                tema.appendChild(deadlineWeek);
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
    public Tema save(Tema entity) throws ValidationException, IllegalArgumentException {
        Tema t=super.save(entity);
        saveToFile();
        return t;
    }

    @Override
    public Tema update(Tema entity) throws ValidationException, IllegalArgumentException {
        Tema t=super.update(entity);
        saveToFile();
        return t;
    }

    @Override
    public Tema delete(Integer integer) throws IllegalArgumentException {
        Tema t=super.delete(integer);
        saveToFile();
        return t;
    }
}

/*
try(BufferedReader buffer=new BufferedReader(new FileReader(filename))){
            String linie;
            int i=-1;
            while((linie=buffer.readLine())!=null){
                String[] vec=linie.split(";");
                i++;
                if(vec.length!=4){
                    System.out.println("Linia "+i+" are un nr invalid de parametrii");
                }
                else {
                    Tema tema=new Tema(Integer.parseInt(vec[0]),vec[1],Integer.parseInt(vec[2]),Integer.parseInt(vec[3]));
                    try {
                        super.save(tema);
                    }catch (ValidationException|IllegalArgumentException ex){
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
            for(Tema t:findAll()){
                pw.println(t.getId()+";"+t.getDescriere()+";"+t.getStartWeek()+";"+t.getDeadlineWeek());
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
 */