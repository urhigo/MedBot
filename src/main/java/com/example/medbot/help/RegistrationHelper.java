package com.example.medbot.help;

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

public class RegistrationHelper {

    public void setInfDoctorTimePointReg(Update update, UserRep userRep) {
        Optional<User> user = userRep.findById(update.getMessage().getChatId());
        user.get().setTimePointRegFromDoctor(update.getMessage().getText());
        userRep.save(user.get());
    }

    public SendMessage getFreeTimeIntervals(Update update, BookFromDoctorRep bookFromDoctorRep) {
        List<BookFromDoctorDB> listBooks = bookFromDoctorRep.findAllByDoctor(Doctors.valueOf(update.getMessage().getText()));
        List<String> freeTimeIntervals = new GenerateTime().generateTime();
        if (!listBooks.isEmpty()) {
            for (BookFromDoctorDB book : listBooks) {
                for (int i = 0; i < freeTimeIntervals.size(); i++) {
                    if (book.getTime().equals(freeTimeIntervals.get(i))) {
                        freeTimeIntervals.remove(i);
                        break;
                    }
                }
            }
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));

        if (freeTimeIntervals.isEmpty()) {
            sendMessage.setText("No free time");
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
        } else {
            sendMessage.setText("Choose time what do you need!");
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRows = new ArrayList<>();

            int counter = 0;
            while (counter < freeTimeIntervals.size()) {
                KeyboardRow keyboardRow = new KeyboardRow();
                for (int i = counter; (i < counter + 5) && i < freeTimeIntervals.size(); i++) {
                    keyboardRow.add(freeTimeIntervals.get(i));
                }
                keyboardRows.add(keyboardRow);
                counter += 5;
            }

            replyKeyboardMarkup.setKeyboard(keyboardRows);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }
}
