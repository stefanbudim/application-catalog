package org.example;

import org.yaml.snakeyaml.Yaml;
import freemarker.template.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class AppCatalogGenerator {

    public static void main(String[] args) throws Exception {

        if (args.length > 0 && (args[0].equals("-h") || args[0].equals("--help"))) {
            printUsage();
            return;
        }

        // --- Parse command-line arguments ---
        List<String> stagesToInclude = parseStages(args);
        String outputFile = parseOutput(args);
        String dataPath = parseData(args);
        String viewPath = parseView(args);

        System.out.println(" Stages to include: " + String.join(", ", stagesToInclude));
        System.out.println(" Output file: " + outputFile);
        System.out.println(" Data YAML: " + dataPath);
        System.out.println(" View template: " + viewPath);

        // --- Load YAML ---
        Path yamlPath = Paths.get(dataPath);
        if (!Files.exists(yamlPath)) {
            throw new FileNotFoundException("data.yaml not found: " + yamlPath.toAbsolutePath());
        }

        Yaml yaml = new Yaml();  // SnakeYAML 2.x
        Map<String, Object> data;
        try (InputStream in = Files.newInputStream(yamlPath)) {
            data = yaml.load(in);
        }

        Map<String, Object> applications = (Map<String, Object>) data.get("applications");

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

        // --- Setup FreeMarker ---
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDefaultEncoding("UTF-8");

        Template template;
        Path viewFilePath = Paths.get(viewPath);
        if (Files.exists(viewFilePath)) {
            cfg.setDirectoryForTemplateLoading(viewFilePath.getParent().toFile());
            template = cfg.getTemplate(viewFilePath.getFileName().toString());
        } else {
            cfg.setClassLoaderForTemplateLoading(AppCatalogGenerator.class.getClassLoader(), "/");
            template = cfg.getTemplate(viewPath);  // default: view.ftl inside JAR
        }

        Map<String, Object> model = new HashMap<>();
        model.put("applications", applications);
        model.put("stage_envs", stageEnvs);

        // --- Render HTML ---
        try (Writer out = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8")) {
            template.process(model, out);
            System.out.println(" Generated " + outputFile);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar application-catalog.jar [options]");
        System.out.println("Options:");
        System.out.println("  --stages <stage1,stage2,...>   Comma-separated stages to include (default: production)");
        System.out.println("  --output <filename>            Output HTML file (default: Application-Catalog.html)");
        System.out.println("  --data <path/to/data.yaml>     Path to data.yaml (default: data.yaml in current directory)");
        System.out.println("  --view <path/to/view.ftl>      FreeMarker template (default: view.ftl inside JAR)");
        System.out.println("  -h, --help                     Show this help message");
    }

    private static List<String> parseStages(String[] args) {
        List<String> defaultStages = Collections.singletonList("production");
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--stages") && i + 1 < args.length) {
                return Arrays.stream(args[i + 1].split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
            }
        }
        return defaultStages;
    }

    private static String parseOutput(String[] args) {
        String defaultOutput = "Application-Catalog.html";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--output") && i + 1 < args.length) {
                return args[i + 1];
            }
        }
        return defaultOutput;
    }

    private static String parseData(String[] args) {
        String defaultData = "data.yaml";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--data") && i + 1 < args.length) {
                return args[i + 1];
            }
        }
        return defaultData;
    }

    private static String parseView(String[] args) {
        String defaultView = "view.ftl";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--view") && i + 1 < args.length) {
                return args[i + 1];
            }
        }
        return defaultView;
    }
}
