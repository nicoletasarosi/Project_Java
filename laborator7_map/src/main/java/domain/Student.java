package domain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Student extends Entity<Integer> {
    private String nume;
    private String prenume;
    private int grupa;
    private String email;
    private String cadruDidacticIndrumatorLab;

    public Student(Integer id,String nume, String prenume, int grupa,String email, String cadruDidacticIndrumatorLab){
        setId(id);
        this.nume=nume;
        this.prenume=prenume;
        this.grupa=grupa;
        this.email=email;
        this.cadruDidacticIndrumatorLab=cadruDidacticIndrumatorLab;
    }

    public Integer getId() {
        return super.getId();
    }

    public int getGrupa() {
        return grupa;
    }

    public String getCadruDidacticIndrumatorLab() {
        return cadruDidacticIndrumatorLab;
    }

    public String getNume() {
        return nume;
    }

    public String getEmail(){return email;}

    public String getPrenume() {
        return prenume;
    }

    public void setCadruDidacticIndrumatorLab(String cadruDidacticIndrumatorLab) {
        this.cadruDidacticIndrumatorLab=cadruDidacticIndrumatorLab;
    }

    public void setGrupa(int grupa) {
        this.grupa=grupa;
    }

    public void setId(Integer id) {
        super.setId(id);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    @Override
    public String toString() {
        return getId()+" "+nume+" "+prenume+" "+grupa+" "+email+" "+cadruDidacticIndrumatorLab;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student){
            Student stud=(Student)obj;
            return stud.getId().equals(getId());
        }
        return false;
    }

    //&& stud.getNume().equals(nume) && stud.getPrenume().equals(prenume)&&
    //                    stud.getGrupa()==grupa&& stud.getCadruDidacticIndrumatorLab().equals(cadruDidacticIndrumatorLab)

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
/*
1;Prundus;Vlad;226;Marcel
2;Pop;Daniel;226;Marcel
3;Pop;Radu;231;Costel
4;Pocas;Mihai;221;Dodel
5;Parje;Denisa;216;Costel
 */


/*
<?xml version="1.0" encoding="UTF-8"?>
<studenti>
    <student id="1">
        <nume>Prundus</nume>
        <prenume>Vlad</prenume>
        <grupa>226</grupa>
        <cadruDidacticIndrumatorLab>Marcel</cadruDidacticIndrumatorLab>
    </student>
    <student id="2">
        <nume>Pop</nume>
        <prenume>Daniel</prenume>
        <grupa>226</grupa>
        <cadruDidacticIndrumatorLab>Marcel</cadruDidacticIndrumatorLab>
    </student>
    <student id="3">
        <nume>Pop</nume>
        <prenume>Radu</prenume>
        <grupa>231</grupa>
        <cadruDidacticIndrumatorLab>Costel</cadruDidacticIndrumatorLab>
    </student>
    <student id="4">
        <nume>Pocas</nume>
        <prenume>Mihai</prenume>
        <grupa>221</grupa>
        <cadruDidacticIndrumatorLab>Dodel</cadruDidacticIndrumatorLab>
    </student>
    <student id="5">
        <nume>Parje</nume>
        <prenume>Denisa</prenume>
        <grupa>216</grupa>
        <cadruDidacticIndrumatorLab>Costel</cadruDidacticIndrumatorLab>
    </student>
</studenti>
 */
