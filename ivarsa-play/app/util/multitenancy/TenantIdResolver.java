package util.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import play.mvc.Http;

import java.util.Map;

/**
 * Created by RP on 1/6/16.
 */
public class TenantIdResolver implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        Map<String, Object> args = Http.Context.current().args;
        System.out.println("Selected tenant"+(String)args.get("tenantId"));
        return (String)args.get("tenantId");
        //return "uchealth";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
