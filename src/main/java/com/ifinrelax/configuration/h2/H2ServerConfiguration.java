package com.ifinrelax.configuration.h2;

import org.flywaydb.core.Flyway;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

/**
 * @author Timur Berezhnoi.
 */
@Configuration
@Profile("dev")
public class H2ServerConfiguration {

    @Value("${db.port}")
    private String h2TcpPort;

    /**
     * TCP connection to connect with SQL clients to the embedded h2 database.
     *
     * @see Server
     * @throws SQLException if something went wrong during startup the server.
     * @return h2 db Server
     */
    @Bean
    public Server server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
    }

    /**
     * @return FlywayMigrationStrategy the strategy for migration.
     */
    @Bean
    @DependsOn("server")
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return Flyway::migrate;
    }
}
