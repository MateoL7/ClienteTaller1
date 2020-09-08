package comm;

import java.io.*;

public class Emisor {

	private OutputStream os;
	private BufferedWriter bw;
	
	public Emisor(OutputStream os) {
		this.os = os;
		bw = new BufferedWriter(new OutputStreamWriter(this.os));
	}
	
	public void sendMessage(String comando) {
		new Thread(
				()->{
					try {
						
						byte[] m1024 = new byte[1024];
						String msg1024 = new String(m1024);

						byte[] m8192 = new byte[8192];
						String msg8192 = new String(m8192);
						
						String comandoMandar = comando;

						if(comando.equalsIgnoreCase("RTT")) {
							comandoMandar = msg1024;
							double timeBefore = System.currentTimeMillis();
							bw.write(comandoMandar + "\n");
							bw.flush();
							double timeAfter = System.currentTimeMillis();
							double time = (timeAfter-timeBefore);
							System.out.println("Tiempo tomado: " + time + " ms");
						}else if(comando.equalsIgnoreCase("Speed")) {
							comandoMandar = msg8192;
							double timeBefore = System.currentTimeMillis();
							bw.write(comandoMandar + "\n");
							bw.flush();
							double timeAfter = System.currentTimeMillis();
							double time = timeAfter-timeBefore;
							double kb = ((comandoMandar.getBytes().length)/1000.0);
							if(time == 0) time = 0.0001;
							double speed = ((2*kb)/(time/1000));
							System.out.println("Speed: " + speed + " Kb/s");
						}else {
							bw.write(comandoMandar+"\n");
							bw.flush();
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				).start();
	}
}
