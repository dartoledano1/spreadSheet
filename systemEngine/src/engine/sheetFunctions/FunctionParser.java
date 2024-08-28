package engine.sheetFunctions;
import engine.api.Cell;
import engine.api.SheetWriter;
import engine.value.CellType;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


    public enum FunctionParser {

        IDENTITY{

            public Expression parse(List<String> args , SheetWriter sheet, Cell cell) {

                if(args.size() != 1)
                {
                    throw new IllegalArgumentException("Invalid number of arguments for IDENTITY, expecting 1 and received " + args.size());
                }

                String ValueOfArg = args.get(0).trim();

                if(isBoolean(ValueOfArg))
                {
                    return new IndendityExpression(Boolean.parseBoolean(ValueOfArg), CellType.BOOLEAN);
                }
                else if(isNumeric(ValueOfArg))
                {
                    return new IndendityExpression(Double.parseDouble(ValueOfArg) , CellType.NUMERIC);
                }
                else
                {
                    return new IndendityExpression(ValueOfArg, CellType.STRING);
                }

            }

            private static boolean isBoolean(String arg)
            {
                return "true".equalsIgnoreCase(arg) || "false".equalsIgnoreCase(arg);
            }

            public static boolean isNumeric(String arg)
            {
                try
                {
                    Double.parseDouble(arg);
                    return true;
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
            }


        },
        PLUS {
            public Expression parse(List<String> args, SheetWriter sheet, Cell cell) {
                try {
                    if (args.size() != 2) {
                        throw new IllegalArgumentException("Invalid number of arguments for PLUS, expecting 2 and received " + args.size());
                    }

                    Expression arg1 = parseExpression(args.get(0).trim(), sheet, cell);
                    Expression arg2 = parseExpression(args.get(1).trim(), sheet, cell);

                    if (!arg1.getFunctionResultType().equals(CellType.NUMERIC) || !arg2.getFunctionResultType().equals(CellType.NUMERIC)) {
                        throw new IllegalArgumentException("Invalid argument for PLUS, expected 2 Numeric and got " + arg1.getFunctionResultType() + ", " + arg2.getFunctionResultType());
                    }

                    return new PlusExpression(arg1, arg2);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }
            }
        },
        MINUS{
            public Expression parse(List<String> args , SheetWriter sheet, Cell cell){
                try {
                    if (args.size() != 2) {
                        throw new IllegalArgumentException("Invalid number of arguments for PLUS, expecting 2 and received " + args.size());
                    }

                    Expression arg1 = parseExpression(args.get(0).trim(), sheet, cell);
                    Expression arg2 = parseExpression(args.get(1).trim(), sheet, cell);


                    if (!arg1.getFunctionResultType().equals(CellType.NUMERIC) || !arg2.getFunctionResultType().equals(CellType.NUMERIC)) {
                        throw new IllegalArgumentException("Invalid argument for MINUS, expected 2 Numeric and got " + arg1.getFunctionResultType() + " " + arg2.getFunctionResultType());
                    }
                    return new MinusExpression(arg1, arg2);
                }catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }

            }
        },
        DIVIDE{
            public Expression parse(List<String> args , SheetWriter sheet, Cell cell) {
                try {
                    if (args.size() != 2) {
                        throw new IllegalArgumentException("Invalid number of arguments for PLUS, expecting 2 and received " + args.size());
                    }

                    Expression arg1 = parseExpression(args.get(0).trim(), sheet, cell);
                    Expression arg2 = parseExpression(args.get(1).trim(), sheet, cell);


                    if (!arg1.getFunctionResultType().equals(CellType.NUMERIC) || !arg2.getFunctionResultType().equals(CellType.NUMERIC)) {
                        throw new IllegalArgumentException("Invalid argument for DIVIDE, expected 2 Numeric and got " + arg1.getFunctionResultType() + " " + arg2.getFunctionResultType());
                    }
                    return new DivideExpression(arg1, arg2);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }

            }
        },
        MOD{
            public Expression parse(List<String> args , SheetWriter sheet, Cell cell){

                try {

                    if (args.size() != 2) {
                        throw new IllegalArgumentException("Invalid number of arguments for PLUS, expecting 2 and received " + args.size());
                    }

                    Expression arg1 = parseExpression(args.get(0).trim(), sheet, cell);
                    Expression arg2 = parseExpression(args.get(1).trim(), sheet, cell);


                    if (!arg1.getFunctionResultType().equals(CellType.NUMERIC) || !arg2.getFunctionResultType().equals(CellType.NUMERIC)) {
                        throw new IllegalArgumentException("Invalid argument for MOD, expected 2 Numeric and got " + arg1.getFunctionResultType() + " " + arg2.getFunctionResultType());
                    }
                    return new ModExpression(arg1, arg2);
                }catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }

            }
        },
        POW{
            public Expression parse(List<String> args , SheetWriter sheet, Cell cell){
                try {
                    if (args.size() != 2) {
                        throw new IllegalArgumentException("Invalid number of arguments for PLUS, expecting 2 and received " + args.size());
                    }

                    Expression arg1 = parseExpression(args.get(0).trim(), sheet, cell);
                    Expression arg2 = parseExpression(args.get(1).trim(), sheet,cell);


                    if (!arg1.getFunctionResultType().equals(CellType.NUMERIC) || !arg2.getFunctionResultType().equals(CellType.NUMERIC)) {
                        throw new IllegalArgumentException("Invalid argument for POW, expected 2 Numeric and got " + arg1.getFunctionResultType() + " " + arg2.getFunctionResultType());
                    }
                    return new PowExpression(arg1, arg2);
                }catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }
            }
        },
        TIMES{
            public Expression parse(List<String> args , SheetWriter sheet, Cell cell){
                try {
                    if (args.size() != 2) {
                        throw new IllegalArgumentException("Invalid number of arguments for PLUS, expecting 2 and received " + args.size());
                    }

                    Expression arg1 = parseExpression(args.get(0).trim(), sheet, cell);
                    Expression arg2 = parseExpression(args.get(1).trim(), sheet, cell);


                    if (!arg1.getFunctionResultType().equals(CellType.NUMERIC) || !arg2.getFunctionResultType().equals(CellType.NUMERIC)) {
                        throw new IllegalArgumentException("Invalid argument for TIMES, expected 2 Numeric and got " + arg1.getFunctionResultType() + " " + arg2.getFunctionResultType());
                    }
                    return new TimesExpression(arg1, arg2);
                }catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }
            }
        },
        ABS{
            public Expression parse(List<String> args , SheetWriter sheet, Cell cell){
                try {

                    if (args.size() != 1) {
                        throw new IllegalArgumentException("Invalid number of arguments for ABS, expecting 1 and received " + args.size());
                    }

                    Expression arg1 = parseExpression(args.get(0).trim(), sheet, cell);
                    if (!arg1.getFunctionResultType().equals(CellType.NUMERIC)) {
                        throw new IllegalArgumentException("Invalid argument for ABS, expected numeric and got " + arg1.getFunctionResultType());
                    }

                    return new ABSExpression(arg1);
                }catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }

            }

            public static boolean isNumeric(String arg)
            {
                try
                {
                    Double.parseDouble(arg);
                    return true;
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
            }

        },
        CONCAT{
            public Expression parse(List<String> args , SheetWriter sheet, Cell cell){
                try {
                    if (args.size() != 2) {
                        throw new IllegalArgumentException("Invalid number of arguments for CONCAT, expecting 2 and received " + args.size());
                    }

                    Expression arg1 = parseExpression(args.get(0).trim(), sheet, cell);
                    Expression arg2 = parseExpression(args.get(1).trim(), sheet, cell);


                    if (!arg1.getFunctionResultType().equals(CellType.STRING) || !arg2.getFunctionResultType().equals(CellType.STRING)) {
                        throw new IllegalArgumentException("Invalid argument for CONCAT, expected 2 Numeric and got " + arg1.getFunctionResultType() + " " + arg2.getFunctionResultType());
                    }
                    return new ConcatExpression(arg1, arg2);
                }catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }

            }

        },
        SUB{
            public Expression parse(List<String> args , SheetWriter sheet, Cell cell){
                try {
                    if (args.size() != 3) {
                        throw new IllegalArgumentException("Invalid number of arguments for SUB, expecting 2 and received " + args.size());
                    }

                    Expression arg1 = parseExpression(args.get(0).trim(), sheet, cell);
                    Expression arg2 = parseExpression(args.get(1).trim(), sheet, cell);
                    Expression arg3 = parseExpression(args.get(2).trim(), sheet, cell);

                    if (!arg1.getFunctionResultType().equals(CellType.STRING) || !arg2.getFunctionResultType().equals(CellType.NUMERIC) || !arg3.getFunctionResultType().equals(CellType.NUMERIC)) {
                        throw new IllegalArgumentException("Invalid argument for SUB, expected 1 String and 2 Numeric and got " + arg1.getFunctionResultType() + " , " + arg2.getFunctionResultType() + " and " + arg3.getFunctionResultType());
                    } else if (!isWholeNumber(args.get(1).trim()) || !isWholeNumber(args.get(2).trim())) {
                        throw new IllegalArgumentException("Invalid argument for SUB, expected 2 Numeric whole numbers and got " + args.get(1).trim() + " and " + args.get(2).trim());
                    }

                    int ValueLeft, ValueRight;
                    String Source = args.get(0).trim();
                    ValueLeft = Integer.parseInt(args.get(1).trim());
                    ValueRight = Integer.parseInt(args.get(2).trim());


                    if (!ValidateSub(Source, ValueLeft, ValueRight)) {
                        throw new IllegalArgumentException("Invalid parameter for SUB , Left index must be greater then 0 and right index lower then " + Source.length());
                    }

                    return new SubExpression(arg1, arg2, arg3);
                }catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }
            }

            public static boolean isWholeNumber(String str) {
                try {
                    double number = Double.parseDouble(str);
                    return number == Math.rint(number);
                } catch (NumberFormatException e) {
                    return false; // Not a valid number
                }
            }

            public boolean ValidateSub(String Source ,int left , int right)
            {
                if(left < 0 || right > Source.length())
                {
                    return false;
                }
                return true;

            }
        },
        REF{
            public Expression parse(List<String> args , SheetWriter sheet, Cell cell){
                try {
                    if (args.size() != 1) {
                        throw new IllegalArgumentException("Invalid number of arguments for REF, expecting 2 and received " + args.size());
                    }
                }catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }


                Expression arg1 = parseExpression(args.get(0).trim() , sheet , cell);
                try {
                    if (!arg1.getFunctionResultType().equals(CellType.STRING)) {
                        throw new IllegalArgumentException("Invalid argument for REF, expected 1 String including column and row and got " + args.get(0).trim());
                    }
                }catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }

                int column = args.get(0).trim().charAt(0) - 'A' + 1;
                try {
                    if (column < 1 || column > sheet.getSheetLayout().getNumOfCols())
                        throw new IllegalArgumentException("Invalid argument for REF, expected a column between 1 and " + sheet.getSheetLayout().getNumOfCols() + " and got " + column);
                }catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }
                int row ;
                try{
                    row = Integer.parseInt(args.get(0).trim().substring(1));
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid argument for REF, expected a number as rows and got " + args.get(0).trim().substring(1));
                }

                if(row < 1 || row > sheet.getSheetLayout().getNumOfRows())
                    throw new IllegalArgumentException("Invalid argument for REF, expected a row between 1 and " + sheet.getSheetLayout().getNumOfRows() + " and got " + row);


                return new REFExpression(args.get(0).trim() , sheet, cell);

            }
        };

        abstract public Expression parse(List<String> arguments , SheetWriter sheet, Cell cell);

        public static Expression parseExpression(String input , SheetWriter sheet, Cell cell)
        {
            if(input.startsWith("{") && input.endsWith("}"))
            {

                String ExpressionString = input.substring(1, input.length() - 1);
                List<String> TopLevelParts = parseMainParts(ExpressionString);


                String functionName = TopLevelParts.get(0).trim().toUpperCase();

                TopLevelParts.remove(0);

                return FunctionParser.valueOf(functionName).parse(TopLevelParts , sheet, cell);

            }

            return FunctionParser.IDENTITY.parse(List.of(input.trim()) , sheet, cell);
        }

        private static List<String> parseMainParts(String input)
        {

            List<String> Parts = new ArrayList<>();
            StringBuilder buffer = new StringBuilder();
            Stack<Character> stack = new Stack<>();

            for(char c : input.toCharArray())
            {
                if (c == '{')
                {
                    stack.push(c);
                }
                else if (c == '}')
                {
                    stack.pop();
                }

                if(stack.isEmpty() && c == ',')
                {
                    Parts.add(buffer.toString().trim());
                    buffer.setLength(0);
                }
                else
                {
                    buffer.append(c);
                }

            }

            if(buffer.length() > 0)
            {
                Parts.add(buffer.toString().trim());
            }

            return Parts;

        }




    }








