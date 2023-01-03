public class YearReportByString {   // класс для хранения данных строки отчета года

    // month,amount,is_expense - формат строки из отчёта - используем как параметры для класса
    Integer month;                  // номер месяца
    Integer amount;                 // сумма
    Boolean isExpense;              // трата (TRUE) или доход (FALSE)

    public YearReportByString(Integer month, Integer amount, Boolean isExpense) { // конструктор класса
        this.month = month;
        this.amount = amount;
        this.isExpense = isExpense;
    }
}