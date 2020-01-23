package domain.validate;

import domain.Tema;

public class ValidationTema implements Validator<Tema> {
    private String err;

    public ValidationTema(){}

    @Override
    public void validate(Tema entity) throws ValidationException{
        err="";
        if(entity.getStartWeek()<1 || entity.getStartWeek()>14)
            err+="Termenul de inceput trebuie sa aiba valori intre 1 si 14\n";
        if(entity.getDeadlineWeek()<1 || entity.getDeadlineWeek()>14)
            err+="Termenul de final trebuie sa aiba valori intre 1 si 14\n";
        if(entity.getStartWeek()>=entity.getDeadlineWeek())
            err+="Termenul de inceput trebuie sa fie strict mai mic decat termenul de final";
        if(!err.equals(""))
            throw new ValidationException(err);
    }


}
