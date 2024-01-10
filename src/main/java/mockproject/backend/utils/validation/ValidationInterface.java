package mockproject.backend.utils.validation;

import mockproject.backend.domain.dto.ResponseData;
import org.springframework.validation.BindingResult;

public interface ValidationInterface {
    ResponseData error(BindingResult bindingResult);
}
