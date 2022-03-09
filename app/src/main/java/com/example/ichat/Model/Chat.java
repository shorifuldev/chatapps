package com.example.ichat.Model;

public class Chat {

   private String sender,receiver,message,chatId;

    public Chat() {
    }

    public Chat(String sender, String receiver, String message, String chatId) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.chatId = chatId;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public String getChatId() {
        return chatId;
    }
}
