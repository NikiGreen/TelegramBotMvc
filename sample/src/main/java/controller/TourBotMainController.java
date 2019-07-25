package controller;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import repository.CityInfoRepository;

@SpringBootApplication
@EnableTelegram
@BotController
public class TourBotMainController implements TelegramMvcConfiguration {

    private final CityInfoRepository cityInfoRepository;
    private static final Logger logger = LoggerFactory.getLogger(TourBotMainController.class);

    private Environment environment;

    @Autowired
    public TourBotMainController(CityInfoRepository cityInfoRepository, Environment environment) {
        this.cityInfoRepository = cityInfoRepository;
        this.environment = environment;
    }



    public static void main(String[] args) {
        SpringApplication.run(TourBotMainController.class);
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
        logger.info("Text = {}", text);
        logger.info("ChatId or UserId = {}", chatId);
        logger.info("Telegram Request = {}", telegramRequest);
        logger.info("TelegramBot = {}", telegramBot);
        logger.info("Update = {}", update);
        logger.info("Message = {}", message);
        logger.info("Chat = {}", chat);
        logger.info("User = {}", user);

        return new SendMessage(chatId, message.text());
    }

    @BotRequest(value = "{text}")
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
        return new SendMessage(chatId, /*message.text()*/message.text());
    }
}
