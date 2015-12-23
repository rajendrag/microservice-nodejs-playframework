package models.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import models.util.Day;
import models.util.IQueueUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the iq_unit_hrs_of_operation database table.
 * 
 */
@Entity
@Table(name="iq_unit_hrs_of_operation")
@Audited
public class UnitHrsOfOperationEntity extends BaseDo implements Comparable<UnitHrsOfOperationEntity> {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
	private static final long serialVersionUID = 1L;

	public static final Integer ONE_MINUTE_IN_MILLIS=60000;//millisecs

	@Id
	@Column(name="iq_unit_hrs_of_operation_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="day")
    @Enumerated(EnumType.STRING)
    private Day day;

	@Column(name="from_time")
	private Time fromTime;

	@Column(name="until_time")
	private Time untilTime;
	
	@Column(name="last_appt_time")
	private Time lastApptTime;


	//bi-directional many-to-one association to IqUnitMstr
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="iq_unit_mstr_id")
	private UnitEntity iqUnitMstr;

	public UnitHrsOfOperationEntity() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String iqUnitHrsOfOperationId) {
		this.id = iqUnitHrsOfOperationId;
	}

	public Day getDay() {
		return this.day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public Time getFromTime() {
		return this.fromTime;
	}

	public void setFromTime(Time fromTime) {
		this.fromTime = fromTime;
	}

	public Time getLastApptTime() {
		return this.lastApptTime;
	}

	public void setLastApptTime(Time lastApptTime) {
		this.lastApptTime = lastApptTime;
	}

	public Time getUntilTime() {
		return this.untilTime;
	}

	public void setUntilTime(Time untilTime) {
		this.untilTime = untilTime;
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
	
	/**
	 * Method to return a list of time interval, broken down by given slotInterval, for this day hours of operations
	 * @param slotInterval
	 * @return
	 */
	public List<String> getTimeSlots(int slotInterval){
        List<String> timeSlots = Lists.newLinkedList();
        try{
            Integer interval = slotInterval*ONE_MINUTE_IN_MILLIS;
            DateTime jodaTime1 = new DateTime(fromTime.getTime());
            Date d1 = jodaTime1.toDate();
            
            DateTime jodaTime2 = new DateTime(untilTime.getTime());
            Date d2 = jodaTime2.toDate();
            
            while(d1.getTime() <= d2.getTime()){
                timeSlots.add(IQueueUtils.convertDatetoString(d1, "HH:mm"));
                d1 = new Date(d1.getTime() + interval);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return timeSlots;
    }

	@Override
    public BaseDo deepCopy() {
	    UnitHrsOfOperationEntity newInstance = new UnitHrsOfOperationEntity();
        BeanUtils.copyProperties(this, newInstance, new String[]{"id","version","created_by","modified_by","created_datetime","modified_datetime","iqUnitMstr"});
        return newInstance;
    }

    @Override
    public int compareTo(UnitHrsOfOperationEntity obj) {
        return (this.getDay().getDayIndex() - obj.getDay().getDayIndex());
    }
	
}
