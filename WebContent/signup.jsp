<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href = "./css/style.css" rel = "stylesheet" type = "text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー登録</title>
</head>
<body>
<div class = "main-content">
<c:if test ="${not empty errorMessages }">
	<div class = "errorMessages">
		<ul>
			<c:forEach items = "${errorMessages }" var = "message">
				<li><c:out value = "${message }" /></li>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope = "session" />
</c:if>

<form action = "signup" method = "post" enctype = "multepart/form-date"><br>
	<label for = "name">名前</label>
	<input name = "name" value = "${editUser.name }" id = "name" />（名前はあなたの公開プロフィールに表示されます）<br>

	<label for = "account">アカウント名</label>
	<input name = "account" value = "${editUser.account }"  id = "account" />（あなたの公開プロフィール：http://localhost:3306/twitter/?account = アカウント名）<br>

	<label for = "password">パスワード</label>
	<input name = "password" type = "password" id = "password" /><br>

	<label for = "email">メールアドレス</label>
	<input name = "email" value = "${editUser.email }" id = "email" /><br>

	<label for = "description">説明</label>
	<textarea name = "description" cols = "35" rows = "5" id = "description"><c:out value = "${editUser.description }" /></textarea><br>

	<input type = "submit" value = "登録" /><br>
	<a href = "./">戻る</a>
</form>
<br>
<div class = "copyright">Copyright(c) Ohtsuka Jun</div>
</div>
</body>
</html>