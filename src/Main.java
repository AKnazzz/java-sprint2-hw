import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        YearlyReport yearlyManager = new YearlyReport(2021);
        MonthlyReport monthlyManager = new MonthlyReport();
        Checker checker = new Checker();

        System.out.println("Программа <АВТОМАТИЗАЦИЯ БУХГАЛТЕРИИ 1.0> запущена");

        printMenu();
        int userInput = scanner.nextInt();                                          // считываем выбор пользователя

        while (userInput != 0) {

            if (userInput == 1) {                                                   // ("1 - Считать все месячные отчёты");
                monthlyManager.loadAllMonth();

            } else if (userInput == 2) {                                            // ("2 - Считать годовой отчёт");
                yearlyManager.yearlyReportLoad("resources/y.2021.csv");

            } else if (userInput == 3) {                                            // ("3 - Сверить отчёты");

                checker.check(monthlyManager, yearlyManager);

            } else if (userInput == 4) {                                            // ("4 - Вывести информацию о всех месячных отчётах");
                if (monthlyManager.checkLoad()) {
                    for (int i = 1; i <= 3; i++) {
                        monthlyManager.printMonthStats(i);
                    }
                }

            } else if (userInput == 5) {                                            // ("5 - Вывести информацию о годовом отчёте");
                yearlyManager.printYearStats();

            } else {
                System.out.println("Введена некорректная команда.");
            }
            printMenu();                                                    // печатаем меню ещё раз перед завершением предыдущего действия
            userInput = scanner.nextInt();                                  // повторное считывание данных от пользователя
        }
        System.out.println("Программа завершена!");
    }

    public static void printMenu() {                                        // метод для вывода меню
        System.out.println(" ");
        System.out.println("Уточните пожалуйста, что Вы хотите сделать? ");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Закончить работу");
    }
}