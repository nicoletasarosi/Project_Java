
package service;

import controller.observ.Observable;
import domain.StructuraAnUniversitar;
import domain.Tema;
import domain.validate.ValidationException;
import repository.TemaRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceTema extends Observable {

    private TemaRepository repo;
    private int id;

    public ServiceTema(TemaRepository repo) {
        this.repo=repo;
        if(getAll().size()!=0) {
            id = getAll().get(getAll().size() - 1).getId();
        }
        else id=0;
    }

    private int getSaptamana(Date start, StructuraAnUniversitar str){
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
                    saptamana=j;
                }
            }
        }
        return saptamana;
    }

    public void add(String descriere, int deadline, Date start, StructuraAnUniversitar str) throws  Exception {
        id++;
        int star=getSaptamana(start,str);
        if(star!=0) {
            Tema tema = new Tema(id, descriere, star, deadline);
            repo.save(tema);
            notif();
        }
        else {
            throw new Exception("Ati dat un deadline in afara anului universitar");
        }
    }

    public void delete(int id){
        try {
            repo.delete(id);
            notif();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void update(int id, String descriere, int deadline, Date start, StructuraAnUniversitar str)throws Exception {
        int star = getSaptamana(start, str);
        if (star != 0) {
            Tema tema = new Tema(id, descriere, star, deadline);
            repo.update(tema);
            notif();
        } else {
            throw new Exception("Ati dat un deadline in afara anului universitar");
        }
    }

    public Tema getOne(int id){
        try {
            return repo.findOne(id);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Tema> getAll() {
        try {
            Iterable<Tema> it=repo.findAll();
            List<Tema> lt=new ArrayList<>();
            for(Tema t:it)
                lt.add(t);
            return lt;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
