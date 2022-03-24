import java.util.Arrays;
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
                    
                    String[] inputs = line.split("\\s+");
                    int sum = Arrays.stream(inputs).mapToInt(Integer::parseInt).sum();
                    System.out.println(sum);
                                        
                }
            } 
            
        }
        scanner.close();
        
    }
}