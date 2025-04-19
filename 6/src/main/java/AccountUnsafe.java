class AccountUnsafe implements Account {
    private Integer balance;

    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
//    public synchronized Integer getBalance() {
        return balance;
    }

    @Override
    public void withdraw(Integer amount) {
//    public synchronized void withdraw(Integer amount) {
        balance -= amount;
    }
}