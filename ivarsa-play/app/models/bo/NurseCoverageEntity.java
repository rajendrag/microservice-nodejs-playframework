package models.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.util.Day;
import models.util.IQueueUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Time;
import java.util.Calendar;


/**
 * The persistent class for the iq_nurse_coverage database table.
 * 
 */
@Entity
@Table(name="iq_nurse_coverage")
@Audited
public class NurseCoverageEntity extends BaseDo implements Comparable<NurseCoverageEntity> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="iq_nurse_coverage_id")
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="day")
    @Enumerated(EnumType.STRING)
    private Day day;

	@Column(name="no_of_nurses")
	private int noOfNurses;

	@Column(name="shift_length")
	private Time shiftLength;

	@Column(name="start_time")
	private Time startTime;
	
	//bi-directional many-to-one association to IqUnitMstr
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="iq_unit_mstr_id")
	private UnitEntity iqUnitMstr;

	public NurseCoverageEntity() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String iqNurseCoverageId) {
		this.id = iqNurseCoverageId;
	}

	public Day getDay() {
		return this.day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public int getNoOfNurses() {
		return this.noOfNurses;
	}

	public void setNoOfNurses(int noOfNurses) {
		this.noOfNurses = noOfNurses;
	}

	public Time getShiftLength() {
		return this.shiftLength;
	}

	public void setShiftLength(Time shiftLength) {
		this.shiftLength = shiftLength;
	}

	public Time getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public UnitEntity getIqUnitMstr() {
		return this.iqUnitMstr;
	}

	public void setIqUnitMstr(UnitEntity iqUnitMstr) {
		this.iqUnitMstr = iqUnitMstr;
	}

	public Time getEndTime(){
	    return IQueueUtils.getNurseCoverageEndTime(startTime, shiftLength);
	}
	
    @Override
    public int compareTo(NurseCoverageEntity o) {
        if (this.getDay().equals(o.getDay())) {
            return this.startTime.compareTo(o.getStartTime());
        }
        return (this.getDay().getDayIndex() - o.getDay().getDayIndex());
    }
	
    @Override
    public BaseDo deepCopy() {
        NurseCoverageEntity newInstance = new NurseCoverageEntity();
        BeanUtils.copyProperties(this, newInstance, new String[]{"id","version","created_by","modified_by","created_datetime","modified_datetime","iqUnitMstr"});
        return newInstance;
    }
    
    public boolean isItWithInDayHoursOfOperation(UnitHrsOfOperationEntity hrsOfOperationEntity){
        boolean isValid=true;
        
        Calendar dayEndTime = Calendar.getInstance();
        dayEndTime.setTime(hrsOfOperationEntity.getUntilTime());
        dayEndTime.add(Calendar.HOUR_OF_DAY, 1);
        
        Calendar nurseShiftEndTime = Calendar.getInstance();
        nurseShiftEndTime.setTime(startTime);
        nurseShiftEndTime.add(Calendar.MINUTE, (shiftLength.getHours()*60)+shiftLength.getMinutes());
        
        Calendar hrsOfOperationFromTime = Calendar.getInstance();
        hrsOfOperationFromTime.setTime(hrsOfOperationEntity.getFromTime());
        hrsOfOperationFromTime.add(Calendar.HOUR_OF_DAY, -1);
       
        if(hrsOfOperationFromTime.getTime().compareTo(startTime)>0){
            isValid=false;
        }else if(dayEndTime.getTime().compareTo(nurseShiftEndTime.getTime())<0){
            isValid=false;
        }
        return isValid;
    }

}
