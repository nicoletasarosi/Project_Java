package domain.validate;

import domain.Nota;

public class ValidationNota implements Validator<Nota>{
    private String err;

    public ValidationNota(){}

    @Override
    public void validate(Nota entity) throws ValidationException{
        err = "";
        if(!err.equals(""))
            throw new ValidationException(err);
    }
}
