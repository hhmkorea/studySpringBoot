const eventSouce = new EventSource("http://localhost:8080/sender/ssar/receiver/cos");

eventSouce.onmessage = (event) => {
    console.log(1, event);
    const data = JSON.parse(event.data);
    console.log(2, data);
    initMessage(data);
}

function getSendMsgBox(msg, time) {
    return `<div class="sent_msg">
                <p>${msg}</p>
                <span class="time_date">${time}</span>
            </div>`;
}

function initMessage(data) {
    let chatBox = document.querySelector("#chat-box");
    let msgInput = document.querySelector("#chat-outgoing-msg");

    // alert(msgInput.value);

    let chatOutgoingBox = document.createElement("div");
    chatOutgoingBox.className = "outgoing_msg";

    let parseTime = new Date(data.createdAt.replace('T',' ').replace('Z',''));
    chatOutgoingBox.innerHTML = getSendMsgBox(data.msg, parseStringToDateTime(parseTime));
    chatBox.append(chatOutgoingBox);
    msgInput.value = "";
}

async function addMessage() { // async ~ wait : 기다렸다가 파싱해서 데이타 출력.
    let chatBox = document.querySelector("#chat-box");
    let msgInput = document.querySelector("#chat-outgoing-msg");

    // alert(msgInput.value);

    let chatOutgoingBox = document.createElement("div");
    chatOutgoingBox.className = "outgoing_msg";

    let parseTime = new Date();

    // 내가 만든 메세지 서버로 전송하기
    let chat = {
        sender: "ssar",
        receiver: "cos",
        msg: msgInput.value
    };

    let response = await fetch("http://localhost:8080/chat", { // fetch : js 비동기 통신 함수
        method: "post", // http post 메서드 (새로운 데이터를 write할때 쓰이는 함수)
        body: JSON.stringify(chat), // JS -> JSON
        headers: {
            "Content-Type" : "application/json; charset=utf-8"
        }
    });

    // console.log(response);

    let parseResponse = response.json();

    console.log(parseResponse);

    chatOutgoingBox.innerHTML = getSendMsgBox(msgInput.value, parseStringToDateTime(parseTime));
    chatBox.append(chatOutgoingBox);
    msgInput.value = "";
}

document.querySelector("#chat-send").addEventListener("click", () => {
    //alert("클릭됨")
    addMessage();
});

document.querySelector("#chat-outgoing-msg").addEventListener("keydown", (e) => {
    if (e.keyCode === 13) {
        addMessage();
    }
});

function parseStringToDateTime(parseTime) { // Date 타입을 String으로 변환
    parseTime.setHours(parseTime.getHours());

    let year = parseTime.getFullYear();
    let month = ('0' + (parseTime.getMonth() + 1)).slice(-2);
    let day = ('0' + parseTime.getDate()).slice(-2);

    let hours = parseTime.getHours();
    let minutes = ('0' + parseTime.getMinutes()).slice(-2);
    let ampm = hours >= 12 ? '오후' : '오전'; // mongoDB 시간대가 UTC 기준, 한국 시간 기준으로 하려면 +9

    hours = hours % 12;
    hours = hours ? hours : 12; // 0시를 12시로 표시

    let parseTimeString = year + '-' + month + '-' + day + ' ' + ampm +' '+ hours + ':' + minutes;

    return parseTimeString;

}
