package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataBase.createNewDataBase(args[1]);
        home();
    }

    public static void home() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        int input = scanner.nextInt();
        if (input == 1) {
            crtAcc();
        } else if (input == 2) {
            login();
        } else if (input == 0) {
            System.out.println("Bye!");
        }
    }




    public static void crtAcc() {
        Account acc = new Account();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(acc.getAccNumber());
        System.out.println("Your card PIN:");
        System.out.println(acc.getPass());
        DataBase.insert(acc);
        home();
    }



    public static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number:");
        long enteredNumber = scanner.nextLong();
        System.out.println("Enter your PIN:");
        int enteredPin = scanner.nextInt();


        if (DataBase.checkNUmberCard(enteredNumber)) {
            if (DataBase.checkPinCard(enteredNumber, enteredPin)) {
                insideAccount(enteredNumber);
            } else {
                System.out.println("Wrong card number or PIN!");
                home();
            }
        } else {
            System.out.println("Wrong card number or PIN!");
            home();
        }

    }

    public static void insideAccount(Long cardNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You have successfully logged in!");
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3 Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
        int input = scanner.nextInt();

        if (input == 1) {
            System.out.println(DataBase.getBalance(cardNumber));
            insideAccount(cardNumber);
        } else if (input == 2) {
            System.out.println("Enter income:");
            int money = scanner.nextInt();
            DataBase.addIncome(cardNumber, money);
            insideAccount(cardNumber);
        } else if (input == 3) {
            long to = scanner.nextLong();
            int money = scanner.nextInt();
            DataBase.doTransfer(cardNumber, to, money);
            insideAccount(cardNumber);
        } else if (input == 4) {
            DataBase.deleteAccount(cardNumber);
            home();
        } else if (input == 5) {
            System.out.println("You have successfully logged out!");
            home();
        } else if (input == 0) {
            System.out.println("Bye!");
        }


    }

}
