package com.github.tompower.spring.boot.flyway.migrate.tasks;

import com.github.tompower.spring.boot.flyway.migrate.core.helper.FlywayMigrateLogger;
import com.github.tompower.spring.boot.flyway.migrate.core.helper.LoggerGradleImpl;
import com.github.tompower.spring.boot.flyway.migrate.plugin.abs.Plugin;
import com.github.tompower.spring.boot.flyway.migrate.plugin.helper.PluginExecutionException;
import com.github.tompower.spring.boot.flyway.migrate.plugin.helper.PluginFactory;
import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public abstract class SpringBootFlywayMigrateTask extends DefaultTask {

    private final Properties properties = System.getProperties();
    private FlywayMigrateLogger logger = new LoggerGradleImpl((Logger) getLogger());

    public abstract void action();

    protected void execute(Plugin plugin) {
        try {
            SourceSet main = getProject().getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().getByName("main");
            PluginFactory.create(plugin,
                    getResourcesDir(main),
                    getBuildDirs(main),
                    properties,
                    logger).execute();
        } catch (PluginExecutionException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private String getResourcesDir(SourceSet main) {
        return main.getResources().getSrcDirs().toArray()[0].toString();
    }

    private List<String> getBuildDirs(SourceSet main) {
        SourceSetOutput output = main.getOutput();
        return Arrays.asList(output.getClassesDir().getAbsolutePath(), output.getResourcesDir().getAbsolutePath());
    }
}
