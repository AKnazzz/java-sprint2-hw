import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final int DEFAULT_YEAR = 2021;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        YearlyReport yearlyManager = new YearlyReport(DEFAULT_YEAR);
        MonthlyReport monthlyManager = new MonthlyReport();
        Checker checker = new Checker();

        System.out.println("Программа <АВТОМАТИЗАЦИЯ БУХГАЛТЕРИИ 1.0> запущена");

        int userInput = -1;
        while (userInput != 0) {
            printMenu();
            try {
                userInput = scanner.nextInt(); // считываем выбор пользователя
                handleUserInput(userInput, monthlyManager, yearlyManager, checker);
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число.");
                scanner.next(); // очистка некорректного ввода
            }
        }
        System.out.println("Программа завершена!");
        scanner.close(); // Закрываем Scanner
    }

    public static void printMenu() {
        System.out.println(" ");
        System.out.println("Уточните пожалуйста, что Вы хотите сделать? ");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Закончить работу");
    }

    private static void handleUserInput(int userInput, MonthlyReport monthlyManager, YearlyReport yearlyManager, Checker checker) {
        switch (userInput) {
            case 1:
                monthlyManager.loadAllMonths();
                break;
            case 2:
                yearlyManager.yearlyReportLoad("resources/y." + DEFAULT_YEAR + ".csv");
                break;
            case 3:
                checker.check(monthlyManager, yearlyManager);
                break;
            case 4:
                if (monthlyManager.checkLoad()) {
                    for (int i = 1; i <= 12; i++) { // Исправлено на 12 месяцев
                        monthlyManager.printMonthStats(i);
                    }
                }
                break;
            case 5:
                yearlyManager.printYearStats();
                break;
            case 0:
                break; // Завершение программы
            default:
                System.out.println("Введена некорректная команда.");
                break;
        }
    }
}
