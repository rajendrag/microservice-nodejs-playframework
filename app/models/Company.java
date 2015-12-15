package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;
import play.data.validation.*;



/**
 * Company entity managed by Ebean
 */
@Entity 
public class Company extends Model {

    private static final long serialVersionUID = 1L;

	@Id
    public Long id;
    
    @Constraints.Required
    public String name;
    
    /**
     * Generic query helper for entity Company with id Long
     */
    public static Finder<Long,Company> find = new Finder<Long,Company>(Company.class);

    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Company c: Company.find.orderBy("name").findList()) {
            options.put(c.id.toString(), c.name);
        }
        return options;
    }

}

