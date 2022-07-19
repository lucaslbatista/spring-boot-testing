package net.javaguides.springboot.cor;

import java.util.Objects;

public class ATMWithdrawal {
    protected static PaperCurrencyDispenser hundredDispenser = new HundredDispenser();
    protected static PaperCurrencyDispenser fiftyDispenser = new FiftyDispenser();
    protected static PaperCurrencyDispenser twentyDispenser = new TwentyDispenser();
    protected static PaperCurrencyDispenser tenDispenser = new TenDispenser();
    protected static PaperCurrencyDispenser dispenserChain;

    static {
        hundredDispenser.setNextDispenser(fiftyDispenser);
        fiftyDispenser.setNextDispenser(twentyDispenser);
        twentyDispenser.setNextDispenser(tenDispenser);
        dispenserChain = hundredDispenser;
    }

    public static String withdraw(PaperCurrency currency) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Objects.nonNull(currency)) {
            dispenserChain.dispense(currency, stringBuilder);
        }

        return stringBuilder.toString();
    }
}
