package com.example.amanetpfe.utils;

import java.time.Year;

public class AccountUtils {

    public static String  generateAccountNumber(){

        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;
        //generate random number between min and max
        int randNumber = (int) Math.floor(Math.random()* (max-min +1 )+min);

        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);

        StringBuilder accountNumber = new StringBuilder();
        return accountNumber.append(year).append(randNumber).toString();

    }


    public static final String ACCOUNT_EXISTS_CODE = "001";
    public  static final String ACCOUNT_EXIST_MESSAGE="this user already has an account";
    public  static  final  String ACCOUNT_CREATION_SUCCESS="002";
    public static final String ACCOUNT_CREATION_MESSAGE="account has been successfully created";
    public  static final String ACCOUNT_NOT_EXIST_CODE ="003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE ="user with the provided account number does not exist";
    public  static final String ACCOUNT_FOUND_CODE ="004";
    public static final String ACCOUNT_FOUND_SUCCESS  ="user with the provided account number  exist";

    public  static final String ACCOUNT_CREDITED_SUCCESS ="005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE  ="user with the provided account number  exist";

    public  static final String INSUFFICIENT_BALANCE_CODE ="006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE  ="insufficient Balance ";

    public  static final String ACCOUNT_DEBITED_SUCCESS_CODE ="007";
    public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE  ="Account has been successfully  debited";

    public  static final String TRANSFER_SUCCESSFUL_CODE ="008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE  ="Transfer successful";
}


