import java.util.HashMap;
import java.util.Map;

public class Calculator {

    private Map<String, Integer> variables;

    public Calculator() {
        this.variables = new HashMap<>();
    }

    public void calculate(String expr) {

        if (expr.matches(".*\\s*=\\s*.*")) {                // Caso seja uma atribuição ou cálculo com variáveis
            calculateWithVariables(expr);
        } else if (expr.matches("[a-zA-Z]+")) {         // Consulta do valor de uma variável
            Integer varValue = this.variables.get(expr);
            if (varValue == null) {
                System.out.println("Unknown variable");
            } else {
                System.out.println(varValue);
            }

        } else if (expr.matches("\\d+")) {              // Input de um inteiro apenas
            System.out.println(Integer.parseInt(expr));
        } else {                                              // Cálculo de soma ou subtração de inteiros
            String[] expression = expr.split("\\s+");
            int sum = 0;

            for (int i = 0; i < expression.length; i++) {

                if (expression[i].matches("[a-zA-Z]+")) {        // Se durante a execução é lida uma váriável
                    calculateWithVariables(expr);             // o cálculo é passado para o método que sabe como
                    return;                                   // processá-las
                }

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
            System.out.println(sum);
        }


    }

    // Cálculos onde se utilizam variáveis
    private void calculateWithVariables(String input) {

        if (input.matches(".*\\s*=\\s*.*")){
            String[] entry = input.trim().split("\\s*=\\s*");
            if (entry.length != 2
                    || !entry[1].matches("(\\b[a-zA-Z]+\\b)|(\\b\\d+\\b)"))  { // Testa para inputs inválidos
                System.out.println("Invalid assignment");
            }
            else if (!entry[0].matches("\\b[a-zA-Z]+\\b")) {                 // Testa para inputs inválidos
                System.out.println("Invalid identifier");
            } else if (input.matches("[a-zA-Z]+\\s*=\\s*[a-zA-Z]+")) {              // Executa atribuição de variáveis
                Integer varValue = this.variables.get(entry[1]);
                if (varValue == null) {
                    System.out.println("Unknown variable");
                    return;
                }
                variables.put(entry[0], varValue);

            }else if (input.matches("[a-zA-Z]+\\s*=\\s*\\d+")) {
                variables.put(entry[0], Integer.parseInt(entry[1]));
            }
        } else {                                                                // Executa as operções de soma e subtração
            String[] expression = input.split("\\s+");
            int sum = 0;

            for (int i = 0; i < expression.length; i++) {
                if (expression[i].matches("\\d+")) {
                    sum += Integer.parseInt(expression[i]);
                } else if (expression[i].matches("\\b[a-zA-Z]+\\b")) {
                    Integer varValue = this.variables.get(expression[i]);
                    if (varValue == null) {
                        System.out.println("Unknown variable");
                        return;
                    }
                    sum += varValue;

                } else if (expression[i].matches("[-]\\d")) {
                    sum += -1 * Integer.parseInt(expression[i].substring(1));
                } else if (expression[i].matches("[+]\\d")) {
                    sum += Integer.parseInt(expression[i].substring(1));
                } else if (expression[i].matches("[+]+")){

                    if (expression[i + 1].matches("\\d+")) {
                        sum += Integer.parseInt(expression[i + 1]);
                    } else {
                        Integer varValue = this.variables.get(expression[i + 1]);
                        if (varValue == null) {
                            System.out.println("Unknown variable");
                            return;
                        }
                        sum += varValue;
                    }

                    i++;

                } else if (expression[i].matches("[-]+")){

                    if (expression[i + 1].matches("\\d+")) {
                        if (expression[i].length() % 2 == 0) {
                            sum += Integer.parseInt(expression[i + 1]);
                        } else {
                            sum += -1 * Integer.parseInt(expression[i + 1]);
                        }
                    } else {
                        Integer varValue = this.variables.get(expression[i + 1]);
                        if (varValue == null) {
                            System.out.println("Unknown variable");
                            return;
                        }
                        if (expression[i].length() % 2 == 0) {
                            sum += varValue;
                        } else {
                            sum += -1 * varValue;
                        }
                    }

                    i++;
                }
            }
            System.out.println(sum);
        }

    }

}
