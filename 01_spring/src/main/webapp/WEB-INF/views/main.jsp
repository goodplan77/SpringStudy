<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content">
		<div class="content-1">
			<h3>회원 정보 조회</h3>
			
			<p>아이디를 입력 받아 일치하는 회원 정보를 출력</p>
			아이디 : <input type='text' id='in1'>
			<button id="select1">조회</button>
			<div id="result" style="height:150px">
				
			</div>
		</div>
	</div>
	<script>
		$('#select1').click(function() {
			var $in1 = $('#in1');
			var $result = $("#result");
			
			$.ajax({
				url:'${contextPath}/member/selectOne',
				data : {
					userId : $in1.val()
				} ,
				type : "POST" ,
				success : function(result) {
					// js 객체 형태로 응답받을 예정 {key:value , key:value}
					console.log(result);
					
					// console.log(JSON.parse(result)); // 넘겨온 객체를 JSON 형태로
					// console.log(JSON.stringify(new Object)); // 객체 -> JSON -> 문자열
					
					var $ul = $("<ul></ul>");
					
					var $li = $("<li></li>").text("아이디 : " + result.userId);
					var $li2 = $("<li></li>").text("이름 : " + result.userName);
					$ul.append($li).append($li2).appendTo($result);
				} , error : function (xhr) {
					if(xhr.status == 404){
						alert("존재하지 않는 회원 입니다.");
					} else {
						alert("에러가 발생했습니다.");
					}
				}
			}) 
		})
	</script>
	
	<%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>