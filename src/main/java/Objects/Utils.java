package Objects;

import Objects.Enums.TextColors;

import java.util.Scanner;

import static Objects.Enums.TextColors.RESET;
import static Objects.Enums.TextColors.GREEN;

public class Utils {


    private static final Scanner scanner = new Scanner(System.in);

    public static void pressAnyKey() {
        System.out.print("Press enter to continue.\r");
        scanner.nextLine();
    }

    public void showText(String text, TextColors color) {
        System.out.println(color.value + text + RESET.value);
    }

    public static void showText(String text) {
        System.out.println(text);
    }

    public static void showTextInLine(String text, TextColors color) {
        System.out.print(color.value + text + RESET.value);
    }

    public static void showTextInLine(String text) {
        System.out.print(text);
    }

    public static int getDollarResponse() {
        int value;
        String response;
        while (true) {
            try {
                response = scanner.nextLine();
                response = response.replace("$", "");
                response = response.replace(".00","");
                value = Integer.parseInt(response);
                if (value <= 0) {
                    showText("Please enter an amount greater than zero.");
                    showTextInLine("$", GREEN);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                showText("Please enter a whole dollar value.");
                showTextInLine("$", GREEN);
            }
        }
        return value;
    }

    public static int getNumericalResponse() {
        int value;
        String response;
        while (true) {
            try {
                response = scanner.nextLine();
                value = Integer.parseInt(response);
                if (value <= 0) {
                    showText("Please enter one of the available options.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                showText("Please enter one of the available options.");
            }
        }
        return value;
    }

}
