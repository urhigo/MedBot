package com.example.medbot.service;

import com.example.medbot.commands.*;
import com.example.medbot.commands.doctors.*;
import com.example.medbot.confirm.Confirm;
import com.example.medbot.model.BookFromDoctorRep;
import com.example.medbot.model.UserRep;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Component
public class TelegramBot extends TelegramLongPollingBot {
    final Confirm confirm;

    final UserRep userRep;

    final BookFromDoctorRep bookFromDoctorRep;

    public TelegramBot(BookFromDoctorRep bookFromDoctorRep, Confirm confirm, UserRep userRep) {
        this.bookFromDoctorRep = bookFromDoctorRep;
        this.confirm = confirm;
        this.userRep = userRep;
    }

    @Override
    public String getBotUsername() {
        return confirm.getBotName();
    }

    @Override
    public String getBotToken() {
        return confirm.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            List<Command> commands = new ArrayList<>();
            commands.add(new StartCommand());
            commands.add(new UsualRegistrationCommand());
            commands.add(new BookFromTerapevtCommand());
            commands.add(new BookFromOkulistCommand());
            commands.add(new BookFromLorCommand());
            commands.add(new BookFromHirurgCommand());
            commands.add(new BookFromGinekologCommand());
            commands.add(new BookFromGastroenterologCommand());
            commands.add(new RegistrationTimeCommand());
            commands.add(new LookAllBooksCommand());
            commands.add(new DeleteBookFromDBCommand());
            commands.add(new DeleteAllBooksCommand());
            commands.add(new DeleteSomeFromDBCommand());
            commands.add(new DeleteSomeBookUserCommand());
            commands.add(new LookListDoctorsCommand());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("I dont know this command!");
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
            for (Command command : commands) {
                SendMessage commandMessage = command.bodyCommand(update, userRep, bookFromDoctorRep);
                if (commandMessage != null) {
                    sendMessage = commandMessage;
                    break;
                }
            }
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

}