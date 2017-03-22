package com.github.tompower.spring.boot.flyway.migrate;

import com.github.tompower.spring.boot.flyway.migrate.properties.Properties;
import org.flywaydb.core.Flyway;

public class FlywayFactory {

    public static Flyway create(String[] locations, ClassLoader classLoader, Properties properties) {
        Flyway flyway = new Flyway();
        flyway.setLocations(locations);
        flyway.setClassLoader(classLoader);
        flyway.setDataSource(properties.getUrl(), properties.getUser(), properties.getPass());
        flyway.setBaselineOnMigrate(true);
        return flyway;
    }

}