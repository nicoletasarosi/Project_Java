package service;

import controller.observ.Observable;
import domain.*;
import domain.validate.ValidationException;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentService extends Observable {
    private StudentRepository repo;
    private int id;

    public StudentService(StudentRepository repo){
        this.repo=repo;
        if(getAll().size()!=0) {
            id = getAll().get(getAll().size() - 1).getId();
        }
        else id=0;
    }

    /*
    private int cmmi(){
        int i=0;
        List<Student> list=getAll();
        if(list.size()==0)
            return 0;
        for(Student s:list) {
            if (s.getId() != i)
                return i;
            else i +=1;
        }
        return i+1;
    }
*/

    public void add(String nume,String prenume,int grupa,String email, String cadru) throws ValidationException,IllegalArgumentException{
        id++;
        Student stud=new Student(id,nume,prenume,grupa,email,cadru);
        repo.save(stud);
        notif();
    }

    public void delete(int id) throws IllegalArgumentException {
        repo.delete(id);
        notif();
    }

    public void update(int id,String nume, String prenume, int grupa,String email,String cadru) throws ValidationException,IllegalArgumentException {
        Student stud = new Student(id, nume, prenume, grupa,email, cadru);
        repo.update(stud);
        notif();
    }

    public Student getOne(int id) throws IllegalArgumentException {
        return repo.findOne(id);
    }

    public List<Student> getAll() throws IllegalArgumentException {
        List<Student> list = new ArrayList<>();
        for (Student s : repo.findAll())
            list.add(s);
        return list;
    }
}
