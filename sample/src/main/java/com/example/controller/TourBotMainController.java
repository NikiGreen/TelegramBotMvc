package com.example.controller;

import com.example.service.CityInfoService;
import com.example.session.UserCommand;
import com.example.session.UserSession;
import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.github.telegram.mvc.api.EnableTelegram;
import com.github.telegram.mvc.api.TelegramRequest;
import com.github.telegram.mvc.config.TelegramBotBuilder;
import com.github.telegram.mvc.config.TelegramMvcConfiguration;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;


@EnableTelegram
@BotController
public class TourBotMainController implements TelegramMvcConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(TourBotMainController.class);

    public static final String CREATE_CITY_MESSAGE = "Введите имя города и через дефис информацию о нем. Пример: Минск - Лучший город на замле!";
    public static final String UPDATE_CITY_MESSAGE = "Введите имя города информацию о которомо вы хотите изменить и через дефис информацию о нем. " +
            "Пример: Минск - Лучший город на замле!";
    public static final String DELETE_CITY_MESSAGE = "Введите имя города, который вы хотите удалить! Пример: Минск";

    private CityInfoService cityInfoService;
    private Environment environment;

    @Autowired
    public TourBotMainController(CityInfoService cityInfoService, Environment environment) {
        this.cityInfoService = cityInfoService;
        this.environment = environment;
    }

    @Override
    public void configuration(TelegramBotBuilder telegramBotBuilder) {
        telegramBotBuilder
                .token(environment.getProperty("telegram.bot.token")).alias("myFirsBean");
    }

    @BotRequest("/start")
    BaseRequest hello(String text,
                      Long chatId,
                      TelegramRequest telegramRequest,
                      TelegramBot telegramBot,
                      Update update,
                      Message message,
                      Chat chat,
                      User user
    ) {
        return new SendMessage(chatId, message.text());
    }

    @BotRequest(value = "/create")
    BaseRequest answer(String text,
                       Long chatId,
                       TelegramRequest telegramRequest,
                       TelegramBot telegramBot,
                       Update update,
                       Message message,
                       Chat chat,
                       User user,
                       String id
    ) {
      UserSession.setCurrentUserCommand(UserCommand.CREATE);

      return new SendMessage(chatId, CREATE_CITY_MESSAGE);

    }
    @BotRequest(value = "/update")
    BaseRequest save(String text,
                       Long chatId,
                       TelegramRequest telegramRequest,
                       TelegramBot telegramBot,
                       Update update,
                       Message message,
                       Chat chat,
                       User user,
                       String id
    ) {
        UserSession.setCurrentUserCommand(UserCommand.UPDATE);

        return new SendMessage(chatId, UPDATE_CITY_MESSAGE);

    }@BotRequest(value = "/delete")
    BaseRequest edit(String text,
                       Long chatId,
                       TelegramRequest telegramRequest,
                       TelegramBot telegramBot,
                       Update update,
                       Message message,
                       Chat chat,
                       User user,
                       String id
    ) {
        UserSession.setCurrentUserCommand(UserCommand.DELETE);

        return new SendMessage(chatId, DELETE_CITY_MESSAGE);

    }@BotRequest(value = "{text}")
    BaseRequest delete(String text,
                       Long chatId,
                       TelegramRequest telegramRequest,
                       TelegramBot telegramBot,
                       Update update,
                       Message message,
                       Chat chat,
                       User user,
                       String id
    ) {

        return new SendMessage(chatId, UserSession.getCurrentUserCommand().toString());

    }
}
