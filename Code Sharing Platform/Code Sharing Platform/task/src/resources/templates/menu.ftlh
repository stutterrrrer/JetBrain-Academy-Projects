<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Menu</title>
</head>
<body>
	<#list pizzas as pizza>
		${pizza.name}
		${pizza.price}
	</#list>
</body>
</html>