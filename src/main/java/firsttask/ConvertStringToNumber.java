package firsttask;

import java.util.*;

public class ConvertStringToNumber {

    private static Map<String, Integer> NUMBERS_LIST =  new HashMap<>();

    public ConvertStringToNumber() {
        initList();
    }

    private void initList() {
        Map<String, Integer> numbers = new HashMap<>();

        numbers.put("one", 1);
        numbers.put("two", 2);
        numbers.put("three", 3);
        numbers.put("four", 4);
        numbers.put("five", 5);
        numbers.put("six", 6);
        numbers.put("seven", 7);
        numbers.put("eight", 8);
        numbers.put("nine", 9);

        numbers.put("ten", 10);
        numbers.put("eleven", 11);
        numbers.put("twelve", 12);
        numbers.put("thirteen", 13);
        numbers.put("fourteen", 14);
        numbers.put("fifteen", 15);
        numbers.put("sixteen", 16);
        numbers.put("seventeen", 17);
        numbers.put("eighteen", 18);
        numbers.put("ninety", 19);

        numbers.put("twenty", 20);
        numbers.put("thirty", 30);
        numbers.put("forty", 40);
        numbers.put("fifty", 50);
        numbers.put("sixty", 60);
        numbers.put("seventy", 70);
        numbers.put("eighty", 80);
        numbers.put("ninety", 90);

        NUMBERS_LIST = Collections.unmodifiableMap(numbers);
    }

    public static int convertToNumber(String strNumber) throws NumberFormatException {
        String[] numbers = strNumber.split(" ");
        List<String> list = Arrays.asList(numbers);
        int result = 0;

        List<String> currentNumber = new LinkedList<>();

        for (String str : list) {
            if (NUMBERS_LIST.containsKey(str)) {
                currentNumber.add(str);
            } else if ("thousand".equals(str)) {
                result += searchNumber(currentNumber) * 1000;
                currentNumber.clear();
            } else if ("hundred".equals(str)) {
                result += searchNumber(currentNumber) * 100;
                currentNumber.clear();
            }
        }
        result += searchNumber(currentNumber);

        if (1 <= result && result <= 9999) {
            return result;
        }
        else {
            throw new NumberFormatException("Number is not a valid!");
        }
    }

    private static int searchNumber(List<String> strNumber) {
        int result = 0;
        for(String str : strNumber) {
            if (NUMBERS_LIST.containsKey(str)) {
                  result += NUMBERS_LIST.get(str);

            }
        }
        return result;
    }
}
