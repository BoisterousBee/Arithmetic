// Arithmetic.java - Etude 9 - Oliver O'Connor - 6529968
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Arithmetic {
    public static Integer goal;
    public static ArrayList<Integer> integerOrder = new ArrayList<Integer>();
    public static String path = "";

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            String value = sc.nextLine();
            if (value.startsWith("N")){
                ArrayList<Integer> intOrder = CleanUp(value);
                if (intOrder == null){
                    System.out.println(value + " Invalid");
                } else {
                    path = "";
                    goal = intOrder.get(0);
                    intOrder.remove(0);
                    integerOrder.clear();
                    integerOrder.addAll(intOrder);
                    int length = integerOrder.size();
                    if (Noperation(length - 1, integerOrder.get(0), 1, 'z', 0)){
                        String finalString = zipStrings("N", intOrder, path);
                        System.out.println(finalString);

                    } else System.out.println(value + " impossible");
                }
            }
            else if (value.startsWith("L")){
                ArrayList<Integer> intOrder = CleanUp(value);
                if (intOrder == null){
                    System.out.println(value + " Invalid");
                } else {
                    path = "";
                    goal = intOrder.get(0);
                    intOrder.remove(0);
                    integerOrder.clear();
                    integerOrder.addAll(intOrder);
                    int length = integerOrder.size();
                    if (Loperation(length - 1, integerOrder.get(0), 1)){
                        String finalString = zipStrings("L", intOrder, path);
                        System.out.println(finalString);
                    } else System.out.println(value + " impossible");
                }
            } else {
                System.out.println(value + " Invalid");
            }
        }
    }

    public static ArrayList<Integer> CleanUp(String value){
        ArrayList<Integer> intOrder = new ArrayList<Integer>();
        String[] split = value.split("\\s+");
        String[] sSplit = Arrays.copyOfRange(split,1,split.length);
        for (String s : sSplit){
            try {
                int number = Integer.parseInt(s);
                intOrder.add(number);
            } catch (NumberFormatException e) {
                return null;
            } 
        }
        return intOrder;
    }

    public static boolean Noperation(int length, int count, int current, char past, int plusValue){
        if (length == 0){
            if (count == goal){
                return true;
            }
            else {
                return false;
            }  
        } 
        else if (count > goal){
            return false;
        }
        int multiplicationCount = 0;
        if (past == '*' && plusValue != 0){
            multiplicationCount = ((count - plusValue) * integerOrder.get(current) + plusValue);
        }
        else if (past == '+' && plusValue == 0){
            plusValue = integerOrder.get(current-2);
            multiplicationCount = ((count - plusValue) * integerOrder.get(current)) + plusValue;
        } else {
            multiplicationCount = count * integerOrder.get(current);
        }
        if (Noperation(length-1, multiplicationCount, current+1,'*',plusValue)){
            path = "*" + path;
            return true;
        }
        int additionCount = count + integerOrder.get(current);
        plusValue = 0;
        if (Noperation(length-1, additionCount, current+1,'+',plusValue)){
            path = "+" + path;
            return true;
        }

        return false;
    }

    public static boolean Loperation(int length, int count, int current){
        if (length == 0){
            if (count == goal){
                return true;
            }
            else {
                return false;
            }  
        } 
        else if (count > goal){
            return false;
        }

        int additionCount = count + integerOrder.get(current);
        if (Loperation(length-1, additionCount, current+1)){
            path = "+" + path;
            return true;
        }

        int multiplicationCount = count * integerOrder.get(current);
        if (Loperation(length-1, multiplicationCount, current+1)){
            path = "*" + path;
            return true;
        }
        return false;
    }

    public static String zipStrings(String prefix, ArrayList<Integer> integers, String pathway){
        String finalString = prefix + " " + goal + " = " + integers.get(0);
        for (int i = 1; i < integers.size(); i++){
            finalString = finalString + " " + pathway.charAt(i-1) + " " + integers.get(i);
        }
        return finalString;
    }
}