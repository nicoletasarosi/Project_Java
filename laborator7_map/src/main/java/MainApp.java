import controller.*;
import domain.Nota;
import domain.StructuraAnUniversitar;
import domain.Student;
import domain.Tema;
import domain.validate.ValidationNota;
import domain.validate.ValidationStudent;
import domain.validate.ValidationTema;
import domain.validate.Validator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import repository.NotaRepository;
import repository.StudentRepository;
import repository.TemaRepository;
import service.config.ApplicationContext;
import service.ServiceNota;
import service.StudentService;
import service.ServiceTema;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Validator<Student> validStud=new ValidationStudent();
        String filenameStud= ApplicationContext.getPROPERTIES().getProperty("data.studentiXML");
        StudentRepository repoStud=new StudentRepository(filenameStud,validStud);
        StudentService serviceStudent=new StudentService(repoStud);

        Validator<Tema> validTem=new ValidationTema();
        String filenameTem= ApplicationContext.getPROPERTIES().getProperty("data.temeXML");
        TemaRepository repoTem=new TemaRepository(filenameTem,validTem);
        ServiceTema serviceTema=new ServiceTema(repoTem);

        Validator<Nota> validNota=new ValidationNota();
        String filenameNota= ApplicationContext.getPROPERTIES().getProperty("data.noteXML");
        NotaRepository repoNot=new NotaRepository(filenameNota,validNota);
        ServiceNota serviceNota=new ServiceNota(repoNot,serviceStudent,serviceTema);

        FXMLLoader loaderPagPrinc = new FXMLLoader();
        loaderPagPrinc.setLocation(getClass().getResource("/pagPrinc.fxml"));
        BorderPane rootPagPrinc=loaderPagPrinc.load();

        FXMLLoader loaderData = new FXMLLoader();
        loaderData.setLocation(getClass().getResource("/DataView.fxml"));
        GridPane rootData = loaderData.load();

        FXMLLoader loaderStudent = new FXMLLoader();
        loaderStudent.setLocation(getClass().getResource("/StudentView.fxml"));
        BorderPane rootStudent=loaderStudent.load();

        FXMLLoader loaderTema = new FXMLLoader();
        loaderTema.setLocation(getClass().getResource("/TemaView.fxml"));
        BorderPane rootTema=loaderTema.load();

        FXMLLoader loaderNota = new FXMLLoader();
        loaderNota.setLocation(getClass().getResource("/NotaView.fxml"));
        BorderPane rootNota=loaderNota.load();

        FXMLLoader loaderVizFinalaNota = new FXMLLoader();
        loaderVizFinalaNota.setLocation(getClass().getResource("/VizFinalaNotaView.fxml"));
        GridPane rootVizFinalaNota = loaderVizFinalaNota.load();


        PagPrincController controllerPagPrinc = loaderPagPrinc.getController();
        StudentController controllerStudent = loaderStudent.getController();
        TemaController controllerTema = loaderTema.getController();
        DataController controllerData = loaderData.getController();
        NotaController controllerNota=loaderNota.getController();
        VizFinalaNotaController controllerVizFinalaNota = loaderVizFinalaNota.getController();


        Scene scenePagPrinc = new Scene(rootPagPrinc);
        Stage stagePagPrinc = new Stage();
        stagePagPrinc.setTitle("Pagina Principala");
        stagePagPrinc.setScene(scenePagPrinc);
        stagePagPrinc.setWidth(640);

        Scene sceneStudent = new Scene(rootStudent);
        Stage stageStudent = new Stage();
        stageStudent.setTitle("Meniu gestiune studenti");
        stageStudent.setScene(sceneStudent);
        stageStudent.setWidth(715);

        Scene sceneTema = new Scene(rootTema);
        Stage stageTema = new Stage();
        stageTema.setTitle("Meniu gestiune teme");
        stageTema.setScene(sceneTema);
        stageTema.setWidth(715);

        Scene sceneNota = new Scene(rootNota);
        Stage stageNota = new Stage();
        stageNota.setTitle("Meniu gestiune note");
        stageNota.setScene(sceneNota);
        stageNota.setWidth(800);
        stageNota.setHeight(600);

        Scene sceneVizFinalaNota = new Scene(rootVizFinalaNota);
        Stage stageVizFinalaNota = new Stage();
        stageVizFinalaNota.setTitle("Vizualizare Finala");
        stageVizFinalaNota.setScene(sceneVizFinalaNota);
        stageVizFinalaNota.setWidth(365);

        controllerStudent.set(serviceStudent,stagePagPrinc,stageStudent,stageTema,stageNota);
        controllerTema.set(serviceTema,stagePagPrinc,stageStudent,stageTema,stageNota);
        controllerNota.set(serviceNota,stagePagPrinc,stageStudent,stageTema,stageNota,stageVizFinalaNota,controllerVizFinalaNota);
        controllerPagPrinc.set(stageStudent,stageTema,stageNota,stagePagPrinc);

        serviceStudent.addObserver(controllerNota);
        serviceTema.addObserver(controllerNota);

        Scene sceneData = new Scene(rootData);
        stage.setTitle("Data");
        stage.setScene(sceneData);
        controllerData.set(stage,stagePagPrinc,controllerTema,serviceNota);
        stage.setWidth(365);
        stage.show();

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                controllerStudent.clear();
                controllerTema.clear();
                controllerNota.clear();
            }
        };

        sceneStudent.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        sceneTema.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        sceneNota.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);


    }

    public static void main(String[] args) {
        launch(args);
    }


}