import java.io.*;
import java.net.*;

public class EnregistreurPageWeb
{
	final static int port = 80;
    public static void main (String args[])
    {
	    if (args.length > 0 && args.length <=2)
        {
			try
			{
				if (args.length == 1)
				{
					Enregistreur(args[0],"page.html");
				}
				else
				{
					Enregistreur(args[0],args[1]);
				}
			}
			catch (Exception ioe)
			{
				System.err.println(ioe.getMessage());
			}
		}
        else
        {
            System.err.println("Nombre de paramètre invalide");
            System.exit(1);
        }
    }

    static private void Enregistreur(String WebPage, String sortie)
    {
		try
		{
			Socket socket = new Socket(InetAddress.getByName(WebPage), port);

			PrintWriter writer = new PrintWriter(socket.getOutputStream());

			PrintWriter fileWriter = 	new PrintWriter(new BufferedWriter(new FileWriter(sortie)));
		  BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			writer.println("GET / HTTP/1.1");
			writer.println("Host: " + WebPage);
			writer.println("");
            writer.flush();


			String result;

			while((result = reader.readLine()) != null)
			{
				fileWriter.println(result);
			}
			reader.close();
			fileWriter.close();
		}
		catch (Exception ioex)
		{
			System.err.println("Connexion a la page impossible");
		}
    }
}
