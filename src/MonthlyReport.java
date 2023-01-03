import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport { // класс = для хранения данных по месячным отчётам
    public ArrayList<MonthReportByString> monthReportData = new ArrayList<>(); // список для сохранения объектов в которых хранятся данные за каждый месяц

    public void loadFile(Integer month, String path) { // метод считывания и обработки файла одного месяца, сохранение его значений в соответствующий объект

        Reader reader = new Reader(); // вызываем объект класса Reader для считывания файлов

        List<String> content = reader.readFileContents(path); // получаем список со строчками из файла item_name,is_expense,quantity,sum_of_one


        if (!content.isEmpty()) {               // проверка, что файл заполнен данными
            System.out.println("Скачан файл " + Path.of(path));
        } else {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
        }


        for (int i = 1; i < content.size(); i++) {
            String[] monthContent = content.get(i).split(","); // получаем массив на каждую из строк (исключая нулевую строку), разделяем по запятым
            String itemName = monthContent[0]; // item_name [коньки]
            int quantity = Integer.parseInt(monthContent[2]); // quantity [50]
            int sumOfOne = Integer.parseInt(monthContent[3]); // sum_of_one [2000]
            boolean isExpense = Boolean.parseBoolean(monthContent[1]); // is_expense [TRUE]
            MonthReportByString monthReportByString = new MonthReportByString(itemName, isExpense, quantity, sumOfOne, month);
            monthReportData.add(monthReportByString);
        }
    }

    public void loadAllMonth() {                    // метод для извлечения и сохранения данных за три месяц
        for (int i = 1; i <= 3; i++) {
            String path = "resources/m.20210" + i + ".csv";
            loadFile(i, path);
        }
    }


    // далее методы по расчёту статистики

    public HashMap<String, Integer> topSaleName(int month, boolean isExpense) { // метод поиска наименования топового товара за месяц (приход - FALSE,расход - FALSE)
        HashMap<String, Integer> freqs = new HashMap<>(); // создаём мапу с полями - [товар], [сумма поступлений]
        if (!isExpense) {
            for (MonthReportByString monthReportByString : monthReportData) { // отсеиваем месяцы которые нас не интересуют
                if (monthReportByString.month != month) {
                    continue;
                }
                if (monthReportByString.isExpense) { // отсеиваем позиции с расходами
                    continue;
                }
                freqs.put(monthReportByString.itemName, freqs.getOrDefault(monthReportByString.itemName, 0) +
                        (monthReportByString.quantity * monthReportByString.sumOfOne));
                //заполняем мапу значениями товара и сумму (значение по ключу которое там уже было или 0 + сумму поступлений = цена*кол-во)
            }
        } else {
            for (MonthReportByString monthReportByString : monthReportData) {
                if (monthReportByString.month != month) {
                    continue;
                }
                if (!monthReportByString.isExpense) {
                    continue;
                }
                freqs.put(monthReportByString.itemName, freqs.getOrDefault(monthReportByString.itemName, 0) +
                        (monthReportByString.quantity * monthReportByString.sumOfOne));
            }
        }
        return freqs; // получаем мапу в которой, в зависимости от начальных условий будут все поступления или расходы
    }


    public Integer monthExpenses(HashMap<String, Integer> freqs) {          // метод подсчета суммы значений мапы
        int sum = 0;
        for (int order : freqs.values()) {
            sum += order;
        }
        return sum;
    }

    public String topName(HashMap<String, Integer> freqs) {                 // метод получения имени самого прибыльного или самого расходного продукта
        String topName = null;                                              //(в зависимости от начальных условий)
        for (String saleName : freqs.keySet()) {
            if (topName == null) {
                topName = saleName;
                continue;
            }
            if (freqs.get(topName) < freqs.get(saleName)) {
                topName = saleName;
            }
        }
        return topName;                                                    // возвращаем имя самого дорогого/ расходного продукта
    }


    public int topOrder(HashMap<String, Integer> freqs) {                  // метод получения имени самого дорогого/расходного продукта
        String topName = null;                                             //(в зависимости от начальных условий)
        for (String saleName : freqs.keySet()) {
            if (topName == null) {
                topName = saleName;
                continue;
            }
            if (freqs.get(topName) < freqs.get(saleName)) {
                topName = saleName;
            }
        }
        return freqs.get(topName);                                         // возвращаем стоимость самого дорогого/ расходного продукта
    }


    public boolean checkLoad() {                                           // метод проверки перед выводом статистики, что отчеты загружены
        if (monthReportData.isEmpty()) {
            System.out.println("Отчеты не загружены - считайте файлы (команда 1).");
            return false;
        } else {
            return true;
        }
    }


    public void printMonthStats(Integer month) {                            // метод вывода статистики за каждый месяц

        System.out.println("Вывод статистики за " + month + " месяц: ");
        System.out.print("Самый прибыльный продукт в " + month + " месяце: " + topName(topSaleName(month, false)));
        System.out.println(". Сумма продаж : " + topOrder(topSaleName(month, false)) + " рублей.");
        System.out.print("Самая большая расходы на продукт: " + topName(topSaleName(month, true)) + " рублей.");
        System.out.println("Сумма израсходованных средств: " + topOrder(topSaleName(month, true)) + " рублей.");

    }

}
