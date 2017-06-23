package com.github.tompower.spring.boot.flyway.migrate.plugin.helper;

import com.github.tompower.spring.boot.flyway.migrate.core.helper.FlywayMigrateLogger;
import com.github.tompower.spring.boot.flyway.migrate.plugin.abs.Plugin;

import java.util.List;
import java.util.Properties;

public class PluginFactory {

    public static Plugin create(Plugin plugin, String resourcesDir, List<String> buildDirs, Properties properties, FlywayMigrateLogger logger) throws PluginExecutionException {
        com.github.tompower.spring.boot.flyway.migrate.core.properties.Properties properties1 = new com.github.tompower.spring.boot.flyway.migrate.core.properties.Properties();
        properties1.putAll(properties);
        plugin.setup(resourcesDir, buildDirs, properties1, logger);
        return plugin;
    }
}
