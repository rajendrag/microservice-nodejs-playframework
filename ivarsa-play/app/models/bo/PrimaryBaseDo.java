
package models.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.bo.validator.IQueueValidator;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Primary Base do interface for all Primary entities like Unit,Appointment,Forecast and Scheduling Template 
 * 
 * @author <a href="santhosh.g@leantaas.com">Santhosh Gandhe</a>
 * @version $Revision: 1.0 $, $Date: Apr 27, 2015
 */

@MappedSuperclass
public abstract class PrimaryBaseDo extends BaseDo {
  
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * Indicates the latest version of this primary base do, that should be shown on the UI.
     */
    @Column(name="show_on_ui")
    protected boolean showOnUI=true;
    
    @Column(name="parent_id")
    protected String parentId=null;
    
    /**
     * Version field that we are maintaining manually ( out of hibernate frame work )
     * for handling the dependency between the entities.
     * 
     * When a primary base do object is edited, while it already has its child entities created,
     * then we auto initialize the new version of primary base do and increment the version counter.
     */
    @Column(name="iq_version")
    protected int iqVersion=0;
    
    @Column(name="cloned_from")
    protected String clonedFrom=null;
    
    /**
     * Abstract method to define the behavior for primary base do objects.
     * Returns true, if there exists any child entities for this primary base do, 
     * otherwise this should return false.
     * 
     * Initializing the new version of primary base do depends on this method.
     * @return
     */
    public abstract boolean isThereAnyDependentExists();
    
    public abstract Object getId();

    @JsonIgnore
    public abstract IQueueValidator getValidator();
    
    public boolean isShowOnUI() {
        return showOnUI;
    }

    public void setShowOnUI(boolean showOnUI) {
        this.showOnUI = showOnUI;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    public int getIqVersion() {
        return iqVersion;
    }

    public void setIqVersion(int iqVersion) {
        this.iqVersion = iqVersion;
    }
    

    public String getClonedFrom() {
        return clonedFrom;
    }

    public void setClonedFrom(String clonedFrom) {
        this.clonedFrom = clonedFrom;
    }
}
