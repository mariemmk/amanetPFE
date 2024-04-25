package com.example.amanetpfe.Services.Classes;

import com.example.amanetpfe.Entities.Account;
import com.example.amanetpfe.Repositories.AccountRepository;
import com.example.amanetpfe.Services.Interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService  implements IAccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public Account saveAccount(Account account) {
        // Vérifier si l'utilisateur a déjà un compte associé
        Optional<Account> existingAccount = accountRepository.findByUser(account.getUser());

        // Si un compte existe déjà pour cet utilisateur, renvoyer null
        if (existingAccount.isPresent()) {
            return null;
        } else {
            // Générer automatiquement le numéro de compte et enregistrer le nouveau compte
            account.setAccountNumber(generateAccountNumber());
            account.setRIB(generateRIB());
            return accountRepository.save(account);
        }
    }

    @Override
    // Méthode pour récupérer tous les comptes
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    @Override
    // Méthode pour récupérer un compte par son ID
    public Optional<Account> getAccountById(Integer idAccount) {
        return accountRepository.findById(idAccount);
    }
    @Override
    // Méthode pour supprimer un compte par son ID
    public void deleteAccountById(Integer idAccount) {
        accountRepository.deleteById(idAccount);
    }
    @Override
    // Méthode pour mettre à jour un compte
    public Account updateAccount(Integer idAccount, Account updatedAccount) {
        Optional<Account> existingAccountOptional = accountRepository.findById(idAccount);
        if (existingAccountOptional.isPresent()) {
            Account existingAccount = existingAccountOptional.get();
            existingAccount.setAccountNumber(updatedAccount.getAccountNumber());
            existingAccount.setBalance(updatedAccount.getBalance());
            existingAccount.setAccountType(updatedAccount.getAccountType());
            existingAccount.setDevise(updatedAccount.getDevise());
            existingAccount.setTotSolde(updatedAccount.getTotSolde());
            existingAccount.setDateSolde(updatedAccount.getDateSolde());
            return accountRepository.save(existingAccount);
        } else {
            // Gérer l'erreur si le compte n'existe pas
            return null;
        }
    }
    @Override
    // Méthode pour générer automatiquement le numéro de compte
    public   String generateAccountNumber() {
        StringBuilder accountNumberBuilder = new StringBuilder();

        // Générer une partie aléatoire du numéro de compte
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            accountNumberBuilder.append(random.nextInt(10)); // Ajouter un chiffre aléatoire entre 0 et 9
        }

        // Ajouter un préfixe ou un suffixe si nécessaire
      // accountNumberBuilder.insert(0, "ACC"); // Ajouter un préfixe, par exemple "ACC"

        return accountNumberBuilder.toString();
    }


    @Override
    //methode qui genere le RIB
    public  String generateRIB(){
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
}
