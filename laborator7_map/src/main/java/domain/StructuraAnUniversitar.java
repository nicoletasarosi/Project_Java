package domain;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class StructuraAnUniversitar extends Entity<String>{
    private Date anulUniversitar;
    private StructuraSemestru sem1;
    private StructuraSemestru sem2;

    public StructuraAnUniversitar(String id, String s1, String s2, Date anulUniversitar){
        setId(id);
        this.anulUniversitar=anulUniversitar;
        sem1=new StructuraSemestru(s1,anulUniversitar);
        Date ndate=sem1.getSem()[27];
        Calendar cal = Calendar.getInstance();
        cal.setTime(ndate);
        //cal.add(Calendar.DATE, 22);
        sem2=new StructuraSemestru(s2,cal.getTime());
    }
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String s) {
        super.setId(s);
    }

    public Date getAnulUniversitar() {
        return anulUniversitar;
    }

    public StructuraSemestru getSem1() {
        return sem1;
    }

    public StructuraSemestru getSem2() {
        return sem2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StructuraAnUniversitar) {
            StructuraAnUniversitar an = (StructuraAnUniversitar) obj;
            return an.getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
