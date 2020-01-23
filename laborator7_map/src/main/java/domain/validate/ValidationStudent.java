package domain.validate;

import domain.Student;
import domain.validate.ValidationException;
import domain.validate.Validator;

public class ValidationStudent implements Validator<Student> {
    private String err;

    public ValidationStudent(){ }

    @Override
    public void validate(Student entity) throws ValidationException {
        err="";
        if(!entity.getEmail().contains("@") && !entity.getEmail().contains(".com") && !entity.getEmail().contains(".ro"))
            err+="Emailul trebuie sa contina @ si .com sau .ro\n";
        if(!err.equals("")) {
            throw new ValidationException(err);
        }
    }
}