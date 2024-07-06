package com.example.amanetpfe.utils;

import java.time.Year;
import java.util.Random;

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


    //methode qui genere le RIB
    public  static String generateRIB(){
        //CODE BIC DE LA BANQUE
        String bic = "07";
        String codeGuichet= "405";
        //generer le numero de compte
        String numeroCompte = generateAccountNumber();
        //genere aleatoirement le rest de RIB

        Random random = new Random();
        String CleRIB = String.format("%02d",random.nextInt(100)); //generer un cle RIB a  2 chiffres
        String rib = bic+ codeGuichet+numeroCompte+CleRIB;
        return rib;

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


    public static final String REQUEST_NOT_FOUND_CODE  ="009";
    public static final String REQUEST_NOT_FOUND_MESSAGE  ="request Not found";



    public static final String TRANSACTION_SUCCESS="010";

    public static final String TRANSACTION_SUCCESS_CODE ="transaction successful";
}


