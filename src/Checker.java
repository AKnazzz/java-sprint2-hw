public class Checker {                                                                       // класс для сверки отчетов
    public void check(MonthlyReport monthlyReport, YearlyReport yearlyReport) {            //метод проверки, что файлы считаны и сохранены


        if (yearlyReport.yearReport.isEmpty()) {                                             // условие загрузки годового файла
            System.out.println("Годовой отчет не загружен - считайте файл (команда 2).");
            return;
        }
        if (monthlyReport.monthReportData.isEmpty()) {                                      // условие загрузки месячных файлов
            System.out.println("Отчеты не загружены - считайте файлы (команда 1).");
            return;
        }

        boolean errors = false;                                                                    // переменная для сохранения кол-ва ошибок при сверке

        // методы для расчета доходов и расходов уже были созданы внутри соответствующих классов - используем их для сравнения сумм
        // используем эти методы в цикле - получаем значения по каждому месяцу из месячного и годового отчета

        for (int i = 1; i <= 3; i++) {                                                      //цикл для сравнения
            boolean isProfitEqual = (monthlyReport.monthExpenses(monthlyReport.topSaleName(i, false)).equals(yearlyReport.getMonthProfit(i)));
            boolean isLossEqual = (monthlyReport.monthExpenses(monthlyReport.topSaleName(i, true)).equals(yearlyReport.getMonthLoss(i)));

            if (!isLossEqual) {
                System.out.println("ОШИБКА! Расхождение по расходам в месяце №" + i);
                errors = true;
            }
            if (!isProfitEqual) {
                System.out.println("ОШИБКА! Расхождение по доходам в месяце №" + i);
                errors = true;
            }
        }
        if (errors == false) {
            System.out.println("Отчеты успешно сверены - расхождений в данных не обнаружено !");
        }
        errors = false;                                                                         // возвращаем исходное значение переменной (чтоб при повторном вызове метод)
    }

}
