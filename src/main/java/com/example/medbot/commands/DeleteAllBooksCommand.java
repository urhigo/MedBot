package com.example.medbot.commands;

import com.example.medbot.model.BookFromDoctorDB;
import com.example.medbot.model.BookFromDoctorRep;
import com.example.medbot.model.UserRep;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class DeleteAllBooksCommand implements Command {
    @Override
    public SendMessage bodyCommand(Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep) {
        if (update.getMessage().getText().equals("Delete all books")) {
            List<BookFromDoctorDB> listBooks = bookFromDoctorRep.findAllByUserId(update.getMessage().getChatId());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
            if (listBooks.isEmpty()) {
                sendMessage.setText("You dont have books");
            } else {
                bookFromDoctorRep.deleteAll(listBooks);
                sendMessage.setText("You delete all your books");
            }
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            row.add("registration");
            row.add("look all your book");
            row.add("look list doctors");

            keyboardRows.add(row);
            replyKeyboardMarkup.setKeyboard(keyboardRows);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);

            return sendMessage;
        }
        return null;
    }
}
