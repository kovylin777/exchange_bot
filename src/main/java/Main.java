import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.logging.BotLogger;
import requester.PriceProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(new ExchangeBot());
        } catch (TelegramApiRequestException e) {
            BotLogger.error("Oops, something went wrong while registering bot", e);
        }

        Runnable runnable = new Runnable() {
            public void run() {
                new PriceProvider().setCompanies();
            }
        };
        ScheduledExecutorService service = Executors
            .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.MINUTES);
    }
}
