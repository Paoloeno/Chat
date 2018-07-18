var webSocketLink = "ws://localhost:8080/room";
var infoText = document.getElementById("info");
var messageArea = document.getElementById("messageArea");
var sendedMessages = document.getElementById("messages");

var webSocket = new WebSocket(webSocketLink);

webSocket.onopen = function () {
    infoText.innerHTML = "Połączono z chatem";
};

webSocket.onmessage = function (ev) {
    sendedMessages.innerHTML = sendedMessages.innerHTML + "<li>" + ev.data + "</li>";
    sendedMessages.scrollTop = sendedMessages.scrollHeight;
};

function sendMessage() {
    var text = messageArea.value;
    messageArea.value = "";
    webSocket.send(text);
}
