package mn.aurora.seller;

public class DataManager {

	private static int form;
	private static String move;
	private static String date;
	private static String shop;
	
	
	public static void setForm(int value){
		form = value;
	}
	public static int getForm(){
		return form;
	}
	
	public static void setMove(String value){
		move = value;
	}
	public static String getMove(){
		return move;
	}
	
	public static void setDate(String vDate){
		date = vDate;
		
	}
	public static String getDate(){
		return date;
	}	
	
	public static void setShop(String vShop){
		shop = vShop;
		
	}
	public static String getShop(){
		return shop;
	}
}
