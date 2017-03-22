package com.github.tompower.spring.boot.flyway.migrate;

import com.github.tompower.spring.boot.flyway.migrate.messages.FlywayMigrateLogger;
import com.github.tompower.spring.boot.flyway.migrate.messages.Messages;
import com.github.tompower.spring.boot.flyway.migrate.properties.Properties;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.maven.artifact.DependencyResolutionRequiredException;

public class PluginValidate {

    private final String resourceBaseDir;
    private final List<String> paths;
    private final String profile;
    private final FlywayMigrateLogger logger;

    public PluginValidate(String resourceBaseDir, List<String> paths, String profile, FlywayMigrateLogger logger) {
        this.resourceBaseDir = resourceBaseDir;
        this.paths = paths;
        this.profile = profile;
        this.logger = logger;
    }

    private Resources resources;
    private Migration migration;
    private Properties properties;

    public void validate() {
        try {
            init();
            new Validator(new String[]{migration.getDirectory()}, resources.getClassloader(), properties).flywayValidate();
            logger.info(Messages.MIGRATION_SUCCESSFUL);
        } catch (DependencyResolutionRequiredException | IOException | URISyntaxException ex) {
            logger.error(ex.getMessage());
        }
    }

    private void init() throws DependencyResolutionRequiredException, IOException, URISyntaxException {
        resources = Resources.getInstance(paths);
        properties = resources.getProperties(profile);
        migration = new Migration(properties, resourceBaseDir);
    }

}