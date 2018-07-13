var webSocketLink = "ws://localhost:8080/room";
var infoText = document.getElementById("info");
var messageArea = document.getElementById("messageArea");
var sendedMessages = document.getElementById("messages");
var login = document.getElementById("loginArea");

var webSocket = new WebSocket(webSocketLink);

webSocket.onopen = function (ev) {
    infoText.innerHTML = "Połączono";
}

webSocket.onmessage = function (ev) {
    sendedMessages.innerHTML = sendedMessages.innerHTML + "<li>" + ev.data + "</li>"
    sendedMessages.scrollTop = sendedMessages.scrollHeight;
}

function sendMessage() {
    var text = messageArea.value;
    messageArea.value = "";
    webSocket.send(text);
}

function setLogin() {
    var login = login.value;
    webSocket.send(text);
}