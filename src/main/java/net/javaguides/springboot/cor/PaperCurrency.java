package net.javaguides.springboot.cor;

public class PaperCurrency {
    protected int amount;

    public PaperCurrency(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
