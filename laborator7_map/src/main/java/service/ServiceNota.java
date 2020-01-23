
package service;
import domain.Nota;
import domain.StructuraAnUniversitar;
import domain.Student;
import domain.Tema;
import domain.validate.ValidationException;
import repository.NotaRepository;
import service.StudentService;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceNota {
    private NotaRepository repo;
    private StudentService servstud;
    private ServiceTema servtem;
    private StructuraAnUniversitar str;
    private int id;

    public ServiceNota(NotaRepository repo,StudentService servstud,ServiceTema servtem){
        this.repo=repo;
        this.servstud=servstud;
        this.servtem=servtem;
        if(getAll().size()!=0) {
            id = getAll().get(getAll().size() - 1).getId();
        }
        else id=0;
    }

    public Student getStudent(int id){
        return servstud.getOne(id);
    }

    public Tema getTema(int id){
        return servtem.getOne(id);
    }

    public List<Student> getAllStudent(){
        return servstud.getAll();
    }

    public List<Tema> getAllTema(){
        return servtem.getAll();
    }

    private int getSaptamana(Date start){
        int saptamana=0,i=1,j=0;
        Date[] date1=str.getSem1().getSem();
        for(i=1;i<28 && saptamana==0;i+=2){
            j++;
            if(!date1[i-1].after(start) && !date1[i].before(start)){
                saptamana=j;
            }
        }
        if (saptamana==0){
            date1=str.getSem2().getSem();
            j=0;
            for(i=1;i<28 && saptamana==0;i+=2){
                j++;
                if(!date1[i-1].after(start) && !date1[i].before(start)){
                    saptamana=j+14;
                }
            }
        }
        return saptamana;
    }

    private float intarziere(int saptamana, Tema t, Nota n){
        int nota=1;
        if(saptamana<=t.getDeadlineWeek())
            nota=10;
        if(saptamana==t.getDeadlineWeek()+1)
            nota = 9;
        if(saptamana==t.getDeadlineWeek()+2)
            nota=8;
        return nota-n.getPenalizari();
    }

    private void addPrivateFile(Student s){
        Iterable<Nota> note=repo.getNoteStudent(s.getId());
        String file="D:\\Proiecte MAP + PLF\\MAP\\Lab7Gui\\data\\fisiereStudenti\\"+s.getNume()+s.getPrenume();
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Nota n : note) {
                Tema t = servtem.getOne(n.getTemaID());
                int saptamana = getSaptamana(n.getDataPredare());
                pw.println("Tema:" + n.getTemaID());
                pw.println("Nota:" + intarziere(saptamana, t, n));
                pw.println("Predata in saptamana:" + saptamana);
                pw.println("Deadline:" + t.getDeadlineWeek());
                pw.println("Feedback:" + n.getFeedback());
                pw.println("");
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void addFilePrivateFile(StructuraAnUniversitar str){
        this.str=str;
        for(Student s:servstud.getAll()){
            addPrivateFile(s);
        }
    }

    public void add(int id_stud,int id_tem,Date data, String profesor, String feedback,float penalizari,int[] verificare)throws Exception {
        id++;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String mdata = dateFormat.format(date);
        Date df = dateFormat.parse(mdata);
        if (data.before(df)) {
            verificare[0]=1;
        }
        Iterable<Nota> note = repo.getNoteStudent(id_stud);
        int ok = 0;
        for (Nota n : note) {
            if (n.getTemaID() == id_tem) {
                ok = 1;
                break;
            }
        }
        if (ok == 0) {
            Nota nota = new Nota(id, id_stud, id_tem, data, profesor, feedback, penalizari);
            repo.save(nota);
            addPrivateFile(servstud.getOne(id_stud));
        } else {
            throw new Exception("Un student are o singura nota la un laborator");
        }
    }

    public void update(int id,int id_stud,int id_tem,Date data, String profesor, String feedback,float penalizari) throws  ValidationException,IllegalArgumentException {
        Nota nota = new Nota(id, id_stud, id_tem, data, profesor, feedback, penalizari);
        repo.update(nota);
        addPrivateFile(servstud.getOne(id_stud));
    }

    public void delete(int id){
        try {
            Nota n=repo.delete(id);
            addPrivateFile(servstud.getOne(n.getStudentID()));
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Nota getOne(int id){
        try {
            return repo.findOne(id);
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Nota> getAll(){
        try {
            List<Nota> l=new ArrayList<>();
            for(Nota n:repo.findAll())
                l.add(n);
            return l;
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public int notaMaxStudent(int id_tem, Date data){
        int saptamana=getSaptamana(data);
        int deadline=servtem.getOne(id_tem).getDeadlineWeek();
        int nota=1;
        if(saptamana<=deadline)
            nota=10;
        if(saptamana==deadline+1)
            nota = 9;
        if(saptamana==deadline+2)
            nota=8;
        return nota;
    }

    public List<Integer> studentiTema(int tema){
        Iterable<Nota> n=repo.findAll();
        List<Nota> ln=new ArrayList<>();
        for(Nota not:n)
            ln.add(not);
        return ln.stream().filter(x->x.getTemaID()==tema).map(Nota::getStudentID).collect(Collectors.toList());
    }

    public List<Integer> studentiTemaProfesor(int tema, String profesor){
        Iterable<Nota> n=repo.findAll();
        List<Nota> ln=new ArrayList<>();
        for(Nota not:n)
            ln.add(not);
        return ln.stream().filter(x->x.getTemaID()==tema && x.getProfesor().equals(profesor)).map(Nota::getStudentID).collect(Collectors.toList());

    }

    public List<Integer> noteTemaSaptamana(int tema, int saptamana){
        Iterable<Nota> n=repo.findAll();
        List<Nota> ln=new ArrayList<>();
        for(Nota not:n)
            ln.add(not);
        return ln.stream().filter(x->getSaptamana(x.getDataPredare())==saptamana && x.getTemaID()==tema).map(Nota::getId).collect(Collectors.toList());
    }

    public Tema getTemaCurenta(int saptamana){
        List<Tema> list = getAllTema();
        for(Tema tema: list){
            if(tema.getDeadlineWeek() == saptamana)
                return tema;
        }
        return getTema(1);
    }

    private static DecimalFormat df = new DecimalFormat("0.00");

    public double medie(Student student ){
        double suma=0;
        double nr =0;
        int ok=0;
        List<Nota> note = getAll();
        for (Nota nota: note){
            if(nota.getStudentID() == student.getId()){
                ok=1;
                int idTema = nota.getTemaID();
                Tema tema = getTema(idTema);
                int nrSaptamani = tema.getDeadlineWeek() - tema.getStartWeek();
                float nota1= notaMaxStudent(idTema, nota.getDataPredare())-nota.getPenalizari();
                suma += nota1*nrSaptamani;
                nr+=nrSaptamani;

            }

        }
        if(ok==0) return 1;
        return suma/nr;
    }

    public String mediePonderata(){
        double suma,nr ;
        HashMap<Student,String> catalog = new HashMap<>();
        List<Nota> note = getAll();
        List<Student> students = getAllStudent();
        String s="";
        for(Student student:students){
            catalog.put(student,df.format(medie(student)));
            s+=student.getNume() + " "+student.getPrenume()+"->"+catalog.get(student)+'\n';

        }
        return s ;

    }

    public float medieTema(Tema t) {
        List<Nota> note = getAll();
        int suma = 0, ok = 0;
        int nr = 0;
        for (Nota nota : note) {

            if (nota.getTemaID() == t.getId()) {
                ok = 1;
                float n = notaMaxStudent(t.getId(), nota.getDataPredare())- nota.getPenalizari();
                suma += n;
                nr++;
            }
        }
        if (ok == 0) return 1;
        return suma / nr;
    }

    public String  temaCeaMaiGrea() {
        float min = 11;
        String temaGrea = "";
        /*
        List<Float> list= getAllTema().stream()
                .map(x->medieTema(x))
                .collect(Collectors.toList());
         */
        for (Tema tema : getAllTema()) {
            if (medieTema(tema) < min) {
                min = medieTema(tema);
                temaGrea = tema.getDescriere();
            }
        }
        return temaGrea;
    }

    public String studentiExamen(){
        double medie = 0;
        double cati;
        String studenti="";
        for(Student student:servstud.getAll()){
            medie = 0;
            cati=0;
            for(Nota n: repo.findAll()){
                if(n.getStudentID() == student.getId()){
                    medie+= notaMaxStudent(n.getTemaID(), n.getDataPredare()) - n.getPenalizari();
                    cati++;

                }
            }
            medie/=cati;
            if(medie>=4){
                studenti+=student.toString()+"\n";
            }

        }
        return studenti;
    }
/*
    public String studentiLaTimp(){
        String studenti="";
        int ok;
        for(Student s:servstud.getAll()) {
            ok=1;
            for (Nota n : repo.findAll()) {
                if (n.getStudentID() == s.getId()) {
                    if(notaMaxStudent(n.getTemaID(), n.getDataPredare())==1) {
                        ok=0;
                    }
                }
            }
            if (ok==1) {
                studenti+=s.toString()+"\n";
            }
        }
        return studenti;
    }
*/
    public boolean laTimp(Student student) {
        List<Nota> note = getAll();
        boolean ok = false;
        for (Nota nota : note) {
            if (nota.getStudentID() == student.getId()) {
                ok = true;
                int idTema = nota.getTemaID();
                Tema tema = getTema(idTema);
                float nota1 = notaMaxStudent(idTema, nota.getDataPredare())-nota.getPenalizari();
                if (getSaptamana(nota.getDataPredare()) > tema.getDeadlineWeek())
                    ok = false;
            }
        }
        return ok;
    }



        public String studentiLaTimp() {
            String s = "";
            List<Student> lista = getAllStudent();
            List<String> studenti = lista.stream()
                    .filter(this::laTimp)
                    .map(Student::getNume)
                    .collect(Collectors.toList());
            for (String student : studenti)
                s += student + '\n';

            return s;
        }


}
