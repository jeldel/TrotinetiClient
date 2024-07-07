package client.communication;

import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;

import java.net.Socket;

public class Communication {
    private static Communication instance;
    private Socket socket;

    private Communication() {

    }

    public static Communication getInstance() {
        if(instance == null){
            instance = new Communication();
        }
        return instance;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Response makeRequest(Request request) throws Exception{
        new Sender(socket).send(request);
        Response receive = (Response) new Receiver(socket).receive();
        return receive;
    }


    //    public Response login(Request request) throws Exception {
//        new Sender(socket).send(request);
//        System.out.println("Zahtev za login je poslat");
//        Response receive = (Response) new Receiver(socket).receive();
//        return receive;
//    }
//
//    public Response addVoznja(Request request) throws Exception{
//        new Sender(socket).send(request);
//        System.out.println("Zahtev za cuvanje voznje je poslat");
//        Response receive = (Response) new Receiver(socket).receive();
//        return receive;
//    }
}
