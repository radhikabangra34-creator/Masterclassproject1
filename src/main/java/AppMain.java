package BrowserProject.src.main.java;

import java.util.Scanner;

public class AppMain{

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);

        Browser browser=new Browser("home.com",10);

        while(true){
            System.out.println("\n------ BROWSER PROJECT ------");
            System.out.println("1. Visit Page");
            System.out.println("2. Back");
            System.out.println("3. Forward");
            System.out.println("4. Current Page");
            System.out.println("5. Show Cache");
            System.out.println("6. Get Cached Page");
            System.out.println("7. Exit");
            System.out.print("Choice: ");

            int choice=sc.nextInt();

            switch(choice){
                case 1 -> {
                    System.out.print("Enter URL: ");
                    String url=sc.next();

                    browser.visit(url);
                }

                case 2 -> browser.back();

                case 3 -> browser.forward();

                case 4 -> browser.currentPage();

                case 5 -> browser.showCache();

                case 6 -> {
                    System.out.print("Enter URL: ");
                    String url=sc.next();

                    browser.getCachedPage(url);
                }

                case 7 -> {
                    System.out.println("Exiting Browser...");
                    sc.close();
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }
}