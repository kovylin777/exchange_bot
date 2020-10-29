import models.Price;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
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
        return 267850708; // Your ID here
    }

    public Ability saysHelloWorld() {
        return Ability.builder()
            .name("buy") // Name and command (/hello)
            .info("Самыая выгодная покупка USD") // Necessary if you want it to be reported via /commands
            .privacy(PUBLIC)  // Choose from Privacy Class (Public, Admin, Creator)
            .locality(ALL) // Choose from Locality enum Class (User, Group, PUBLIC)
            .input(0) // Arguments required for command (0 for ignore)
            .action(ctx -> {
          /*
          ctx has the following main fields that you can utilize:
          - ctx.update() -> the actual Telegram update from the basic API
          - ctx.user() -> the user behind the update
          - ctx.firstArg()/secondArg()/thirdArg() -> quick accessors for message arguments (if any)
          - ctx.arguments() -> all arguments
          - ctx.chatId() -> the chat where the update has emerged
          NOTE that chat ID and user are fetched no matter what the update carries.
          If the update does not have a message, but it has a callback query, the chatId and user will be fetched from that query.
           */
                // Custom sender implementation
                PRICE_PROVIDER.setPrices();
                Price bestBuyingPrice = PRICE_PROVIDER.getBestBuyingPrice();
                Price bestSellingPrice = PRICE_PROVIDER.getBestSellingPrice();
                sender.send("Самый выгодный курс покупки USD:\n" +
                    bestBuyingPrice.getName() + ": " + bestBuyingPrice.getBuyingRate(), ctx.chatId());
                sender.send("Самый выгодный курс продажи USD:\n" +
                    bestSellingPrice.getName() + ": " + bestSellingPrice.getSellingRate(), ctx.chatId());
            })
            .build();
    }
}