import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            String line = scanner.nextLine();
            if (!"".equals(line)) {
                if ("/exit".equals(line)) {
                    System.out.println("Bye!");
                    exit = true;
                } else if ("/help".equals(line)) {
                    System.out.println("The program calculates the sum of numbers");
                } else {

                    String[] expression = line.split("\\s+");
                    int result = calculate(expression);
                    System.out.println(result);

                }
            }

        }
        scanner.close();

    }

    public static int calculate(String[] expression) {
        if (expression.length == 1) {
            return Integer.parseInt(expression[0]);
        }

        int sum = 0;

        for (int i = 0; i < expression.length; i++) {
            if (expression[i].matches("\\d+")) {
                sum += Integer.parseInt(expression[i]);
            } else if (expression[i].matches("[-]\\d")) {
                sum += -1 * Integer.parseInt(expression[i].substring(1));
            } else if (expression[i].matches("[+]\\d")) {
                sum += Integer.parseInt(expression[i].substring(1));
            } else if (expression[i].matches("[+]+")){
                sum += Integer.parseInt(expression[i + 1]);
                i++;
            } else if (expression[i].matches("[-]+")){
                if (expression[i].length() % 2 == 0) {
                    sum += Integer.parseInt(expression[i + 1]);
                } else {
                    sum += -1 * Integer.parseInt(expression[i + 1]);
                }
                i++;
            }
        }

        return sum;
    }
}
