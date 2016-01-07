package util.multitenancy;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import play.Configuration;
import play.Logger;
import play.Play;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by RP on 1/6/16.
 */
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {
    private static final long serialVersionUID = 8074002161278796379L;


    private static Logger.ALogger log = Logger.of(MultiTenantConnectionProviderImpl.class );

    private Map<String, ComboPooledDataSource> dataSourceMap = new ConcurrentHashMap<>();

    /**
     *
     * Constructor. Initializes the ComboPooledDataSource based on the config.properties.
     *
     * @throws PropertyVetoException
     */
    public MultiTenantConnectionProviderImpl() {
        Configuration configuration = Play.application().configuration();
        ComboPooledDataSource cpds = getComboPooledDataSource(configuration);
        cpds.setJdbcUrl(configuration.getString("jdbc.url"));
        dataSourceMap.put("default", cpds);
        log.info("Connection Pool initialised!");
    }

    private ComboPooledDataSource getComboPooledDataSource(Configuration configuration) {
        ComboPooledDataSource cpds = new ComboPooledDataSource("Example");
        try {
            cpds.setDriverClass(configuration.getString("jdbc.driver"));
            cpds.setUser(configuration.getString("jdbc.username"));
            cpds.setPassword(configuration.getString("jdbc.password"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return cpds;
    }


    @Override
    public Connection getAnyConnection() throws SQLException {
        ComboPooledDataSource cpds = dataSourceMap.get("default");
        log.debug("Get Default Connection:::Number of connections (max: busy - idle): {} : {} - {}",new int[]{cpds.getMaxPoolSize(),cpds.getNumBusyConnectionsAllUsers(),cpds.getNumIdleConnectionsAllUsers()});
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize()){
            log.warn("Maximum number of connections opened");
        }
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize() && cpds.getNumIdleConnectionsAllUsers()==0){
            log.error("Connection pool empty!");
        }
        return cpds.getConnection();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        ComboPooledDataSource cpds = dataSourceMap.get(tenantIdentifier);
        if(cpds == null) {
            Configuration conf = Play.application().configuration();
            cpds = getComboPooledDataSource(conf);
            String url = conf.getString("jdbc.url");
            String jdbcUrl = url.substring(0, url.lastIndexOf("/")+1) + conf.getString("jdbc.commondb")+"_"+tenantIdentifier;
            cpds.setJdbcUrl(jdbcUrl);
            dataSourceMap.put("default", cpds);
        }
        log.debug("Get {} Connection:::Number of connections (max: busy - idle): {} : {} - {}",new Object[]{tenantIdentifier, cpds.getMaxPoolSize(),cpds.getNumBusyConnectionsAllUsers(),cpds.getNumIdleConnectionsAllUsers()});
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize()){
            log.warn("Maximum number of connections opened");
        }
        if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize() && cpds.getNumIdleConnectionsAllUsers()==0){
            log.error("Connection pool empty!");
        }
        return cpds.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection){
        try {
            this.releaseAnyConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return ConnectionProvider.class.equals( unwrapType ) || MultiTenantConnectionProvider.class.equals( unwrapType ) || MultiTenantConnectionProviderImpl.class.isAssignableFrom( unwrapType );
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if ( isUnwrappableAs( unwrapType ) ) {
            return (T) this;
        }
        else {
            throw new UnknownUnwrapTypeException( unwrapType );
        }
    }
}
