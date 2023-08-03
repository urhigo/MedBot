package com.example.medbot.commands;

import com.example.medbot.model.BookFromDoctorRep;
import com.example.medbot.model.UserRep;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class DeleteBookFromDBCommand implements Command{
    @Override
    public SendMessage bodyCommand(Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep) {
        if(update.getMessage().getText().equals("delete book")){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
            sendMessage.setText("What do you want do?");

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRow = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            row.add("Delete all books");
            row.add("Delete some book");

            keyboardRow.add(row);
            replyKeyboardMarkup.setKeyboard(keyboardRow);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            return sendMessage;
        }
        return null;
    }
}
