import java.io.FileInputStream;
import jxl.Workbook;
import java.io.File;
import jxl.Sheet;
import jxl.Cell;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class ExcelParser{

    public static void main(String[] args){
	try{
	    File excel = new File(args[0]);
	    Workbook w = Workbook.getWorkbook(excel);
	    Sheet s = w.getSheet(0);
	    Cell cell;
	    PrintWriter pw = new PrintWriter(new FileOutputStream(new File("bd.txt")));
	    String line = "";
	    for(int i = 1 ; i < 3637 ; i++){
		line = "";
		for(int j = 2 ; j < 10 ; j++){
		    cell = s.getCell(j,i);
		    line += cell.getContents()+"|";
		}
		pw.println(line);
		pw.flush();
	    }
	    w.close();
	}catch(Exception e){
	    System.out.println(e);
	}
    }
}

//age(fait), nom concatené avec id et prenom, profession, domaine, ligne "vide" a completer (fait) , fonction random(fait), 
