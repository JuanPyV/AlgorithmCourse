import java.util.ArrayList;
import java.util.Arrays;

public class BigNumber {

    private ArrayList<Integer> number = new ArrayList<>();

    public BigNumber(ArrayList<Integer> number) {
        this.number = number;
    }


    public static BigNumber multiplication(BigNumber a, BigNumber b) {
        BigNumber x = new BigNumber(new ArrayList<>());
        // Case base: the numbers just have 1 digit
        if (a.number.size() == 1 || b.number.size() == 1) {
            int oneMult = toInt(a) * toInt(b);
            return new BigNumber(intToBigNumber(oneMult));
        }
        int n = Math.max(a.number.size(), b.number.size());
        if (n%2 != 0) n+=1;
        if (a.number.size()%2 != 0){
            a.number.add(0,0);

        }
        if (b.number.size()%2 != 0){
            b.number.add(0,0);

        }
        if (a.number.size() > b.number.size()) {
            int diffAB = a.number.size() - b.number.size();
            for (int i = 0; i < diffAB; i++) {
                b.number.add(0, 0);
            }
        } else if (a.number.size() < b.number.size()) {
            int diffBA = b.number.size() - a.number.size();
            for (int i = 0; i < diffBA; i++) {
                a.number.add(0, 0);
            }
        }


        BigNumber aL = new BigNumber(new ArrayList<>(a.number.subList(0, (a.number.size() / 2))));
        BigNumber aR = new BigNumber(new ArrayList<>(a.number.subList((a.number.size() / 2), a.number.size())));
        BigNumber bL = new BigNumber(new ArrayList<>(b.number.subList(0, (b.number.size() / 2))));
        BigNumber bR = new BigNumber(new ArrayList<>(b.number.subList((b.number.size() / 2), b.number.size())));

        BigNumber x1 = multiplication(aL, bL);
        BigNumber x2 = multiplication(aR, bR);
        BigNumber x3 = multiplication(sum(aL, aR), sum(bL, bR));
        BigNumber tmp = substract(x3, sum(x2, x1));

        BigNumber x1Pow = tenPow(x1, n);
        BigNumber tmpPow = tenPow(tmp, n / 2);
        BigNumber sumx1PowtmpPow = sum(x1Pow, tmpPow);
        BigNumber sumx1PowtmpPowx2 = sum(sumx1PowtmpPow, x2);

        return sumx1PowtmpPowx2;
    }

    public static BigNumber sum(BigNumber a, BigNumber b) {
        ArrayList<Integer> sumRes = new ArrayList<>();

        if (a.number.size() == 1 || b.number.size() == 1) {
            int n = toInt(a) + toInt(b);
            return new BigNumber(intToBigNumber(n));
        }

        if (a.number.size() > b.number.size()) {
            int diffAB = a.number.size() - b.number.size();
            for (int i = 0; i < diffAB; i++) {
                b.number.add(0, 0);
            }
        } else if (a.number.size() < b.number.size()) {
            int diffBA = b.number.size() - a.number.size();
            for (int i = 0; i < diffBA; i++) {
                a.number.add(0, 0);
            }
        }
        int sum, x = 0;
        //x = carry
        for (int i = a.number.size() - 1; i >= 0; i--) {
            sum = a.number.get(i) + b.number.get(i) + x;
            if (sum > 9) {
                x = 1;
                sum = sum % 10;
            } else x = 0;
            sumRes.add(0, sum);
        }
        if (x==1) sumRes.add(0, x);

        return new BigNumber(sumRes);
    }

    public static BigNumber substract(BigNumber a, BigNumber b) {
        ArrayList<Integer> subRes = new ArrayList<>();

        if (a.number.size() == 1 || b.number.size() == 1) {
            int n = toInt(a) - toInt(b);
            return new BigNumber(intToBigNumber(n));
        }

        if (a.number.size() > b.number.size()) {
            int diffAB = a.number.size() - b.number.size();
            for (int i = 0; i < diffAB; i++) {
                b.number.add(0, 0);
            }
        } else if (a.number.size() < b.number.size()) {
            int diffBA = b.number.size() - a.number.size();
            for (int i = 0; i < diffBA; i++) {
                a.number.add(0, 0);
            }
        }
        int sub, x = 0;
        //x = carry
        for (int i = a.number.size() - 1; i >= 0; i--) {
            sub = a.number.get(i) - b.number.get(i) - x;
            if (sub < 0) {
                x = 1;
                sub = 10 - (sub * -1);
            } else x = 0;
            subRes.add(0, sub);
        }
        return new BigNumber(subRes);
    }

    public static BigNumber tenPow(BigNumber a, int n) {
        for (int i = 0; i < n; i++) {
            a.number.add(0);
        }
        return a;
    }

    public static String toString(BigNumber a) {
        StringBuilder sb = new StringBuilder();
        for (int s : a.number) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static int toInt(BigNumber a) {
        int instResult = 0;
        for (int temp = 0; temp < a.number.size(); temp++) {
            instResult *= 10;
            instResult += a.number.get(temp);
        }
        return instResult;
    }

    public static ArrayList<Integer> intToBigNumber(int number) {
        String str = (new Integer(number)).toString();
        ArrayList<Integer> intArray = new ArrayList<>();
        char[] chArr = str.toCharArray();
        for (int i = 0; i < chArr.length; i++) {
            intArray.add(i, Character.getNumericValue(chArr[i]));
        }
        return intArray;
    }

    public static ArrayList<Integer> testing(ArrayList<Integer> data){
        BigNumber dataBigNumber = new BigNumber(data);
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < data.size()-1; i++){
            BigNumber a = new BigNumber(intToBigNumber(dataBigNumber.number.get(i)));
            BigNumber b = new BigNumber(intToBigNumber(dataBigNumber.number.get(i+1)));
            results.add(toInt(multiplication(a,b)));
            //System.out.println(i + ".-"  + toString(multiplication(a,b)));

        }

        return results;
    }

    public static void main(String[] args) {

        BigNumber a = new BigNumber(new ArrayList<>(Arrays.asList(6,5,4,8)));
        BigNumber b = new BigNumber(new ArrayList<>(Arrays.asList(5,4,3,4)));

        System.out.println("Resta: " + toString(substract(a, b)));
        System.out.println("Suma: " + toString(sum(a, b)));
        System.out.println("Multiplicacion: " + toString(multiplication(a, b)));



    }
}
