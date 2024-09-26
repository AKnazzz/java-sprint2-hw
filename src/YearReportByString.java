public class YearReportByString {
    // Поля класса
    private final int month;            // Номер месяца (1-12)
    private final int amount;           // Сумма
    private final boolean isExpense;     // Трата (true) или доход (false)

    // Конструктор класса
    public YearReportByString(int month, int amount, boolean isExpense) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.month = month;
        this.amount = amount;
        this.isExpense = isExpense;
    }

    // Геттеры для доступа к полям
    public int getMonth() {
        return month;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isExpense() {
        return isExpense;
    }

    // Метод для получения информации о записи
    @Override
    public String toString() {
        return String.format("Month: %d, Amount: %d, Is Expense: %b", month, amount, isExpense);
    }
}
