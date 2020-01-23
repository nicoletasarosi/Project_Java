package domain;

import java.util.Date;
import java.util.Objects;

public class Nota extends Entity<Integer> {
    private int studentID;
    private int temaID;
    private Date dataPredare ;
    private String profesor;
    private String feedback;
    private float penalizari;

    public Nota(Integer id,int studentID,int temaID,Date dataPredare,String profesor,String feedback,float penalizari ){
        setId(id);
        this.studentID=studentID;
        this.temaID=temaID;
        this.dataPredare=dataPredare;
        this.profesor=profesor;
        this.feedback=feedback;
        this.penalizari=penalizari;
    }

    @Override
    public Integer getId() {return super.getId();}

    public int getStudentID() {return studentID;}

    public int getTemaID() {return temaID; }

    public Date getDataPredare() {
        return dataPredare;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getProfesor() {
        return profesor;
    }


    public float getPenalizari() {
        return penalizari;
    }

    @Override
    public void setId(Integer integer) {
        super.setId(integer);
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setTemaID(int temaID) {
        this.temaID = temaID;
    }

    public void setDataPredare(Date dataPredare) {
        this.dataPredare = dataPredare;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setPenalizari(float penalizari) {
        this.penalizari = penalizari;
    }

    @Override
    public String toString() {
        return getId()+" "+studentID+" "+temaID+" "+dataPredare+" "+profesor+" "+feedback+" "+penalizari;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Nota){
            Nota not=(Nota)obj;
            return not.getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
