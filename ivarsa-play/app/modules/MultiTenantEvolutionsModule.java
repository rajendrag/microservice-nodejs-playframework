package modules;

import com.google.inject.AbstractModule;
import play.Configuration;
import play.Environment;
import util.multitenancy.MultiTenantEvolutionsExecutor;

/**
 * Created by RP on 1/8/16.
 */
public class MultiTenantEvolutionsModule extends AbstractModule {

    private final Environment environment;
    private final Configuration configuration;

    public MultiTenantEvolutionsModule(Environment environment, Configuration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(MultiTenantEvolutionsExecutor.class).asEagerSingleton();
    }
}
