import main.controller.VeribankController;
import main.controller.VeribankControllerApi;
import main.service.impl.AccountServiceImpl;
import main.service.impl.ValidatorServiceImpl;
import main.to.AccountResponse;

import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        System.out.println();
        System.out.println();
        System.out.println("Bank account initial status: ");
        System.out.println();
        System.out.println("id: fransisco");
        System.out.println("balance: 100");
        System.out.println();
        System.out.println("id: florencia");
        System.out.println("balance: 50");
        System.out.println();
        System.out.println("1- Deposit");
        System.out.println("2- Custom Deposit");
        System.out.println("3- Withdraw");
        System.out.println("4- Custom Withdraw");
        System.out.println("5- Transfer");
        System.out.println("6- Custom Transfer");
        System.out.println("7- Exit");
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        VeribankControllerApi veribankControllerApi = new VeribankController(new AccountServiceImpl(), new ValidatorServiceImpl());

        boolean onMenu = true;

        while (onMenu) {
            System.out.println("Select option: ");
            switch (scanner.nextInt()) {
                case 1 -> showResponse(veribankControllerApi.deposit("francisco", 10));
                case 2 -> {
                    System.out.println("Please enter the deposit amount: ");
                    showResponse(veribankControllerApi.deposit("francisco", scanner.nextInt()));
                }
                case 3 -> showResponse(veribankControllerApi.withdrawal("francisco", 10));
                case 4 -> {
                    System.out.println("Please enter the withdrawal amount: ");
                    showResponse(veribankControllerApi.withdrawal("francisco", scanner.nextInt()));
                }
                case 5 -> showResponses(veribankControllerApi.transfer("francisco", 10, "florencia"));
                case 6 -> {
                    System.out.println("Please enter the transfer origin: ");
                    String origin = scanner.next();
                    System.out.println("Please enter the transfer destination: ");
                    String destination = scanner.next();
                    System.out.println("Please enter the transfer amount: ");
                    showResponses(veribankControllerApi.transfer(origin, scanner.nextInt(), destination));
                }
                default -> onMenu = false;
            }
        }
    }

    private static void showResponse(AccountResponse accountResponse) {
        System.out.println("--------------------------------------------------------");
        if (accountResponse.getErrorMessage() == null) {
            System.out.println("Updated account");
            System.out.println("Id: " + accountResponse.getAccount().getId());
            System.out.println("Balance: " + accountResponse.getAccount().getBalance());
        } else {
            System.out.println("Error Occurred");
            System.out.println(accountResponse.getErrorMessage());
        }
        System.out.println("--------------------------------------------------------");
    }

    private static void showResponses(List<AccountResponse> accountResponseList) {
        System.out.println("--------------------------------------------------------");
        if (accountResponseList.get(0).getErrorMessage() == null) {
            System.out.println("Updated accounts");
            accountResponseList.forEach(accountResponse -> {
                System.out.println("Id: " + accountResponse.getAccount().getId());
                System.out.println("Balance: " + accountResponse.getAccount().getBalance());
                System.out.println();
            });
        } else {
            System.out.println("Error Occurred");
            System.out.println(accountResponseList.get(0).getErrorMessage());
        }
        System.out.println("--------------------------------------------------------");
    }

}
