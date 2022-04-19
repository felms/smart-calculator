import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        Calculator calculator = new Calculator();

        while (!exit) {
            String input = scanner.nextLine().trim();
            if (!"".equals(input)) {
                if ("/exit".equals(input)) {
                    System.out.println("Bye!");
                    exit = true;
                } else if ("/help".equals(input)) {
                    System.out.println("The program calculates the sum of numbers");
                } else if (input.matches("\\/.*")) {
                    System.out.println("Unknown command");
                } else {

                    try{
                        calculator.calculate(input);
                    }catch(NumberFormatException nfe) {
                        System.out.println("Invalid expression");
                    }
                }
            }

        }
        scanner.close();
    }

}
