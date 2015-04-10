import java.io.*;
import java.net.*;

public class EnregistreurPageWeb
{
	final static int port = 80;
    public static void main (String args[])
    {
        if (args.length > 0 && args.length <=2) //si 2 parametre
        {
            try
            {
                URL url = new URL(args[0]);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.getResponseCode();
            }
            catch (IOException eos)
            {
                System.err.println("Adresse URL Invalide");
                System.exit(1);
            }
        }
        else
        {
            System.err.println("Nombre de paramètre invalide");
            System.exit(1);
        }

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

    static private void Enregistreur(String WebPage, String sortie)
    {
		try 
		{
			int numSubstring = 7;
			if (WebPage.contains("https"))
				numSubstring = 8;
			Socket socket = new Socket(InetAddress.getByName(WebPage.substring(numSubstring)), port);
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			PrintWriter fileWriter = 	new PrintWriter(new BufferedWriter(new FileWriter(sortie)));

			writer.println("GET / HTTP/1.1");
			writer.println("Host: " + WebPage.substring(numSubstring));
			writer.println("");		
			writer.flush();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String result;
			System.out.println("//////////////" + WebPage.substring(numSubstring) +"//////////////");
		
			while((result = reader.readLine()) != null)
			{
				System.out.println(result);
				fileWriter.println(result);
			}		
			reader.close();
			fileWriter.close();
		}
		catch (Exception ioex)
		{
			System.err.println("Impossible de de connecter a la page");
		}
    }
}