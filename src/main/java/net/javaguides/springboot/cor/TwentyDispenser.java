package net.javaguides.springboot.cor;

import java.util.Objects;

public class TwentyDispenser extends PaperCurrencyDispenser {
    @Override
    public void dispense(PaperCurrency currency, StringBuilder messageBuilder) {
        if (Objects.nonNull(currency)) {
            int amount = currency.getAmount();
            int remainder = amount;
            if (amount >= 20) {
                int count = amount / 20;
                remainder = amount % 20;
                messageBuilder.append("Dispensing ")
                        .append(count)
                        .append(" 20$ currency note.\n");

                if (remainder > 0 && Objects.nonNull(this.nextDispenser)) {
                    this.nextDispenser.dispense(new PaperCurrency(remainder), messageBuilder);
                }
            }
        }
    }
}
