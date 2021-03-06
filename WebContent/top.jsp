<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link href = "./css/style.css" rel = "stylesheet" type = "text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>簡易twitter</title>
</head>
<body>
<div class = "main-content">

<div class = "header">
	<c:if test = "${empty loginUser }">
		<a href = "login">ログイン</a>
		<a href = "signup">登録する</a>
</c:if>
<c:if test = "${not empty loginUser }">
	<a href = "./">ホーム</a>
	<a href = "settings">設定</a>
	<a href = "logout">ログアウト</a>
</c:if>
</div><!-- header -->

<!-- ログインしたら表示するユーザー情報 -->
<c:if test = "${not empty loginUser }">
	<div class = "profile">
		<div class = "name"><h2><c:out value = "${loginUser.name }" /></h2></div>
	</div>
	<div class = "account">
		@<c:out value = "${loginUser.account }" />
	</div>
	<div class = "account">
		<c:out value = "${loginUser.description }" />
	</div>
</c:if>

<!-- つぶやき機能 -->
<div class = "form-area">
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
	<c:if test = "${isShowMessageForm }">
		<form action="newMessage" method = "post">
			いまどうしてる？<br>
			<textarea name = "message" cols = "100" rows = "5" class = "tweet-box"></textarea>
			<br>
			<input type = "submit" value = "つぶやく"/>（140文字まで）
		</form>
	</c:if>
</div>

<!-- メッセージ表示機能 -->
<div class = "messages">
	<c:forEach items = "${messages }" var = "message">
		<div class = "message">
			<div class = "account-name">
				<span class = "account"><c:out value = "${message.account }"></c:out></span>
				<span class = "name"><c:out value = "${message.name }" /></span>
			</div>
			<div class = "text"><c:out value = "${message.text }" /></div>
			<div class = "date"><fmt:formatDate value="${message.insertDate }" pattern = "yyy/MM/dd HH:mm:ss" /></div>
		</div>
	</c:forEach>
</div>

<div class = "copyright">Copyright(c) Ohtsuka Jun</div>
</div><!-- main -->
</body>
</html>