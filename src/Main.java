import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InvalidOperatorException, InputMismatchException {
        Scanner expression = new Scanner(System.in);
        String currentLine = expression.nextLine();
        try {
            if (!inputMismatch(currentLine)){
                System.out.println(calc(currentLine));
            } else {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException | NegativeRomanNumberException e) {
            e.printStackTrace();
        }
    }
    public static String calc(String input) throws InvalidOperatorException, NegativeRomanNumberException {
        String[] variables = arrayOfValues(input);
        int result = 0;
        if (isArabicNumber(variables[0])){
            result = switch (variables[1]) {
                case "+" -> Integer.parseInt(variables[0]) + Integer.parseInt(variables[2]);
                case "-" -> Integer.parseInt(variables[0]) - Integer.parseInt(variables[2]);
                case "/" -> Integer.parseInt(variables[0]) / Integer.parseInt(variables[2]);
                case "*" -> Integer.parseInt(variables[0]) * Integer.parseInt(variables[2]);
                default -> throw new InvalidOperatorException("Введен не верный оператор");
            };
            return String.valueOf(result);
        } else {
            result = switch (variables[1]) {
                case "+" -> romanToArabic(variables[0]) + romanToArabic(variables[2]);
                case "-" -> romanToArabic(variables[0]) - romanToArabic(variables[2]);
                case "/" -> romanToArabic(variables[0]) / romanToArabic(variables[2]);
                case "*" -> romanToArabic(variables[0]) * romanToArabic(variables[2]);
                default -> throw new InvalidOperatorException("Введен не верный оператор");
            };
            if (result > 0) {
                return arabicToRoman(result);
            } else {
                throw new NegativeRomanNumberException("Результат не может быть отрицательным или нулевым");
            }
        }
    }
    static boolean isArabicNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    static boolean isRomanNumber(String input) {
        String romanNumeralPattern = "^(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$";
        return input.matches(romanNumeralPattern);
    }
    static int romanToArabic(String romanNumber) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int arabicNumber = 0;
        int previousValue = 0;

        for (int i = romanNumber.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(romanNumber.charAt(i));

            if (currentValue < previousValue) {
                arabicNumber -= currentValue;
            } else {
                arabicNumber += currentValue;
            }

            previousValue = currentValue;
        }

        return arabicNumber;
    }
    static String arabicToRoman(int arabicNumber) {
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder romanNumber = new StringBuilder();

        for (int i = 0; i < arabicValues.length; i++) {
            while (arabicNumber >= arabicValues[i]) {
                romanNumber.append(romanSymbols[i]);
                arabicNumber -= arabicValues[i];
            }
        }

        return romanNumber.toString();
    }
    static String[] arrayOfValues(String input) {
        return input.split(" ");
    }
    static boolean inputMismatch(String input) {
        String[] variables = arrayOfValues(input);
        if (variables.length != 3) {
            return true;
        } else if (isArabicNumber(variables[0]) & isArabicNumber(variables[2])) {
            if (1 <= Integer.parseInt(variables[0]) & Integer.parseInt(variables[0]) <= 10
                & 1 <= Integer.parseInt(variables[2]) & Integer.parseInt(variables[2]) <= 10) {
                return false;
            } else {
                return true;
            }
        } else if (isRomanNumber(variables[0]) & isRomanNumber(variables[2])) {
            if (1 <= romanToArabic(variables[0]) & romanToArabic(variables[0]) <= 10
                & 1 <= romanToArabic(variables[2]) & romanToArabic(variables[2]) <= 10) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}