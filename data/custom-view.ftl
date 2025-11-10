<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>CUSTOM Application Catalog</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 10px; }
    table { border-collapse: collapse; width: 100%; font-size: 12px; }
    th, td { border: 1px solid #ccc; padding: 2px 4px; text-align: left; vertical-align: middle; }
    th { background-color: #f0f0f0; }
    td a { display: inline-block; margin: 1px; font-size: 14px; text-decoration: none; }
    td { white-space: nowrap; text-align: left; }
    .landing { color: #1E90FF; }
    .health  { color: #008000; }
    .info    { color: #FF4500; }
    .log     { color: #FF8C00; }
    .misc1   { color: #8B4513; }
    h1 { font-size: 16px; margin-bottom: 5px; }
    .legend { font-size: 12px; margin-bottom: 5px; }
    .legend span { margin-right: 8px; }
  </style>
</head>
<body>
  <h1>CUSTOM Application Catalog</h1>

  <table>
    <thead>
      <tr>
        <th rowspan="2">App</th>
        <#list stage_envs?keys as stage_name>
          <th colspan="${stage_envs[stage_name]?size}">${stage_name?cap_first}</th>
        </#list>
      </tr>
      <tr>
        <#list stage_envs?keys as stage_name>
          <#list stage_envs[stage_name] as env>
            <th>${env}</th>
          </#list>
        </#list>
      </tr>
    </thead>
    <tbody>
      <#list applications?keys as app_name>
        <#assign app_data = applications[app_name]>
        <tr>
          <td>${app_name}</td>
          <#list stage_envs?keys as stage_name>
            <#list stage_envs[stage_name] as env>
              <td>
                <#assign stages = app_data["stages"]!{} >
                <#assign urls = app_data["urls"]!{} >
                <#assign stage = stages[stage_name]!{} >
                <#assign envs = stage["environments"]![] >

                <#if envs?seq_contains(env)>
                  <#list urls?keys as url_name>
                    <#assign url_template = urls[url_name]>
                    <#assign url = url_template?replace("ENVIRONMENT", env)?replace("APPLICATION", app_name)>
                    <#if url_name == "landing-page"><a class="landing" href="${url}" title="${url_name}">🏠</a></#if>
                    <#if url_name == "health"><a class="health" href="${url}" title="${url_name}">❤️</a></#if>
                    <#if url_name == "info"><a class="info" href="${url}" title="${url_name}">🛈</a></#if>
                    <#if url_name == "log"><a class="log" href="${url}" title="${url_name}">🗒️</a></#if>
                    <#if url_name?starts_with("misc")><a class="misc1" href="${url}" title="${url_name}">⚙️</a></#if>
                  </#list>
                <#else>-</#if>
              </td>
            </#list>
          </#list>
        </tr>
      </#list>
    </tbody>
  </table>

  <div class="legend">
    URL icons:
    <span class="landing">🏠 Landing-Page</span>
    <span class="health">❤️ Health</span>
    <span class="info">🛈 Info</span>
    <span class="log">🗒️ Logs</span>
    <span class="misc1">⚙️ Misc</span>
  </div>
</body>
</html>
