package domain;

import java.util.Objects;

public class Tema extends Entity<Integer> {
    private String descriere;
    private Integer startWeek;
    private Integer deadlineWeek;

    public Tema(Integer id, String descriere, Integer startWeek, Integer deadlineWeek){
        setId(id);
        this.descriere=descriere;
        this.startWeek=startWeek;
        this.deadlineWeek=deadlineWeek;
    }

    public Integer getId() {
        return super.getId();
    }

    public String getDescriere() {
        return descriere;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public Integer getDeadlineWeek() {
        return deadlineWeek;
    }

    public void setId(Integer id) {
        super.setId(id);
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public void setDeadlineWeek(Integer deadlineWeek) {
        this.deadlineWeek = deadlineWeek;
    }

    @Override
    public String toString() {
        return getId()+" "+descriere+" "+startWeek+" "+deadlineWeek;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tema){
            Tema tem=(Tema) obj;
            return tem.getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
/*
1;Lab1;1;2
2;Lab2;2;4
3;Lab3;4;6
4;Lab4;6;7
5;Lab5;7;9
6;Lab6;9;11
7;Lab7;11;13
8;Lab8;13;14
 */

/*
<?xml version="1.0" encoding="UTF-8"?>
<teme>
    <tema id="1">
        <descriere>Lab1</descriere>
        <startWeek>1</startWeek>
        <deadlineWeek>2</deadlineWeek>
    </tema>
    <tema id="2">
        <descriere>Lab2</descriere>
        <startWeek>2</startWeek>
        <deadlineWeek>4</deadlineWeek>
    </tema>
    <tema id="3">
        <descriere>Lab3</descriere>
        <startWeek>4</startWeek>
        <deadlineWeek>6</deadlineWeek>
    </tema>
    <tema id="4">
        <descriere>Lab4</descriere>
        <startWeek>6</startWeek>
        <deadlineWeek>7</deadlineWeek>
    </tema>
    <tema id="5">
        <descriere>Lab5</descriere>
        <startWeek>7</startWeek>
        <deadlineWeek>9</deadlineWeek>
    </tema>
    <tema id="6">
        <descriere>Lab6</descriere>
        <startWeek>9</startWeek>
        <deadlineWeek>11</deadlineWeek>
    </tema>
    <tema id="7">
        <descriere>Lab7</descriere>
        <startWeek>11</startWeek>
        <deadlineWeek>13</deadlineWeek>
    </tema>
    <tema id="8">
        <descriere>Lab8</descriere>
        <startWeek>13</startWeek>
        <deadlineWeek>14</deadlineWeek>
    </tema>
</teme>
 */