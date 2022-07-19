package net.javaguides.springboot.cor;

import java.util.Objects;

public class FiftyDispenser extends PaperCurrencyDispenser {
    @Override
    public void dispense(PaperCurrency currency, StringBuilder messageBuilder) {
        if (Objects.nonNull(currency)) {
            int amount = currency.getAmount();
            int remainder = amount;
            if (amount >= 50) {
                int count = amount / 50;
                remainder = amount % 50;
                messageBuilder.append("Dispensing ")
                        .append(count)
                        .append(" 50$ currency note.\n");

                if (remainder > 0 && Objects.nonNull(this.nextDispenser)) {
                    this.nextDispenser.dispense(new PaperCurrency(remainder), messageBuilder);
                }
            }
        }
    }
}
