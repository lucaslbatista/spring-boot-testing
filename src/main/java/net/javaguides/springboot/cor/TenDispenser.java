package net.javaguides.springboot.cor;

import java.util.Objects;

public class TenDispenser extends PaperCurrencyDispenser {
    @Override
    public void dispense(PaperCurrency currency, StringBuilder messageBuilder) {
        if (Objects.nonNull(currency)) {
            int amount = currency.getAmount();
            int remainder = amount;
            if (amount >= 10) {
                int count = amount / 10;
                remainder = amount % 10;
                messageBuilder.append("Dispensing ")
                        .append(count)
                        .append(" 10$ currency note.\n");

                if (remainder > 0 && Objects.nonNull(this.nextDispenser)) {
                    this.nextDispenser.dispense(new PaperCurrency(remainder), messageBuilder);
                }
            }
        }
    }
}
