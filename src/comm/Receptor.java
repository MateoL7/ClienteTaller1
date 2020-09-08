package comm;
import java.io.*;

public class Receptor extends Thread {

	private InputStream is;

	public OnMessageListener listener;

	public Receptor(InputStream is) {
		this.is = is;
	}

	@Override
	public void run() {
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(this.is));

			while(true) {
				String msg = br.readLine();
				if(listener!=null) listener.OnMessage(msg);
				else System.out.println("No hay Observer");

			}
		} catch(IOException e ) {
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