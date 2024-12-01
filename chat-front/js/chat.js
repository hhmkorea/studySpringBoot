const eventSouce = new EventSource("http://localhost:8080/sender/ssar/receiver/cos");

eventSouce.onmessage = (event) => {
    console.log(1, event);
    const data = JSON.parse(event.data);
    console.log(2, data);
    initMessage(data);
}

function getSendMsgBox(msg, time) {
    return `<div className="sent_msg"> <p>${msg}</p> <span className="time_date"> ${time} </span> </div>`;
}

function parseStringToDateTime(parseTime) { // Date 타입을 String으로 변환
    parseTime.setHours(parseTime.getHours() + 9);

    let year = parseTime.getFullYear();
    let month = ('0' + (parseTime.getMonth() + 1)).slice(-2);
    let day = ('0' + parseTime.getDate()).slice(-2);

    let hours = parseTime.getHours();
    let minutes = ('0' + parseTime.getMinutes()).slice(-2);
    let ampm = hours >= 12 ? '오후' : '오전';

    hours = hours % 12;
    hours = hours ? hours : 12; // 0시를 12시로 표시

    let parseTimeString = year + '-' + month + '-' + day + ' ' + ampm +' '+ hours + ':' + minutes;

    return parseTimeString;
    // TO-DO : 지금 오후 7시 43분인데... Sun Dec 01 2024 03:24:04 GMT+0900 (한국 표준시)로 등록되네.. 에효..
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


function addMessage() {
    let chatBox = document.querySelector("#chat-box");
    let msgInput = document.querySelector("#chat-outgoing-msg");

    // alert(msgInput.value);

    let chatOutgoingBox = document.createElement("div");
    chatOutgoingBox.className = "outgoing_msg";

    let parseTime = new Date();

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