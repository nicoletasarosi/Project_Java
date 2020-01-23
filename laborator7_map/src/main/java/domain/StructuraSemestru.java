package domain;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class StructuraSemestru extends Entity<String> {
    private Date[] sem;

    public StructuraSemestru(String id, Date date){
        setId(id);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        sem=new Date[30];
        for(int k=1;k<28;k+=2){
            cal.add(Calendar.DATE, 1);
            sem[k-1]=cal.getTime();
            cal.add(Calendar.DATE, 6);
            sem[k]=cal.getTime();
        }
    }

    public String getId() {
        return super.getId();
    }

    public Date[] getSem() {
        return sem;
    }

    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StructuraSemestru) {
            StructuraSemestru sem = (StructuraSemestru) obj;
            return sem.getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
