package server;

public class Connection {

    private static final int PORT = 48361;

    private Controller controller;

    public Connection(Controller controller) {
        this.controller = controller;
    }


    private class ConnectionAccepter extends Thread {

        public ConnectionAccepter(){
            start();
        }


        public void run() {
            while(true){
                try(ServerSocket servSocket = new ServerSocket(PORT)){
                    Socket socket = servSocket.accept();
                    Client client = new Client(socket);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
