// 1) 메세지 전송기능
document.querySelector("#send").addEventListener('click' , function(){
    var inputChatting = document.querySelector('#inputChatting');

    // 입력하지 않은 경우 return
    if(!inputChatting.value.trim()){
        alert("뭐라도 입력하세요");
        inputChatting.value="";
        inputChatting.focus();
        return;
    }

    // 메세지를 전달할 객체 생성
    var chatMessage = {
        message : inputChatting.value , 
        chatRoomNo ,
        userNo , 
        userName
    };

    // 데이터 전달시 js의 객체형태로는 전달못함 [Object object] -> JSON 형태로 파싱해서 보내기
    var jsonParseMessage = JSON.stringify(chatMessage); // json 형변환 완료

    // send(값) : 웹소켓 핸들러로 값을 보내는 역할은 한다.
    chattingSocket.send(jsonParseMessage);

    inputChatting.value="";

})
// 2) 메시지 응답기능 -> 서버 웹소켓 핸들러에서 sendMessage 하는 구문을 감지하는 이벤트 핸들러(onmessage)
chattingSocket.onmessage = function(e){
    console.log(e.data); // json 형태의 데이터

    // 전달 받은 메세지(json)를 js 객체로 변환
    var chatMessage = JSON.parse(e.data);
    console.log(chatMessage);
    
    var li = document.createElement("li");
    var p = document.createElement("p");
    p.classList.add('chat');

    p.innerHTML = chatMessage.message.replace(/\\n/gm,"<br>"); // <p class = "chat">11<br>11</p>

    var span = document.createElement("span");
    span.classList.add("chatDate");
    span.innerText = '2024-01-01';

    // 내가 쓴 글인지 아닌지 체크
    if(userNo == chatMessage.userNo){
        li.classList.add("myChat");
        li.append(span , p);
    } else {
        li.innerHTML = `<b>${chatMessage.userName}</b>`;
        li.append(p , span);
    }

    // 채팅창 요소 가져오기
    const displayChatting = document.querySelector('.display-chatting');

    displayChatting.append(li); // 채팅창에 내용추가

    // scrollTop : 스크롤바의 위치
    // scrollHeight : 스크롤되는 요소의 전체 크기
    displayChatting.scrollTop = displayChatting.scrollHeight;
}