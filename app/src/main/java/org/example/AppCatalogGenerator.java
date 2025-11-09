package org.example;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import freemarker.template.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AppCatalogGenerator {

    public static void main(String[] args) throws Exception {
        // --- Load YAML with SafeConstructor (SnakeYAML 2.x fix) ---
        LoaderOptions loaderOptions = new LoaderOptions();
        Yaml yaml = new Yaml(new SafeConstructor(loaderOptions));
        Map<String, Object> data = yaml.load(Files.newInputStream(Paths.get("src/main/resources/data.yaml")));

        Map<String, Object> applications = (Map<String, Object>) data.get("applications");

        // --- Select stages to include ---
        List<String> stagesToInclude = Arrays.asList(
                "development", "test", "integration", "pre-prod", "production"
        );

        // --- Collect stage environments ---
        Map<String, List<String>> stageEnvs = new LinkedHashMap<>();
        for (Object value : applications.values()) {
            Map<String, Object> appData = (Map<String, Object>) value;
            Map<String, Object> stages = (Map<String, Object>) appData.get("stages");

            for (String stageName : stages.keySet()) {
                if (!stagesToInclude.contains(stageName)) continue;
                Map<String, Object> stageData = (Map<String, Object>) stages.get(stageName);
                List<String> envs = (List<String>) stageData.get("environments");

                stageEnvs.computeIfAbsent(stageName, k -> new ArrayList<>());
                for (String env : envs) {
                    if (!stageEnvs.get(stageName).contains(env)) {
                        stageEnvs.get(stageName).add(env);
                    }
                }
            }
        }

        // --- Prepare FreeMarker ---
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));

        Template template = cfg.getTemplate("view.ftl");

        Map<String, Object> model = new HashMap<>();
        model.put("applications", applications);
        model.put("stage_envs", stageEnvs);

        try (Writer out = new OutputStreamWriter(new FileOutputStream("Application-Catalog.html"), "UTF-8")) {
            template.process(model, out);
            System.out.println("✅ Generated Application-Catalog.html");
        }
    }
}
