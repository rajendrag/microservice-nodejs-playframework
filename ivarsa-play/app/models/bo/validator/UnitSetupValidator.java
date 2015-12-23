package models.bo.validator;

import models.bo.NurseCoverageEntity;
import models.bo.PrimaryBaseDo;
import models.bo.UnitEntity;
import models.bo.UnitHrsOfOperationEntity;
import models.util.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UnitSetupValidator extends BaseValidator {

    private UnitEntity unit;

    private enum LimitType {
        LOWER, UPPER
    }

    public UnitSetupValidator(PrimaryBaseDo primaryBaseDo) {
        super(primaryBaseDo);
        this.unit = (UnitEntity) primaryBaseDo;
    }

    @Override
    public List<BaseValidator.ValidationMsg> validate() {
        List<BaseValidator.ValidationMsg> errMsg = new ArrayList<ValidationMsg>();

        /**
         * Atleast there should one non zero entry in chairs or beds
         * 
         * @return
         */

        if ((unit.getNofBeds() <= 0 && unit.getNofChairs() <= 0)) {
            errMsg.add(BaseValidator.ValidationMsg.INVALID_CAPACITY);
        }

        /**
         * Atleast one hours of operation should be defined
         * 
         * @return
         */

        if (unit.getHrsOfOperation().size() == 0) {
            errMsg.add(ValidationMsg.INVALID_HRS_OF_OPS);
        }

        /**
         * <pre>
         * This covers point 1 under Nurse Coverage Section in IQ-192
         * </br>
         * For each day defined in 'Hours of Operation', 
         * there should be corresponding Nurse Coverage defined.
         * </pre>
         * 
         * @return
         */

        Set<UnitHrsOfOperationEntity> hrsOfOperations = unit.getHrsOfOperation();
        for (UnitHrsOfOperationEntity hrsOfOperation : hrsOfOperations) {
            if (!isNurseCoverageDefinedForDay(hrsOfOperation.getDay())) {
                errMsg.add(ValidationMsg.INVALID_NURSE_COUNT);
                break;
            }
        }

        return errMsg;
    }

    private boolean isNurseCoverageDefinedForDay(Day day) {
        Set<NurseCoverageEntity> nurseAllocations = unit.getNurseCoverage();
        for (NurseCoverageEntity nurseAllocation : nurseAllocations) {
            if (nurseAllocation.getDay() == day) {
                return true;
            }
        }
        return false;
    }

}
