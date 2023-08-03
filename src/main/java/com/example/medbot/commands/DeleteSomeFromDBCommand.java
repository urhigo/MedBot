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

public class DeleteSomeFromDBCommand implements Command{
    @Override
    public SendMessage bodyCommand(Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep) {
        if(update.getMessage().getText().equals("Delete some book")){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));

            List<BookFromDoctorDB> listBooks = bookFromDoctorRep.findAllByUserId(update.getMessage().getChatId());
            if (listBooks.isEmpty()){
                sendMessage.setText("You dont have books");
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
            } else {
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                int counter = 0;
                while (counter < listBooks.size()) {
                    KeyboardRow keyboardRow = new KeyboardRow();
                    for (int i = counter; (i < counter + 3) && i < listBooks.size(); i++) {
                        keyboardRow.add(String.valueOf(listBooks.get(i).getDoctor()) + " " + listBooks.get(i).getTime());
                    }
                    keyboardRows.add(keyboardRow);
                    counter += 3;
                }
                replyKeyboardMarkup.setKeyboard(keyboardRows);
                sendMessage.setText("Choose book what you need delete!");
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                return sendMessage;
            }
        }
        return null;
    }
}
