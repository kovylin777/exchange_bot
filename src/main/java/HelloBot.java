import models.Price;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import requester.PriceProvider;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class HelloBot extends AbilityBot {

    public static final String BOT_TOKEN = "1223769738:AAFZjoRhgohRvdqB0D3jFaSNft9jLcsYZi4";

    public static final String BOT_USERNAME = "currencykh_bot";

    public final PriceProvider PRICE_PROVIDER = new PriceProvider();

    public HelloBot() {
        super(BOT_TOKEN, BOT_USERNAME);
    }

    @Override
    public int creatorId() {
        return 267850708;
    }

    private String prepareMessage(Price bestBuyingPrice, Price bestSellingPrice) {
        String message =
            "➡️\uD83D\uDCB5 Самая выгодная покупка USD:\n"
            + "\uD83D\uDCDB Название: " + bestBuyingPrice.getName() + "\n"
            + "\uD83C\uDF0D Cайт: " + bestBuyingPrice.getUrl() + "\n"
            + "\uD83D\uDCCD Адрес:\n" + bestBuyingPrice.getAddress() + "\n"
            + "\uD83D\uDCF1 Телефон: " + bestBuyingPrice.getPhone() + "\n"
            + "\uD83E\uDD11 *КУРС ПОКУПКИ: " + bestBuyingPrice.getBuyingRate() + "*\n\n"
            + "⬅️️\uD83D\uDCB5 Самая выгодная продажа USD:\n"
            + "\uD83D\uDCDB Название: " + bestSellingPrice.getName() + "\n"
            + "\uD83C\uDF0D Cайт: " + bestSellingPrice.getUrl() + "\n"
            + "\uD83D\uDCCD Адрес:\n" + bestSellingPrice.getAddress() + "\n"
            + "\uD83D\uDCF1 Телефон: " + bestSellingPrice.getPhone() + "\n"
            + "\uD83E\uDD11 *КУРС ПРОДАЖИ: " + bestSellingPrice.getSellingRate() + "*";
        return message;
    }

    public Ability saysHelloWorld() {
        return Ability.builder()
            .name("buy") // Name and command (/hello)
            .info("Самыая выгодная покупка USD") // Necessary if you want it to be reported via /commands
            .privacy(PUBLIC)  // Choose from Privacy Class (Public, Admin, Creator)
            .locality(ALL) // Choose from Locality enum Class (User, Group, PUBLIC)
            .input(0) // Arguments required for command (0 for ignore)
            .action(ctx -> {
                PRICE_PROVIDER.setPrices();
                Price bestBuyingPrice = PRICE_PROVIDER.getBestBuyingPrice();
                Price bestSellingPrice = PRICE_PROVIDER.getBestSellingPrice();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setParseMode("Markdown");
                sendMessage.disableWebPagePreview();
                sendMessage.setChatId(ctx.chatId());
                sendMessage.setText(prepareMessage(bestBuyingPrice, bestSellingPrice));
                try {
                    sender.sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            })
            .build();
    }
}