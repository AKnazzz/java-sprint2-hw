import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthlyReport {

    private List<MonthReportByString> monthReportData = new ArrayList<>();

    public List<MonthReportByString> getMonthReportData() {
        return monthReportData;
    }

    public void loadFile(int month, String path) {
        Reader reader = new Reader();
        List<String> content = reader.readFileContents(path);

        if (content.isEmpty()) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return;
        }

        System.out.println("Скачан файл " + Path.of(path));

        for (int i = 1; i < content.size(); i++) {
            String[] monthContent = content.get(i).split(",");
            if (monthContent.length < 4) {
                System.out.println("Неверный формат строки: " + content.get(i));
                continue;
            }

            try {
                MonthReportByString monthReportByString = parseMonthReport(monthContent, month);
                monthReportData.add(monthReportByString);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Ошибка при парсинге данных: " + e.getMessage());
            }
        }
    }

    private MonthReportByString parseMonthReport(String[] monthContent, int month) {
        String itemName = monthContent[0];
        boolean isExpense = Boolean.parseBoolean(monthContent[1]);
        int quantity = Integer.parseInt(monthContent[2]);
        int sumOfOne = Integer.parseInt(monthContent[3]);

        return new MonthReportByString(itemName, isExpense, quantity, sumOfOne, month);
    }

    public void loadAllMonths() {
        monthReportData.clear();
        for (int i = 1; i <= 3; i++) {
            String path = "resources/m.20210" + i + ".csv";
            loadFile(i, path);
        }
    }

    public HashMap<String, Integer> topSaleName(int month, boolean isExpense) {
        HashMap<String, Integer> freqs = new HashMap<>();

        for (MonthReportByString monthReport : monthReportData) {
            if (monthReport.getMonth() == month && monthReport.isExpense() == isExpense) {
                String itemName = monthReport.getItemName();
                int totalValue = monthReport.getQuantity() * monthReport.getSumOfOne();
                freqs.put(itemName, freqs.getOrDefault(itemName, 0) + totalValue);
            }
        }
        return freqs;
    }

    public Integer monthExpenses(HashMap<String, Integer> freqs) {
        return freqs.values().stream().mapToInt(Integer::intValue).sum();
    }

    public String topName(HashMap<String, Integer> freqs) {
        return freqs.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public int getTopProductCost(HashMap<String, Integer> freqs) {
        String topProductName = topName(freqs);
        return freqs.getOrDefault(topProductName, 0); // Используем getOrDefault для предотвращения NullPointerException
    }

    // метод общей проверки перед выводом статистики
    public boolean checkLoad() {
        boolean isLoaded = !monthReportData.isEmpty();
        if (!isLoaded) {
            System.out.println("Отчеты не загружены - считайте файлы (команда 1).");
        }
        return isLoaded;
    }

    // метод проверки загрузки/заполнения данными отчета каждого месяца
    public boolean checkLoadOneReport(int month) {
        return !topSaleName(month, true).isEmpty() || !topSaleName(month, false).isEmpty(); // Упрощение логики
    }

    public void printMonthStats(Integer month) {
        if (checkLoadOneReport(month)) {
            System.out.println("Вывод статистики за " + month + " месяц: ");

            HashMap<String, Integer> incomeStats = topSaleName(month, false);
            HashMap<String, Integer> expenseStats = topSaleName(month, true);

            if (incomeStats.isEmpty()) {
                System.out.println("Внимание! В отчете за месяц " + month + " отсутствуют данные по расходам.");
                System.out.print("Самый прибыльный продукт в " + month + " месяце: " + topName(expenseStats));
                System.out.println(". Сумма продаж : " + getTopProductCost(expenseStats) + " рублей.");
            } else if (expenseStats.isEmpty()) {
                System.out.println("Внимание! В отчете за месяц " + month + " отсутствуют данные по доходам.");
                System.out.print("Самая большая расходы на продукт: " + topName(incomeStats));
                System.out.println(". Сумма израсходованных средств: " + getTopProductCost(incomeStats) + " рублей.");
            } else {
                System.out.print("Самый прибыльный продукт в " + month + " месяце: " + topName(incomeStats));
                System.out.println(". Сумма продаж : " + getTopProductCost(incomeStats) + " рублей.");
                System.out.print("Самая большая расходы на продукт: " + topName(expenseStats));
                System.out.println(". Сумма израсходованных средств: " + getTopProductCost(expenseStats) + " рублей.");
            }
        } else {
            System.out.println("Внимание! Файл отчета за " + month + " месяц отсутствует или не заполнен!");
        }
    }

}
