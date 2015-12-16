package models.bo.validator;

import models.bo.PrimaryBaseDo;

import java.util.List;


public abstract class BaseValidator implements IQueueValidator {

    protected PrimaryBaseDo primaryBaseDo;
    
    public enum ValidationMsg{
        INVALID_CAPACITY("INVALID_CAPACITY"), 
        INVALID_HRS_OF_OPS("INVALID_HRS_OF_OPS"), 
        INVALID_NURSE_COUNT("INVALID_NURSE_COUNT"), 
        INVALID_NURSE_COVERAGE("INVALID_NURSE_COVERAGE"),
        HRS_OF_OPS_UNDEFINED_FOR_NURSE_COVERAGE("HRS_OF_OPS_UNDEFINED_FOR_NURSE_COVERAGE"),
        INVALID_DATA_SETUP_EMPTY_FILE("INVALID_DATA_SETUP_EMPTY_FILE"),
        INVALID_APPT_DURATION_COUNT("INVALID_APPT_DURATION_COUNT"),
        INVALID_APPT_DURATIONS("INVALID_APPT_DURATIONS");
        
        
        String msgKey;
        
        ValidationMsg(String msgKey){
            this.msgKey = msgKey;
        }
        
        public String getValidationMsgKey(){
            return this.msgKey;
        }
        
        public String toString(){
            return msgKey;
        }
    }

    public BaseValidator(PrimaryBaseDo primaryBaseDo){
        this.primaryBaseDo=primaryBaseDo;
    }
    
    public abstract List<ValidationMsg> validate();
}
