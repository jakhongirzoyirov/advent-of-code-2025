package org.example.day2;

public class Main {
    public static void main(String[] args) {
        System.out.println(day2(getInput()));
//        System.out.println(isRepeatedSeveral(1188511885));
    }

    private static long day2(String input) {
        String[] split = input.split(",");
        long sum = 0;
        for (String range : split) {
            System.out.println(range);
            String[] arr = range.split("-");
            for (long i = Long.parseLong(arr[0]); i <= Long.parseLong(arr[1]); i++) {
                if (isRepeatedSeveral(i)) {
                    System.out.println("-->" + i);
                    sum += i;
                }
            }
        }
        return sum;
    }

    private static boolean isRepeatedSeveral(long num) {
        String str = String.valueOf(num);
        int count = 0;
        for (int i = 1; i < str.length(); i++) {
            if (str.length() % i == 0) {
                count = 0;
                String first = str.substring(0, i);
                for (int j = i; j < str.length(); j+=i) {
                    String second = str.substring(j, j + i);
                    if (first.equals(second)) {
                        count++;
                    }
                }
                if (count + 1 == str.length() / i) return true;
            }
        }
        return false;
    }

    private static long day1(String input) {
        String[] split = input.split(",");
        long sum = 0;
        for (String range : split) {
            String[] arr = range.split("-");
            for (long i = Long.parseLong(arr[0]); i <= Long.parseLong(arr[1]); i++) {
                if (isRepeatedTwice(i)) {
                    sum += i;
                }
            }
        }
        return sum;
    }

    private static boolean isRepeatedTwice(long num) {
        String str = String.valueOf(num);
        if (str.length() % 2 != 0) return false;
        long a = Long.parseLong(str.substring(0, str.length() / 2));
        long b = Long.parseLong(str.substring(str.length() / 2));
        return a == b;
    }

    private static String getExample() {
        return "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124";
    }

    private static String getInput() {
        return "3299143-3378031,97290-131156,525485-660941,7606-10180,961703-1031105,6856273537-6856492968,403537-451118,5330-7241,274725-384313,27212572-27307438,926609-954003,3035-3822,161-238,22625-31241,38327962-38415781,778-1155,141513-192427,2-14,47639-60595,4745616404-4745679582,1296-1852,80-102,284-392,4207561-4292448,404-483,708177-776613,65404-87389,5757541911-5757673432,21-38,485-731,1328256-1444696,11453498-11629572,41-66,2147-3014,714670445-714760965,531505304-531554460,4029-5268,3131222053-3131390224";
    }
}
