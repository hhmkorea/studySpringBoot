// 로그인 시스템 대신 임시방편, 프롬프트에 강제로 메세지 넣기.
let username = prompt("아이디를 입력하세요.");
let roomNum = prompt("채팅방 번호를 입력하세요.");

// SSE 연결하기
// const eventSouce = new EventSource("http://localhost:8080/sender/ssar/receiver/cos"); // 귓속말 용.
const eventSouce = new EventSource(`http://localhost:8080/chat/roomNum/${roomNum}`); // ``을 쓰면 변수 받기가 편함.
eventSouce.onmessage = (event) => {
    const data = JSON.parse(event.data);
    if (data.sender === username) { // 로그인한 유저가 보낸 메시지
        // 파란박스(오른쪽)
        initMyMessage(data);
    } else {
        // 회색박스(왼쪽)
        initYourMessage(data);
    }
}

// 파란박스 만들기
function getSendMsgBox(data) {

    let md = data.createdAt.substring(5, 10)
    let tm = data.createdAt.substring(11, 16)
    convertTime = tm + " | " + md

    return `<div class="sent_msg">
                <p>${data.msg}</p>
                <span class="time_date">${convertTime} / ${data.sender}</span>
            </div>`; // ``로 감싸서 보냄.
}

// 회색박스 만들기
function getReceiveMsgBox(data) {

    let md = data.createdAt.substring(5, 10)
    let tm = data.createdAt.substring(11, 16)
    convertTime = tm + " | " + md

    return `<div class="received_withd_msg">
                <p>${data.msg}</p>
                <span class="time_date">${convertTime} / ${data.sender}</span>
            </div>`; // ``로 감싸서 보냄.
}

// 최초 초기화될 때 1번방 3건이 있으면 3건 다 가져와요.
// addMessage() 함수 호출시 DB에 Insert 되고, 그 데이터가 자동으로 흘러들어온다(SSE)
// 파란박스 초기화하기
function initMyMessage(data) {
    let chatBox = document.querySelector("#chat-box");
    let sendBox = document.createElement("div");

    sendBox.className = "outgoing_msg";
    sendBox.innerHTML = getSendMsgBox(data);
    // let parseTime = new Date(data.createdAt.replace('T',' ').replace('Z',''));
    // sendBox.innerHTML = getSendMsgBox(data.msg, parseStringToDateTime(parseTime));

    chatBox.append(sendBox);
}

//회색박스 초기화하기
function initYourMessage(data) {
    let chatBox = document.querySelector("#chat-box");
    let receivedBox = document.createElement("div");

    receivedBox.className = "received_msg";
    receivedBox.innerHTML = getReceiveMsgBox(data);

    chatBox.append(receivedBox);
}

// Ajax로 채팅 메시지를 전송
async function addMessage() { // async ~ wait : 기다렸다가 파싱해서 데이타 출력.
    let msgInput = document.querySelector("#chat-outgoing-msg");

    // 내가 만든 메세지 서버로 전송하기
    let chat = {
        sender: username,
        roomNum: roomNum,
        msg: msgInput.value
    };

    await fetch("http://localhost:8080/chat", { // fetch : js 비동기 통신 함수
        method: "post", // http post 메서드 (새로운 데이터를 write할때 쓰이는 함수)
        body: JSON.stringify(chat), // JS -> JSON
        headers: {
            "Content-Type" : "application/json; charset=utf-8"
        }
    });

    msgInput.value = "";
}

// 버튼 클릭시 메시지 전송
document.querySelector("#chat-send").addEventListener("click", () => {
    addMessage();
});

// 엔터를 치면 메시지 전송
document.querySelector("#chat-outgoing-msg").addEventListener("keydown", (e) => {
    if (e.keyCode === 13) {
        addMessage();
    }
});

// Date 타입을 String으로 변환
// function parseStringToDateTime(parseTime) {
//     parseTime.setHours(parseTime.getHours());
//
//     let year = parseTime.getFullYear();
//     let month = ('0' + (parseTime.getMonth() + 1)).slice(-2);
//     let day = ('0' + parseTime.getDate()).slice(-2);
//
//     let hours = parseTime.getHours();
//     let minutes = ('0' + parseTime.getMinutes()).slice(-2);
//     let ampm = hours >= 12 ? '오후' : '오전'; // mongoDB 시간대가 UTC 기준, 한국 시간 기준으로 하려면 +9
//
//     hours = hours % 12;
//     hours = hours ? hours : 12; // 0시를 12시로 표시
//
//     let parseTimeString = year + '-' + month + '-' + day + ' ' + ampm +' '+ hours + ':' + minutes;
//
//     return parseTimeString;
//
// }
