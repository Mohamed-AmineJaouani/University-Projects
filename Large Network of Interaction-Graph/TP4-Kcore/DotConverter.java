import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class DotConverter {
    
    public static void datConverter(String filename){
	String fichierSansExtension = filename.substring(0,filename.length()-4);

	fichierSansExtension = (fichierSansExtension.contains("-")? fichierSansExtension.replaceAll("-","") : fichierSansExtension);
	fichierSansExtension = (fichierSansExtension.contains("/")? fichierSansExtension.replaceAll("/","") : fichierSansExtension);

	FileReader fileReader = null;
	BufferedReader br = null;

	try{
	    fileReader = new FileReader(filename);
	    br = new BufferedReader(fileReader);
	    
	    PrintWriter writer = new PrintWriter(fichierSansExtension+".dot", "UTF-8");
	    
	    writer.println("digraph "+fichierSansExtension+" {");

	    String[] listeAdjacence;
	    
	    String line = "";

	    line = br.readLine();

	    int ind = 0;

	    do{
		listeAdjacence = line.trim().split(" ");

		for(int i = 0 ; i < listeAdjacence.length ; i++)
		    if(ind != i)
			if(listeAdjacence[i].equals("1")){
			    writer.println(ind+" -> "+ i +" ;");
			    writer.flush();
			}
		ind++;
	    }while((line = br.readLine()) != null);
	    
	    writer.println("}");
	    writer.close();
	    br.close();
	}catch(FileNotFoundException fnfe){
	    System.out.println("Fichier introuvable : "+filename);
	    System.exit(0);
	}
	catch(UnsupportedEncodingException e){
	    System.out.println("encodage introuvable : "+e);
	    System.exit(0);
	}
	catch(IOException e){
	    System.out.println("Erreur de lecture du fichier : "+e);
	    System.exit(0);
	}
    }
    
    public static void edgesConverter(String filename){
	String fichierSansExtension = filename.substring(0,filename.length()-6);
	fichierSansExtension = (fichierSansExtension.contains("-")? fichierSansExtension.replaceAll("-","") : fichierSansExtension);
	fichierSansExtension = (fichierSansExtension.contains("/")? fichierSansExtension.replaceAll("/","") : fichierSansExtension);

	try{
	    FileReader fileReader = new FileReader(filename);
	    BufferedReader br = new BufferedReader(fileReader);
	    
	    PrintWriter writer = new PrintWriter(fichierSansExtension+".dot", "UTF-8");
	    
	    writer.println("digraph "+fichierSansExtension+" {");

	    String line = br.readLine();
	    
	    String[] tab;
	    
	    do{
		tab = line.trim().split(" |\t");
		
		String aecrire="";
		aecrire+= tab[0];
		
		if(tab.length >= 2)
		    aecrire += " -> "+tab[1];
		aecrire += " ;";
		
		writer.println(aecrire);
		writer.flush();
		
	    }while((line = br.readLine()) != null);

	    writer.println("}");
	    writer.flush();
	    
	    br.close();
	    writer.close();
	    
	}catch(FileNotFoundException fnfe){
	    System.out.println("Fichier introuvable : "+filename);
	    System.exit(0);
	}catch(UnsupportedEncodingException e){
	    System.out.println("encodage introuvable : "+e);
	    System.exit(0);
	}
	catch(IOException e){
	    System.out.println("Erreur de lecture du fichier : "+e);
	    System.exit(0);
	}
    }
    
    public static void main(String[] args){
	if(args.length == 1){
	    if(args[0].length() > 4){
		String filename = args[0];

		
		String ext="";
		for(int i = filename.length()-1 ; i >=0 ; i--)
		    if(filename.charAt(i) == '.')
			ext += filename.substring(i, filename.length());
		
		switch(ext){
		case ".dat": datConverter(filename);
		    break;
		case ".edges": edgesConverter(filename);
		    break;
		default: System.out.println("extension non pris en compte");
		    break;
		}
	    }
	    else
		System.out.println("Entrez un nom de fichier valide");
	}
	else
	    System.out.println("Entrez un fichier en argument");
    }
}
