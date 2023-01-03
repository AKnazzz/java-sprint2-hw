public class MonthReportByString {              // класс для хранения данных строки отчета месяца

    //item_name,is_expense,quantity,sum_of_one - формат строки из отчёта - используем как параметры для класса
    public String itemName;                     // название товара
    public Boolean isExpense;                   // трата (TRUE) или доход (FALSE)
    public Integer quantity;                    // количество закупленного или проданного товара
    public Integer sumOfOne;                    // стоимость одной единицы товара
    public Integer month;                       // добавляем данные по номеру месяца

    public MonthReportByString(String itemName, Boolean isExpense, Integer quantity, Integer sumOfOne, Integer month) { // конструктор класса
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
        this.month = month;
    }


}
