function getMsg(msg) {
    return `<div className="sent_msg">
    <p>${msg}</p>
    <span className="time_date"> 11:18 | Today</span>
</div>`;
}

document.querySelector("#chat-send").addEventListener("click", () => {
    //alert("클릭됨")
    let chatBox = document.querySelector("#chat-box");
    let msgInput = document.querySelector("#chat-outgoing-msg");

    //alert(msgInput.value);

    let chatOutgoingBox = document.createElement("div");
    chatOutgoingBox.className = "outgoing_msg";
    chatOutgoingBox.innerHTML = getMsg(msgInput.value);
    chatBox.append(chatOutgoingBox);
    msgInput.value = "";
    //chatOutgoingBox.innerHTML = "안녕";
    //chatBox.append("<div>안녕</div>");
});

document.querySelector("#chat-outgoing-msg").addEventListener("keydown", (e) => {
    // console.log(e.keyCode);
    if (e.keyCode === 13) {
        //alert("엔터 요청됨");
        let chatBox = document.querySelector("#chat-box");
        let msgInput = document.querySelector("#chat-outgoing-msg");

        //alert(msgInput.value);

        let chatOutgoingBox = document.createElement("div");
        chatOutgoingBox.className = "outgoing_msg";
        chatOutgoingBox.innerHTML = getMsg(msgInput.value);
        chatBox.append(chatOutgoingBox);
        msgInput.value = "";
        //chatOutgoingBox.innerHTML = "안녕";
        //chatBox.append("<div>안녕</div>");
    }
});