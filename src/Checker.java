public class Checker { // Класс для сверки отчетов

    public void check(MonthlyReport monthlyReport, YearlyReport yearlyReport) { // Метод проверки, что файлы считаны и сохранены

        // Проверяем загрузку годового отчета
        if (yearlyReport.getYearReport().isEmpty()) {
            System.out.println("Годовой отчет не загружен - считайте файл (команда 2).");
            return;
        }

        // Проверяем загрузку месячных отчетов
        if (monthlyReport.getMonthReportData().isEmpty()) {
            System.out.println("Отчеты не загружены - считайте файлы (команда 1).");
            return;
        }

        boolean hasErrors = false; // Переменная для отслеживания ошибок при сверке

        // Сравниваем данные за первые три месяца
        for (int month = 1; month <= 3; month++) {
            boolean isProfitEqual = monthlyReport.monthExpenses(monthlyReport.topSaleName(month, false)).equals(yearlyReport.getMonthProfit(month));
            boolean isLossEqual = monthlyReport.monthExpenses(monthlyReport.topSaleName(month, true)).equals(yearlyReport.getMonthLoss(month));

            if (!isLossEqual) {
                System.out.println("ОШИБКА! Расхождение по расходам в месяце №" + month);
                hasErrors = true;
            }
            if (!isProfitEqual) {
                System.out.println("ОШИБКА! Расхождение по доходам в месяце №" + month);
                hasErrors = true;
            }
        }

        // Выводим результат проверки
        if (!hasErrors) {
            System.out.println("Отчеты успешно сверены - расхождений в данных не обнаружено!");
        }
    }
}
