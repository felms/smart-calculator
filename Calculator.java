import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Calculator {

    private Map<String, Integer> variables;

    public Calculator() {
        this.variables = new HashMap<>();
    }

    public void calculate(String expr) {

        if (expr.matches(".*\\s*=\\s*.*")) {                // Caso seja uma atribuição
            variableAssignment(expr);
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
            String expression = replaceOperands(expr);
            expression = infixToPosfix(expression);

            String[] operands = expression.split("\\s+");
            Deque<Integer> stack = new ArrayDeque<>();

            for (String operand : operands) {
                if (operand.matches("^-?\\d+$")) {
                    stack.addLast(Integer.valueOf(operand));
                } else {
                    int a = stack.removeLast();
                    int b = stack.removeLast();

                    int result = 0;
                    switch (operand) {
                        case "+":
                            result = b + a;
                            break;
                        case "-":
                            result = b - a;
                            break;
                        case "*":
                            result = b * a;
                            break;
                        case "/":
                            result = b / a;
                            break;
                        case "^":
                            result = (int)Math.pow(b, a);
                            break;
                    }
                    stack.addLast(result);
                }
            }

            System.out.println(stack.peekLast());
        }


    }

    private String replaceOperands(String input) {
        StringBuilder sb = new StringBuilder();
        String[] operands = input.split("\\s+");
        for (String operand : operands) {
            if (operand.matches("\\+{2,}")) {
                sb.append("+");
                sb.append(" ");
            } else if (operand.matches("-{2,}")) {
                if (operand.length() % 2 == 0) {
                    sb.append("+");
                } else {
                    sb.append("-");
                }
                sb.append(" ");
            } else if (operand.matches("\\*{2,}|\\/{2,}")) {
                throw new CalculatorException("Invalid expression");
            } else {
                sb.append(operand);
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    // Converte uma expressão da notação infix para a notação postfix
    public String infixToPosfix(String input) {
        String[] operands = (addSpacesToString(input) + " )").split("\\s+");
        Deque<String> operatorStack = new ArrayDeque<>();
        operatorStack.addLast("(");
        StringBuilder result = new StringBuilder();

        for (String operand : operands) {

            if (operand.matches("^[a-zA-Z]+")) { // Se o operando é uma variável eu pego o valor da mesma
                Integer varValue = this.variables.get(operand);
                if (varValue == null) {
                    throw new CalculatorException("Unknown variable");
                } else {
                    operand = String.valueOf(varValue);
                }
            }

            if (operand.equals("(")) {
                operatorStack.addLast(operand);
            } else if (operand.matches("^(-?\\d+)$")) {
                result.append(operand);
                result.append(" ");
            } else if (isOperator(operand)) {
                String x = operatorStack.removeLast();
                while (!operatorStack.isEmpty() && isOperator(x)
                        && precedence(x) >= precedence(operand)) {
                    result.append(x);
                    result.append(" ");
                    x = operatorStack.removeLast();
                }
                operatorStack.addLast(x);
                operatorStack.addLast(operand);

            } else if (operand.equals(")")) {
                if (operatorStack.isEmpty()) {
                    throw new CalculatorException("Invalid expression");
                }
                String x = operatorStack.removeLast();
                while (!operatorStack.isEmpty() && !x.equals("(")) {
                    result.append(x);
                    result.append(" ");
                    x = operatorStack.removeLast();
                }
            }


        }

        if (operatorStack.size() > 0) {
            throw new CalculatorException("Invalid expression");
        }

        return result.toString();
    }

    // Cálculos onde se utilizam variáveis
    private void variableAssignment(String input) {

        if (input.matches(".*\\s*=\\s*.*")){
            String[] entry = input.trim().split("\\s*=\\s*");
            if (entry.length != 2
                    || !entry[1].matches("(\\b[a-zA-Z]+\\b)|(-?\\d+$)"))  { // Testa para inputs inválidos
                throw new CalculatorException("Invalid assignment");
            } else if (!entry[0].matches("\\b[a-zA-Z]+\\b")) {                 // Testa para inputs inválidos
                throw new CalculatorException("Invalid identifier");
            } else if (input.matches("[a-zA-Z]+\\s*=\\s*[a-zA-Z]+")) {              // Executa atribuição de variáveis
                Integer varValue = this.variables.get(entry[1]);
                if (varValue == null) {
                    throw new CalculatorException("Unknown variable");
                }
                variables.put(entry[0], varValue);

            }else if (input.matches("[a-zA-Z]+\\s*=\\s*-?\\d+")) {
                variables.put(entry[0], Integer.parseInt(entry[1]));
            }
        }

    }

    // Insere espaços entre os parenteses da string pra facilitar
    // o 'split'
    private String addSpacesToString(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c == '(') {
                sb.append(c);
                sb.append(" ");
            } else if (c == ')') {
                sb.append(" ");
                sb.append(c);
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }


    // Calcula a precedencia dos operadores
    private int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return 0;
        }
    }

    // Checar se o caracter é um operador
    private boolean isOperator(String symbol) {
        return symbol.equals("^") || symbol.equals("*") || symbol.equals("/")
                || symbol.equals("+") || symbol.equals("-");
    }
}
