<html lang="en">
<head>
    <title>Latest</title>
</head>
<body>
    <#list snippets as codeSnippet>
            <pre id="code_snippet">
                ${codeSnippet.code}
            </pre>
            <span id="load_date">
                ${codeSnippet.dateTime}
            </span>
    </#list>
</body>
</html>