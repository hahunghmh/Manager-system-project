package mockproject.backend.utils.validation;

import mockproject.backend.domain.dto.ResponseData;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class Validations implements ValidationInterface {
    @Override
    public ResponseData error(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return new ResponseData(-1, "Biding Error", errors);
    }
}
