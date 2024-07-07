package client;

import client.communication.Communication;
import forms.loginForm;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void connect() throws IOException {
        Socket socket = new Socket("localhost", 9000);
        System.out.println("Klijent se povezao");
        Communication.getInstance().setSocket(socket);
        new loginForm().setVisible(true);

    }
}
