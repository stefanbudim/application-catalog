# Application Catalog

A simple, interactive catalog of all your applications, showing:

- Application names
- Deployment stages (development, test, integration, production)
- Environment names (DEV01, DEV02, INT01, etc.)
- URLs for each stage and environment
- Optional status or health check indicators

![Application Catalog Example](img/screenshot.jpg)

The project generates a table with clickable icons for each URL, making it easy for teams to:

- Quickly find application endpoints
- Track deployment stages
- Access information and logs efficiently

## Features

- One row per application
- Stage and environment headers
- Clickable icons
- Fully configurable via JSON or YAML
- Select which stages to include (in `generate.py`)
- JSON version on branch json

## Getting Started - Development

1. Clone this repository
2. Edit your `./app/src/main/resources/data.yaml` to add applications and stages
3. Run build `./gradlew run`
4. Run Programm `./gradlew generateCatalog`
5. Open the HTML file in your browser

## Usage
```
Usage: java -jar application-catalog.jar [options]
Options:
  --stages <stage1,stage2,...>   Comma-separated stages to include (default: production)
  --output <filename>            Output HTML file (default: Application-Catalog.html)
  --data <path/to/data.yaml>     Path to data.yaml (default: data.yaml in current directory)
  --view <path/to/view.ftl>      FreeMarker template (default: view.ftl inside JAR)
  -h, --help                     Show this help message
```

## License

[MIT License](LICENSE)
