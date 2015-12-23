package models.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the iq_holidays_per_unit database table.
 * 
 */
@Entity
@Table(name="iq_holidays_per_unit")
@Audited
public class HolidaysPerUnitEntity extends BaseDo {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="iq_holidays_per_unit_id")
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="date")
	private Date holidayDate;
	
    @Column(name="day")
	private String day;

	@Column(name="from_time")
	private Time fromTime;

	@Column(name="last_appt_time")
	private Time lastApptTime;

	@Column(name="name_of_the_holiday")
	private String nameOfTheHoliday;

	@Column(name="to_time")
	private Time toTime;
	
   //bi-directional many-to-one association to IqUnitMstr
   	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="iq_unit_mstr_id")
	private UnitEntity iqUnitMstr;

	public HolidaysPerUnitEntity() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String iqHolidaysPerUnitId) {
		this.id = iqHolidaysPerUnitId;
	}

	public String getDay() {
		return this.day;
	}

	public void setDay(String day) {
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

	public String getNameOfTheHoliday() {
		return this.nameOfTheHoliday;
	}

	public void setNameOfTheHoliday(String nameOfTheHoliday) {
		this.nameOfTheHoliday = nameOfTheHoliday;
	}

	public Time getToTime() {
		return this.toTime;
	}

	public void setToTime(Time toTime) {
		this.toTime = toTime;
	}

	public UnitEntity getIqUnitMstr() {
		return this.iqUnitMstr;
	}

	public void setIqUnitMstr(UnitEntity iqUnitMstr) {
		this.iqUnitMstr = iqUnitMstr;
	}

    
    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    @Override
    public BaseDo deepCopy() {
        HolidaysPerUnitEntity newInstance = new HolidaysPerUnitEntity();
        BeanUtils.copyProperties(this, newInstance, new String[]{"id","version","created_by","modified_by","created_datetime","modified_datetime","iqUnitMstr"});
        return newInstance;
    }
    
    
}