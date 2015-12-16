package models.bo.validator;

import java.util.List;


/**
 * All validators should implement this interface,
 * to enforce implementing validate method.
 */
public interface IQueueValidator {
    
    public List<BaseValidator.ValidationMsg> validate();
    
}
