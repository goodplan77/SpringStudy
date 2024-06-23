<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>


 <main class="container my-4">
     <section>
         <h2 class="h5">${board.title }</h2>
         <p class="text-muted">작성자: KH 수달 | 작성일: <fmt:formatDate value="${board.createDate}" pattern="yy-MM-dd" /></p>
         <div>
             <p>${board.subTitle }</p>
             <div class="list-group-row flex-wrap row">
             	<c:forEach items="${board.imgList }" var='imgList'>
             		<article class="col-md-4">
		                <div class="card mb-4">
		                    <img src="${contextPath}${imgList.uploadPath}${imgList.changeName}" class="card-img-top thumbnail" alt="Image 2">
		                </div>
	                </article>
             	</c:forEach>
	            <!--  <article class="col-md-4">
	                <div class="card mb-4">
	                    <img src="/study/resources/upload/study/book1.jpg" class="card-img-top thumbnail" alt="Image 1">
	                </div>
	             </article> -->
             </div>
             <p>${board.content}</p>
         </div>
         <a href="${contextPath }/list" class="btn btn-secondary mt-3">목록으로</a>
     </section>
 </main>


<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>
