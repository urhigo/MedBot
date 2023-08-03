package com.example.medbot.commands;

import com.example.medbot.model.BookFromDoctorRep;
import com.example.medbot.model.User;
import com.example.medbot.model.UserRep;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StartCommand implements Command{

    @Override
    public SendMessage bodyCommand(Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep) {
        if (update.getMessage().getText().equals("/start")){
            SendMessage sendMessage = new SendMessage();
            if (userRep.findById(update.getMessage().getChatId()).isEmpty()){
                User newUser = new User();

                var chat = update.getMessage().getChat();

                newUser.setFirstName(chat.getFirstName());
                newUser.setLastName(chat.getLastName());
                newUser.setUserName(chat.getUserName());
                newUser.setChatId(update.getMessage().getChatId());
                newUser.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

                userRep.save(newUser);

                String textMessage = "You are book at the bot, " + newUser.getFirstName();

                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                sendMessage.setText(textMessage);

            } else {
                Optional<User> someUser = userRep.findById(update.getMessage().getChatId());

                String textMessage = "Hi dear user " + someUser.get().getFirstName();

                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                sendMessage.setText(textMessage);
            }
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();

            row.add("registration");
            row.add("look all your book");

            keyboardRows.add(row);

            row = new KeyboardRow();

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
