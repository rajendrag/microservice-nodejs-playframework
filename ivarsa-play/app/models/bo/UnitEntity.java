
package models.bo;

import com.google.common.collect.Sets;
import models.bo.validator.IQueueValidator;
import models.bo.validator.UnitSetupValidator;
import models.util.Day;
import models.util.IQueueUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Data object for Unit entity record
 *
 * @author <a href="santhosh.g@leantaas.com">Santhosh Gandhe</a>
 * @version $Revision: 1.0 $, $Date: Apr 27, 2015
 */

@Entity
@Table(name = "iq_unit_mstr")
@Audited
public class UnitEntity extends PrimaryBaseDo  {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "iq_unit_mstr_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    
    @Column(name = "unit_name")
    private String unitName;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "nof_chairs")
    private Integer nofChairs;
    
    @Column(name = "nof_beds")
    private Integer nofBeds;
    
    @Column(name = "appt_start_time")
    private String apptStartTime;
    
    @Column(name = "nof_appt_start_with_in_hour")
    private Integer nofApptStartWithInHour;
    
    @Column(name="nof_appt_discharge_with_in_hour")
    private Integer nofApptDischargeWithInHour;
    
    @Column(name="owner")
    private String owner;
    
    @OneToMany(mappedBy="iqUnitMstr", cascade= CascadeType.ALL, fetch= FetchType.EAGER, orphanRemoval=true, targetEntity=UnitHrsOfOperationEntity.class)
    private Set<UnitHrsOfOperationEntity> hrsOfOperation = Sets.newHashSet();
    
    @OneToMany(mappedBy="iqUnitMstr", cascade= CascadeType.ALL, fetch= FetchType.EAGER, orphanRemoval=true, targetEntity=HolidaysPerUnitEntity.class)
    private Set<HolidaysPerUnitEntity> holidays = Sets.newHashSet();
    
    @OneToMany(mappedBy="iqUnitMstr", cascade= CascadeType.ALL, fetch= FetchType.EAGER, orphanRemoval=true, targetEntity=NurseCoverageEntity.class)
    private Set<NurseCoverageEntity> nurseCoverage = Sets.newHashSet();

    public UnitEntity(){ 
        nofApptDischargeWithInHour=0;
        nofApptStartWithInHour=0;
        nofBeds=0;
        nofChairs=0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNofChairs() {
        return nofChairs;
    }

    public void setNofChairs(Integer nofChairs) {
        this.nofChairs = nofChairs;
    }

    public Integer getNofBeds() {
        return nofBeds;
    }

    public void setNofBeds(Integer nofBeds) {
        this.nofBeds = nofBeds;
    }

    public String getApptStartTime() {
        return apptStartTime;
    }

    public void setApptStartTime(String apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    public Integer getNofApptStartWithInHour() {
        return nofApptStartWithInHour;
    }

    public void setNofApptStartWithInHour(Integer nofApptStartWithInHour) {
        this.nofApptStartWithInHour = nofApptStartWithInHour;
    }

    public Integer getNofApptDischargeWithInHour() {
        return nofApptDischargeWithInHour;
    }

    public void setNofApptDischargeWithInHour(Integer nofApptDischargeWithInHour) {
        this.nofApptDischargeWithInHour = nofApptDischargeWithInHour;
    }

  
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public Set<UnitHrsOfOperationEntity> getHrsOfOperation() {
        return hrsOfOperation;
    }

    public void setHrsOfOperation(Set<UnitHrsOfOperationEntity> hrsOfOperation) {
        this.hrsOfOperation = hrsOfOperation;
    }

    public Set<HolidaysPerUnitEntity> getHolidays() {
        return holidays;
    }

    public void setHolidays(Set<HolidaysPerUnitEntity> holidays) {
        this.holidays = holidays;
    }

    public Set<NurseCoverageEntity> getNurseCoverage() {
        return nurseCoverage;
    }

    public void setNurseCoverage(Set<NurseCoverageEntity> nurseCoverage) {
        this.nurseCoverage = nurseCoverage;
    }

    public Integer getCapacity(){
        return (nofChairs == null || nofBeds == null) ? null : (nofChairs + nofBeds); 
    }
    
    public UnitHrsOfOperationEntity getGivenDayHrsOfOperation(Day dayOfTheWeek){
        for (UnitHrsOfOperationEntity hrsOfOperation : this.hrsOfOperation) {
            if (dayOfTheWeek.equals(hrsOfOperation.getDay())) {
                return hrsOfOperation;
            }
        }
        return null;
    }
   /**
     * Utility method - helpful in constructing the scheduling script input object.
     * Method returns the units per day Hours of operation
     * @return
     */
    public Map<String,UnitHrsOfOperationEntity> getDayHoursOfOperationPerDay(){
        Set<UnitHrsOfOperationEntity> unitHrsOfOpsEntities = getHrsOfOperation();
        Map<String,UnitHrsOfOperationEntity> unitHrsOfOpsEntitiesMap = new HashMap<String,UnitHrsOfOperationEntity>();
        for(UnitHrsOfOperationEntity unitHrsOfOperationEntity : unitHrsOfOpsEntities){
            unitHrsOfOpsEntitiesMap.put(unitHrsOfOperationEntity.getDay().toString(), unitHrsOfOperationEntity);
        }
        return unitHrsOfOpsEntitiesMap;
    }
    
    /**
     * Utility method - helpful in constructing the scheduling script input object.
     * Method returns the units per day Nurse Coverage
     * @return
     */
    public Map<String, List<List<String>>> getHourlyNurseCoveragePerDay() {
        Set<NurseCoverageEntity> nurseCoverageEntities = getNurseCoverage();
        Map<String, List<List<String>>> nurseCoveragePerDay = new HashMap<String, List<List<String>>>();
        List<List<String>> nurseCoverage;
        List<NurseCoverageEntity> sortedNurseCoverageList = new ArrayList<NurseCoverageEntity>();
        sortedNurseCoverageList.addAll(nurseCoverageEntities);
        Collections.sort(sortedNurseCoverageList);
        for (NurseCoverageEntity nurseCoverageEntity : sortedNurseCoverageList) {
            String dayStr = nurseCoverageEntity.getDay().toString();
            if (nurseCoveragePerDay.get(dayStr) == null) {
                nurseCoverage = new LinkedList<List<String>>();
                nurseCoveragePerDay.put(dayStr, nurseCoverage);
            } else {
                nurseCoverage = nurseCoveragePerDay.get(dayStr);
            }

            List<String> coverage = new LinkedList<String>();
            coverage.add(IQueueUtils.formatTimeToFloat(nurseCoverageEntity.getStartTime()));
            coverage.add(Integer.toString(nurseCoverageEntity.getNoOfNurses()));
            coverage.add(IQueueUtils.formatTimeToFloat(nurseCoverageEntity.getShiftLength()));
            nurseCoverage.add(coverage);

        }
        return nurseCoveragePerDay;
    }

    public Map<String, List<NurseCoverageEntity>> getHourlyNurseCoverageEntityPerDay() {
        Set<NurseCoverageEntity> nurseCoverageEntities = getNurseCoverage();
        Map<String, List<NurseCoverageEntity>> nurseCoveragePerDay = new HashMap<String, List<NurseCoverageEntity>>();
        List<NurseCoverageEntity> nurseCoverage;
        List<NurseCoverageEntity> sortedNurseCoverageList = new ArrayList<NurseCoverageEntity>();
        sortedNurseCoverageList.addAll(nurseCoverageEntities);
        Collections.sort(sortedNurseCoverageList);
        for (NurseCoverageEntity nurseCoverageEntity : sortedNurseCoverageList) {
            String dayStr = nurseCoverageEntity.getDay().toString();
            if (nurseCoveragePerDay.get(dayStr) == null) {
                nurseCoverage = new LinkedList<NurseCoverageEntity>();
                nurseCoveragePerDay.put(dayStr, nurseCoverage);
            } else {
                nurseCoverage = nurseCoveragePerDay.get(dayStr);
            }
            nurseCoverage.add(nurseCoverageEntity);
        }
        return nurseCoveragePerDay;
    }

    @Override
    public boolean isThereAnyDependentExists() {
        return false;
    }

    @Override
    public BaseDo deepCopy() {
        UnitEntity newInstance = new UnitEntity();
        BeanUtils.copyProperties(this, newInstance, new String[]{"id","version","created_by","modified_by","created_datetime","modified_datetime","hrsOfOperation","holidays","nurseCoverage","apptMaster","forecastMaster","schedulingTemplateMaster"});
        
        Set<UnitHrsOfOperationEntity> hrsOfOps = newInstance.getHrsOfOperation();
        for(UnitHrsOfOperationEntity hrsOfOp:this.getHrsOfOperation()){
            UnitHrsOfOperationEntity tmpHrsOfOp= (UnitHrsOfOperationEntity)hrsOfOp.deepCopy();
            tmpHrsOfOp.setIqUnitMstr(newInstance);
            hrsOfOps.add(tmpHrsOfOp);
        }
        
        Set<NurseCoverageEntity> nurseCoverages = newInstance.getNurseCoverage();
        for(NurseCoverageEntity nurseCoverage:this.getNurseCoverage()){
            NurseCoverageEntity tmpNurseCoverage = (NurseCoverageEntity)nurseCoverage.deepCopy();
            tmpNurseCoverage.setIqUnitMstr(newInstance);
            nurseCoverages.add(tmpNurseCoverage);
        }
        
        Set<HolidaysPerUnitEntity> holidays = newInstance.getHolidays();
        for(HolidaysPerUnitEntity holiday:this.getHolidays()){
            HolidaysPerUnitEntity tmpHoliday=(HolidaysPerUnitEntity)holiday.deepCopy();
            tmpHoliday.setIqUnitMstr(newInstance);
            holidays.add(tmpHoliday);
        }
        return newInstance;
    }

    @Override
    public IQueueValidator getValidator() {
        return new UnitSetupValidator(this);
    }
    
    
}
