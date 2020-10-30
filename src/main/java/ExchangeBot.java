import models.Company;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import requester.CompanyProvider;

import java.math.BigDecimal;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class ExchangeBot extends AbilityBot {

    public static final String BOT_TOKEN = "1223769738:AAFZjoRhgohRvdqB0D3jFaSNft9jLcsYZi4";

    public static final String BOT_USERNAME = "currencykh_bot";

    private static final CompanyProvider COMPANY_PROVIDER = new CompanyProvider();

    public ExchangeBot() {
        super(BOT_TOKEN, BOT_USERNAME);
    }

    @Override
    public int creatorId() {
        return 267850708;
    }

    private String prepareMessage(Company bestBuyingCompany, Company bestSellingCompany, String currencyCode) {
        BigDecimal buyingRate = bestBuyingCompany.getCurrencyRates().stream()
            .filter(e -> e.getCode().equals(currencyCode)).findFirst().get().getBuyingRate();
        BigDecimal sellingRate = bestSellingCompany.getCurrencyRates().stream()
            .filter(e -> e.getCode().equals(currencyCode)).findFirst().get().getSellingRate();

        return
        "➡️\uD83D\uDCB5 Самая выгодная покупка " + currencyCode + ":\n"
        + "\uD83D\uDCDB Название: " + bestBuyingCompany.getName() + "\n"
        + "\uD83C\uDF0D Cайт: " + bestBuyingCompany.getUrl() + "\n"
        + "\uD83D\uDCCD Адрес:\n" + bestBuyingCompany.getAddress() + "\n"
        + "\uD83D\uDCF1 Телефон: " + bestBuyingCompany.getPhone() + "\n"
        + "\uD83E\uDD11 *КУРС ПОКУПКИ: " + buyingRate + "*\n\n"
        + "⬅️️\uD83D\uDCB5 Самая выгодная продажа " + currencyCode + ":\n"
        + "\uD83D\uDCDB Название: " + bestSellingCompany.getName() + "\n"
        + "\uD83C\uDF0D Cайт: " + bestSellingCompany.getUrl() + "\n"
        + "\uD83D\uDCCD Адрес:\n" + bestSellingCompany.getAddress() + "\n"
        + "\uD83D\uDCF1 Телефон: " + bestSellingCompany.getPhone() + "\n"
        + "\uD83E\uDD11 *КУРС ПРОДАЖИ: " + sellingRate + "*"
        + "\n\n⏰ Данные актуальны на " + CompanyProvider.getDateFormatter().format(CompanyProvider.getActualityDate());
    }

    public Ability usdReply() {
        return Ability.builder()
            .name("usd")
            .info("Самыая выгодная покупка USD") // Necessary if you want it to be reported via /commands
            .privacy(PUBLIC)
            .locality(ALL)
            .input(0) // Arguments required for command (0 for ignore)
            .action(ctx -> sendPrices(ctx, "USD"))
            .build();
    }

    public Ability eurReply() {
        return Ability.builder()
            .name("eur")
            .info("Самыая выгодная покупка EUR") // Necessary if you want it to be reported via /commands
            .privacy(PUBLIC)
            .locality(ALL)
            .input(0) // Arguments required for command (0 for ignore)
            .action(ctx -> sendPrices(ctx, "EUR"))
            .build();
    }

    public Ability plnReply() {
        return Ability.builder()
            .name("pln")
            .info("Самыая выгодная покупка PLN") // Necessary if you want it to be reported via /commands
            .privacy(PUBLIC)
            .locality(ALL)
            .input(0) // Arguments required for command (0 for ignore)
            .action(ctx -> sendPrices(ctx, "PLN"))
            .build();
    }

    public Ability rubReply() {
        return Ability.builder()
            .name("rub")
            .info("Самыая выгодная покупка RUB") // Necessary if you want it to be reported via /commands
            .privacy(PUBLIC)
            .locality(ALL)
            .input(0) // Arguments required for command (0 for ignore)
            .action(ctx -> sendPrices(ctx, "RUB"))
            .build();
    }

    private void sendPrices(MessageContext ctx, String countryCode) {
        while (CompanyProvider.isUpdatingNow) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Company bestBuyingCompany = COMPANY_PROVIDER.getBestBuyingCompanyFor(countryCode);
        Company bestSellingCompany = COMPANY_PROVIDER.getBestSellingCompanyFor(countryCode);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode("Markdown");
        sendMessage.disableWebPagePreview();
        sendMessage.setChatId(ctx.chatId());
        sendMessage.setText(prepareMessage(bestBuyingCompany, bestSellingCompany, countryCode));
        try {
            sender.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}