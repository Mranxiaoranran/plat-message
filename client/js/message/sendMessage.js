function sendMessageHandler(userName, message,chatSocket) {
    var jsonObject = {
        userName: userName,
        message: message
    };
    chatSocket.json.send(jsonObject);
}