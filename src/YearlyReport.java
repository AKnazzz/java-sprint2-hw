import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class YearlyReport { // класс для данных годового отчёта

    public ArrayList<YearReportByString> yearReport = new ArrayList<>(); // список для сохранения объектов в которых хранятся объекты класса YearReportByString
    public HashMap<Integer, Integer> getYearLoss = new HashMap<>(); // хеш-таблица для сохранения данных по убыткам
    public HashMap<Integer, Integer> getYearProfit = new HashMap<>(); // хеш-таблица для сохранения данных по прибыли
    int year;

    public YearlyReport(int year) { // конструктор класса
        this.year = year;
    }

    public void yearlyReportLoad(String path) { // метод считывания и обработки файла, а также сохранение его значений в соответствующие объекты
        Reader reader = new Reader();
        List<String> content = reader.readFileContents(path); // получаем список со строчками из файла
        if (!content.isEmpty()) {                               // проверка загрузки файла
            System.out.println("Загрузка файла отчета: " + path);

            for (int i = 1; i < content.size(); i++) {
                String[] yearContent = content.get(i).split(","); // получаем массив на каждую из строк (исключая нулевую строку), разделяем по запятым
                int month = Integer.parseInt(yearContent[0]); // month [01] *******
                int amount = Integer.parseInt(yearContent[1]); // amount [1593150]
                boolean isExpense = Boolean.parseBoolean(yearContent[2]); // is_expense [false]
                YearReportByString yearReportByString = new YearReportByString(month, amount, isExpense); // заполняем объект класса YearReportByString
                yearReport.add(yearReportByString); // и сохраняем его в список
                if (isExpense) {
                    getYearLoss.put(month, amount); // добавляем в таблицу позиции с приходом средств
                } else {
                    getYearProfit.put(month, amount); // добавляем в таблицу позиции с расходом средств
                }
            }

            System.out.println("Годовой отчёт успешно загружен"); // в результате считываем файл и получаем заполненный список по каждой строке из файла
        } else {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно файл не находится в нужной директории.");
        }
    }

    // далее методы по расчёту статистики

    public int getMonthLoss(int month) { // метод возвращения расходов конкретного месяца
        return getYearLoss.getOrDefault(month, 0);
    }

    public int getMonthProfit(int month) { // метод возвращения доходов конкретного месяца

        return getYearProfit.getOrDefault(month, 0);
    }

    public int findMonthSum(int month) {     // метод нахождения прибыли за месяц (доходы минус расходы)

        return getMonthProfit(month) - getMonthLoss(month);
    }

    public int getAveProfit() {               // метод нахождения средней прибыли за год
        int sum = 0;
        for (int month : getYearProfit.keySet()) {
            sum += getYearProfit.get(month);
        }
        return sum / getYearProfit.size();
    }


    public int getAveLoss() {                 // метод нахождения среднего расхода за год
        int sum = 0;
        for (int month : getYearLoss.keySet()) {
            sum += getYearLoss.get(month);
        }
        return sum / getYearLoss.size();
    }


    public void printYearStats() {                                          // метод для вывода статистики за год согласно ТЗ
        if (!yearReport.isEmpty()) {
            System.out.println("Вывод статистики за " + year + " год: ");
            for (int i = 1; i <= ((yearReport.size()) / 2); i++) {          // в цикле можно указать кол-во месяцев 3 (можно кол-во элементов yearReport/2 = на каждый месяц по две записи)
                int month = i;
                System.out.println("Прибыль за " + month + " месяц составляет: " + findMonthSum(month) + " рублей.");
            }
            System.out.println("Средний расход за все месяцы в году составляет: " + getAveLoss() + " рублей.");
            System.out.println("Средний доход за все месяцы в году составляет: " + getAveProfit() + " рублей.");
        } else {
            System.out.println("Годовой отчет не загружен - считайте файл (команда 2).");
        }
    }
}
