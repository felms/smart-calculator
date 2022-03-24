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
                } else {
                    
                    String[] inputs = line.split("\\s+");
                    int a = Integer.parseInt(inputs[0]);
                    if (inputs.length == 1) {
                        System.out.println(a);
                    } else if (inputs.length == 2) {
                        int b = Integer.parseInt(inputs[1]);
                        System.out.println(a + b);
                    }
                    
                }
            } 
            
        }
        scanner.close();
        
    }
}