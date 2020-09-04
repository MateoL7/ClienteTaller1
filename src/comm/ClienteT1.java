package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteT1 extends Thread {



	private static ClienteT1 instance;

	private ClienteT1() {

	}

	public static synchronized ClienteT1 getInstance() {
		if(instance == null) {
			instance = new ClienteT1();
		}
		return instance;
	}



	private Socket socket;



	public OnMessageListener listener;


	@Override
	public void run() {
		try {
			System.out.println("Enviando solicitud...");
			socket = new Socket("127.0.0.1", 5000);
			System.out.println("Conectados");

			byte[] m1024 = new byte[1024];
			String msg1024 = new String(m1024);
			
			byte[] m8192 = new byte[8192];
			String msg8192 = new String(m8192);

			//			int bytes = msg1024.getBytes().length;
			//			System.out.println(bytes);

			OutputStream out = socket.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

			Scanner scan = new Scanner(System.in);

			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			while(true) {
				System.out.println("Escribe el comando: ");
				String comando = scan.nextLine();
				if(comando.equalsIgnoreCase("RTT")) {
					comando = msg1024;
					double timeBefore = System.currentTimeMillis();
					bw.write(comando + "\n");
					bw.flush();
					String msg = br.readLine();
					listener.OnMessage(msg);
					double timeAfter = System.currentTimeMillis();
					double time = (timeAfter-timeBefore);
					System.out.println("Tiempo tomado: " + time + " ms");
				}else if(comando.equalsIgnoreCase("Speed")) {
					comando = msg8192;
					double timeBefore = System.currentTimeMillis();
					bw.write(comando + "\n");
					bw.flush();
					String msg = br.readLine();
					listener.OnMessage(msg);
					double timeAfter = System.currentTimeMillis();
					double time = timeAfter-timeBefore;
					double kb = ((comando.getBytes().length)/1000);
					double speed = ((2*kb)/(time/1000));
//					double speed = (((2*comando.getBytes().length)/time)/1000);
					System.out.println("Speed: " + speed + " Kb/s");
				}else {
					bw.write(comando+"\n");
					bw.flush();
					String msg = br.readLine();
					listener.OnMessage(msg);
				}

			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}

	public interface OnMessageListener{
		public void OnMessage(String msg);
	}

}


