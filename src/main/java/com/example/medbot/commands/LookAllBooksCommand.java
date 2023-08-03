package com.example.medbot.commands;

import com.example.medbot.model.BookFromDoctorDB;
import com.example.medbot.model.BookFromDoctorRep;
import com.example.medbot.model.User;
import com.example.medbot.model.UserRep;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LookAllBooksCommand implements Command {
    @Override
    public SendMessage bodyCommand(Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep) {
        if (update.getMessage().getText().equals("look all your book")) {
            List<BookFromDoctorDB> listBooks = bookFromDoctorRep.findAllByUserId(update.getMessage().getChatId());
            Optional<User> user = userRep.findById(update.getMessage().getChatId());

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));

            if(!listBooks.isEmpty()) {
                StringBuilder textMessageF = new StringBuilder("List books:");

                for (BookFromDoctorDB book : listBooks) {
                    String textMessage = "\nDear " + user.get().getFirstName() + ", you are registrated from:" + book.getDoctor() +
                            "\nYou need come at: " + book.getTime() +
                            "\n--------------";
                    textMessageF.append(textMessage);
                }
                sendMessage.setText(textMessageF.toString());
            } else {
                sendMessage.setText("Dear " + user.get().getFirstName() + ", you don't have any books");
            }
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();

            row.add("registration");
            row.add("delete book");
            row.add("look list doctors");

            keyboardRows.add(row);
            keyboardMarkup.setKeyboard(keyboardRows);
            sendMessage.setReplyMarkup(keyboardMarkup);
            return sendMessage;
        }
        return null;
    }
}
