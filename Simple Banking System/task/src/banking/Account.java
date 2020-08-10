package banking;

import java.util.Random;

public class Account {

    private int pass;
    private long accNumber;

    public Account() {
        setPass();
        setAccNumber();
    }



    public int getPass() {
        return pass;
    }

    public void setPass() {

        this.pass = randomPass();
    }

    public long getAccNumber() {

        return accNumber;
    }

    public void setAccNumber() {

        this.accNumber = randomAccNumber();
    }

    private long randomAccNumber() {
        long iin = 400000L;
        long iinMultipl = 1000000000L;
        int[] randNum = new int[9];
        for (int i = 0; i < randNum.length; i++) {
            int r = (new Random().nextInt(9));
            while (r == 0) {
                r = (new Random().nextInt(9));
            }
            randNum[i] = r;
        }
        long randomPart = 0;
        for (int i = 0; i < randNum.length; i++) {
            randomPart = randomPart * 10 + randNum[i];
        }

        long random15digit = (iin * iinMultipl) + randomPart;
        long a = random15digit;
        long digits[] = new long[15];
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i] = a % 10;
            a = a / 10;
        }
        for (int i = 0; i < digits.length; i++) {
            if (i % 2 == 0) {
                digits[i] = digits[i] * 2;
            }
        }
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] > 9) {
                digits[i] = digits[i] - 9;
            }
        }
        int digitSum = 0;
        for (int i = 0; i < digits.length; i++) {
            digitSum += digits[i];
        }
        int checkSum = -1 * (digitSum % 10 - 10);


        long random16digit = random15digit * 10 + checkSum;
        return random16digit;


    }

    private int randomPass() {
        int[] rand = new int[4];
        for (int i = 0; i < rand.length; i++) {
            int r = (new Random().nextInt(9));
            while (r == 0) {
                r = (new Random().nextInt(9));
            }
            rand[i] = r;
        }
        final int i = 1000;
        final int h = 100;
        int random4digit = i * rand[0] + h * rand[1] + 10 * rand[2] + rand[3];
        return random4digit;
    }

    static boolean checkValidity(Long cnn) {
        String cn = String.valueOf(cnn);
        if (cn.length() != 16) {
            return false;
        }
        int[] nums = new int[16];
        String[] arrays = cn.split("");
        int sum = 0;
        for (int i = 1; i < cn.length(); i++) {
            nums[i - 1] = Integer.parseInt(arrays[i - 1]);
            if (i % 2 == 1) {
                nums[i - 1] *= 2;
            }
            if (nums[i - 1] > 9) {
                nums[i - 1] -= 9;
            }
            sum += nums[i - 1];
        }
        sum += Integer.parseInt(arrays[15]);
        return sum % 10 == 0;
    }
}
