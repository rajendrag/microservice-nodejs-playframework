package util.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * Created by RP on 1/6/16.
 */
public class TenantIdResolver implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {

        return "uchealth";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
