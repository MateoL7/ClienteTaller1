package app;

import java.util.Scanner;

import comm.ClienteT1;
import comm.Emisor;
import comm.Receptor.OnMessageListener;

//OBSERVADOR
public class Application implements OnMessageListener{

	private ClienteT1 connection;


	public Application(){
		connection = ClienteT1.getInstance();
		connection.setIp("127.0.0.1");
		connection.setPuerto(5000);
		connection.setListenerOfMessages(this);
	}

	public void init() {
		connection.start();
		Scanner scan = new Scanner(System.in);

		while(true) {
			System.out.println("Escribe el comando: ");
			String comando = scan.nextLine();
			Emisor em = connection.getEmisor();
			if(em != null) em.sendMessage(comando);
			else System.out.println("No hay cliente conectado");
		}
	}

	@Override
	public void OnMessage(String msg) {
		System.out.println("[Servidor]: " + msg);
	}
}