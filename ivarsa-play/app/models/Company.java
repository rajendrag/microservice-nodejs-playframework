package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;



/**
 * Company entity managed by Ebean
 */
@Entity 
public class Company {

    private static final long serialVersionUID = 1L;

	@Id
    public Long id;
    
    @Constraints.Required
    public String name;
    
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        /*for(Company c: Company.find.orderBy("name").findList()) {
            options.put(c.id.toString(), c.name);
        }*/
        return options;
    }

}

