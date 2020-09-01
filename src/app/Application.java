package app;

import comm.ClienteT1;

//OBSERVADOR
public class Application implements ClienteT1.OnMessageListener{

	private ClienteT1 connection;
	

	public Application(){
		connection = ClienteT1.getInstance();
		connection.setListener(this);
	}

	public void init() {
		connection.start();
	}

	@Override
	public void OnMessage(String msg) {
		System.out.println("[Servidor]: " + msg);
	}
}