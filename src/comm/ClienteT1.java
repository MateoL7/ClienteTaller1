package comm;

import java.io.IOException;
import java.net.Socket;
import comm.Receptor.OnMessageListener;

//CLASE OBSERVADA

public class ClienteT1 extends Thread {


	//SINGLETON
	private static ClienteT1 instance;
	private ClienteT1() {

	}
	public static synchronized ClienteT1 getInstance() {
		if(instance == null) {
			instance = new ClienteT1();
		}
		return instance;
	}


	//CONEXION
	private Socket socket;
	private String ip;
	private int puerto;
	private Emisor emisor;
	private Receptor receptor;
	public OnMessageListener listener;
	
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	


	@Override
	public void run() {
		try {
			System.out.println("Enviando solicitud...");
			socket = new Socket(ip, puerto);
			System.out.println("Solicitud aceptada");
			
			receptor = new Receptor(socket.getInputStream());
			receptor.setListener(listener);
			receptor.start();
			
			emisor = new Emisor(socket.getOutputStream());
		

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setListenerOfMessages(OnMessageListener listener) {
		 this.listener = listener;
	}

	public Emisor getEmisor() {
		return this.emisor;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

//	public interface OnMessageListener{
//		public void OnMessage(String msg);
//	}

}


