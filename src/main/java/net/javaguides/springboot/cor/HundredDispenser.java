package net.javaguides.springboot.cor;

import java.util.Objects;

public class HundredDispenser extends PaperCurrencyDispenser {
    public HundredDispenser() {
    }

    @Override
    public void dispense(PaperCurrency currency, StringBuilder messageBuilder) {
        if (Objects.nonNull(currency)) {
            int amount = currency.getAmount();
            int remainder = amount;
            if (amount >= 100) {
                int count = amount / 100;
                remainder = amount % 100;
                messageBuilder.append("Dispensing ")
                        .append(count)
                        .append(" 100$ currency note.\n");

                if (remainder > 0 && Objects.nonNull(this.nextDispenser)) {
                    this.nextDispenser.dispense(new PaperCurrency(remainder), messageBuilder);
                }
            }
        }
    }
}
