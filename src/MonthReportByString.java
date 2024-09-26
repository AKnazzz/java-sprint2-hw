public class MonthReportByString {
    // Поля класса
    private final String itemName;      // Название товара
    private final boolean isExpense;     // Трата (true) или доход (false)
    private final int quantity;          // Количество закупленного или проданного товара
    private final int sumOfOne;          // Стоимость одной единицы товара
    private final int month;             // Номер месяца

    // Конструктор класса
    public MonthReportByString(String itemName, boolean isExpense, int quantity, int sumOfOne, int month) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
        this.month = month;
    }

    // Геттеры для доступа к полям
    public String getItemName() {
        return itemName;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSumOfOne() {
        return sumOfOne;
    }

    public int getMonth() {
        return month;
    }

    // Метод для получения общей стоимости
    public int getTotalCost() {
        return quantity * sumOfOne;
    }

    @Override
    public String toString() {
        return String.format("Item: %s, Expense: %b, Quantity: %d, Sum of One: %d, Month: %d",
                itemName, isExpense, quantity, sumOfOne, month);
    }
}
