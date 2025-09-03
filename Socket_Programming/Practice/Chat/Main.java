package Socket_Programming.Practice.Chat;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);) {
            System.out.println("Please Enter the Server Port: ");
            String ipAddress = "127.0.0.1";
            String port = sc.nextLine();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
