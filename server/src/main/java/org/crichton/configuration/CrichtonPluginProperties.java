package org.crichton.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crichton.util.FileUtils;
import org.crichton.util.constants.FileName;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

@ConfigurationProperties(prefix = "crichton.plugin")
@Slf4j
@Getter
@RequiredArgsConstructor
public class CrichtonPluginProperties {

    private static final String DEFAULT_PLUGIN_PATH = System.getProperty("user.home") + File.separator + ".crichton" + File.separator + "plugin";

    private static final String DEFAULT_INJECTOR_PLUGIN_PATH = DEFAULT_PLUGIN_PATH + File.separator + "injector";

    private static final String DEFAULT_UNIT_TESTER_PLUGIN_PATH = DEFAULT_PLUGIN_PATH + File.separator + "coyote";

    private final String injectorPath;

    private final String unitTesterPath;

    public String getInjectorPath() {
        var path = Optional.ofNullable(injectorPath).orElse(DEFAULT_INJECTOR_PLUGIN_PATH);
        return FileUtils.getAbsolutePath(path);
    }

    public String getUnitTesterPath() {
        var path = Optional.ofNullable(unitTesterPath).orElse(DEFAULT_UNIT_TESTER_PLUGIN_PATH);
        return FileUtils.getAbsolutePath(path);
    }

    @PostConstruct
    public void validate() {
        log.info("Validating Crichton injector jar path: {}", Paths.get(getInjectorPath(), FileName.INJECTOR_PLUGIN));
        FileUtils.assertFileExists(getInjectorPath(), FileName.INJECTOR_PLUGIN);

        log.info("Validating Crichton injector plugin.properties path: {}", Paths.get(getInjectorPath(), FileName.PLUGIN_PROPERTY_FILE));
        FileUtils.assertFileExists(getInjectorPath(), FileName.PLUGIN_PROPERTY_FILE);

        log.info("Validating Crichton unit-tester plugin.properties path: {}", Paths.get(getInjectorPath(), FileName.UNIT_TESTER_PLUGIN));
        FileUtils.assertFileExists(getUnitTesterPath(), FileName.UNIT_TESTER_PLUGIN);

        log.info("Validating Crichton unit-tester plugin.properties path: {}", Paths.get(getInjectorPath(), FileName.PLUGIN_PROPERTY_FILE));
        FileUtils.assertFileExists(getUnitTesterPath(), FileName.PLUGIN_PROPERTY_FILE);
    }

}
