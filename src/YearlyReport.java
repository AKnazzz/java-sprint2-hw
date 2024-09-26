import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YearlyReport {

    private final List<YearReportByString> yearReport = new ArrayList<>(); // список для хранения объектов YearReportByString
    private final HashMap<Integer, Integer> yearLoss = new HashMap<>(); // хеш-таблица для убытков
    private final HashMap<Integer, Integer> yearProfit = new HashMap<>(); // хеш-таблица для прибыли
    private final int year;

    public YearlyReport(int year) {
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Год должен быть в диапазоне от 1900 до 2100.");
        }
        this.year = year;
    }


    public void yearlyReportLoad(String path) {
        yearReport.clear();
        yearLoss.clear();
        yearProfit.clear();

        Reader reader = new Reader();
        List<String> content = reader.readFileContents(path); // получаем строки из файла

        if (content.isEmpty()) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно файл не находится в нужной директории.");
            return;
        }

        System.out.println("Загрузка файла отчета: " + path);

        for (int i = 1; i < content.size(); i++) {
            String[] yearContent = content.get(i).split(","); // разделяем строки по запятым

            if (yearContent.length < 3) {
                System.out.println("Ошибка: недостаточно данных в строке: " + content.get(i));
                continue; // пропускаем строку с недостаточными данными
            }

            try {
                int month = Integer.parseInt(yearContent[0].trim()); // month
                int amount = Integer.parseInt(yearContent[1].trim()); // amount
                boolean isExpense = Boolean.parseBoolean(yearContent[2].trim()); // is_expense

                YearReportByString yearReportByString = new YearReportByString(month, amount, isExpense);
                yearReport.add(yearReportByString); // добавляем объект в список

                if (isExpense) {
                    yearLoss.put(month, amount); // добавляем в таблицу убытков
                } else {
                    yearProfit.put(month, amount); // добавляем в таблицу прибыли
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка при обработке строки: " + content.get(i) + ". " + e.getMessage());
            }
        }

        System.out.println("Годовой отчёт успешно загружен");
    }

    // далее методы по расчёту статистики

    public int getMonthLoss(int month) { // метод возвращения расходов конкретного месяца
        return yearLoss.getOrDefault(month, 0);
    }

    public int getMonthProfit(int month) { // метод возвращения доходов конкретного месяца
        return yearProfit.getOrDefault(month, 0);
    }

    public int findMonthSum(int month) { // метод нахождения прибыли за месяц (доходы минус расходы
        return getMonthProfit(month) - getMonthLoss(month);
    }

    public double getAveProfit() { // метод нахождения средней прибыли за год
        return calculateAverage(yearProfit);
    }

    public double getAveLoss() { // метод нахождения среднего расхода за год
        return calculateAverage(yearLoss);
    }

    private double calculateAverage(HashMap<Integer, Integer> data) {
        if (data.isEmpty()) return 0; // предотвращение деления на ноль
        int sum = data.values().stream().mapToInt(Integer::intValue).sum();
        return (double) sum / data.size(); // возвращаем среднее значение как double
    }

    public void printYearStats() {
        if (yearReport.isEmpty()) {
            System.out.println("Годовой отчет не загружен - считайте файл (команда 2).");
            return; // Выходим из метода, если отчет не загружен
        }

        System.out.println("Вывод статистики за " + year + " год: ");

        for (int month = 1; month <= 3; month++) {
            checkMonthData(month);
            System.out.println("Прибыль за " + month + " месяц составляет: " + findMonthSum(month) + " рублей.");
        }

        System.out.println("Средний расход за все месяцы в году составляет: " + getAveLoss() + " рублей.");
        System.out.println("Средний доход за все месяцы в году составляет: " + getAveProfit() + " рублей.");
    }

    private void checkMonthData(int month) {
        if (getYearLoss().get(month) == null) {
            System.out.println("В отчете за " + year + " год отсутствуют данные по расходам за " + month + " месяц.");
        }
        if (getYearProfit().get(month) == null) {
            System.out.println("В отчете за " + year + " год отсутствуют данные по доходам за " + month + " месяц.");
        }
    }

    public HashMap<Integer, Integer> getYearLoss() {
        return yearLoss;
    }

    public HashMap<Integer, Integer> getYearProfit() {
        return yearProfit;
    }

    public List<YearReportByString> getYearReport() {
        return yearReport;
    }

}
