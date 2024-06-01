const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/loltech-webchat-stomp'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/loltech/main-chat', (msg) => {
        showGreeting(JSON.parse(msg.body));
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    console.log("Activating, wait")
    stompClient.activate();
    console.log("Activation complete")
    loadChatHistory()
}

function disconnect() {
    console.log("Disconnecting, wait")
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMsg() {
    stompClient.publish({
        destination: "/webchat/send",
        body: JSON.stringify({
            'content': $("#msg").val(),
            'sentTime': new Date().toISOString(),
            'senderName': $("#user-name").val()
        })
    });
}

function loadChatHistory() {
    $.get(
        "messages", {},
        data => {
            data.forEach(it => showGreeting(it))
        }
    )
}

function showGreeting(body) {
    const message = "Author: " + body.senderName + ", at " + body.sentTime + ": " + body.content
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendMsg());
});