#!/usr/bin/env python3
import yaml
from jinja2 import Environment, FileSystemLoader
from pathlib import Path

# Load YAML
with open("data.yaml") as f:
    data = yaml.safe_load(f)

# --- Select which stages to include ---
#stages_to_include = ["development", "test", "integration"]
stages_to_include = ["development", "test", "integration", "pre-prod", "production"]
#stages_to_include = ["production"]

# Collect stages and environments
stage_envs = {}
for app_data in data["applications"].values():
    for stage_name, stage_data in app_data["stages"].items():
        if not stage_name in stages_to_include:
            continue
        if stage_name not in stage_envs:
            stage_envs[stage_name] = []
        for env in stage_data["environments"]:
            if env not in stage_envs[stage_name]:
                stage_envs[stage_name].append(env)

# Render template
env = Environment(loader=FileSystemLoader("."))
template = env.get_template("view.html.j2")

html_output = template.render(
    applications=data["applications"],
    stage_envs=stage_envs
)

Path("Application-Catalog.html").write_text(html_output)
print("Generated Application-Catalog.html")
