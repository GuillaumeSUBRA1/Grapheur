import Arbre.*;
import Operateurs.*;

public class Evaluateur {
	public static float Calcul(Noeud n) {
		float x=0;
		try {
			switch (n.getValeur()) {
				case "x":
					break;
				case "+":
					x=Calcul(n.getFilsGauche()) + Calcul(n.getFilsDroit());
					break;
				case "-":
					if(n.getFilsGauche()==null) {Calcul(n.getFilsDroit());}
					else{x=Calcul(n.getFilsGauche())-Calcul(n.getFilsDroit());}
					break;
				case "*":
					x=Calcul(n.getFilsGauche()) * Calcul(n.getFilsDroit());
					break;
				case "/":
					x=Calcul(n.getFilsGauche()) / Calcul(n.getFilsDroit());
					break;
				case "cos":
					x=Cosinus.res(Calcul(n.getFilsDroit()));
					break;
				case "sin":
					x=Sinus.res(Calcul(n.getFilsDroit()));
					break;
				case "tan":
					x=Tangente.res(Calcul(n.getFilsDroit()));
					break;
				case "abs":case "abs-":
					x=Absolue.res(Calcul(n.getFilsDroit()));
					break;
				case "^":
					x=Puissance.res(Calcul(n.getFilsGauche()),Calcul(n.getFilsDroit()));
					break;
				case "rac":
					x=(float)RacineCarree.res((double)Calcul(n.getFilsDroit()));
					break;
				default:
					x+=Float.parseFloat(n.getValeur());
					break;
				}
		}catch (Exception e){e.printStackTrace();}
		System.out.println("x="+x);
		return x;
	}
	
	
	public static Noeud createArbre(String eq) {
		Noeud n=new Noeud(null);
		if(eq.length()>0 && parentheses(eq)) {
			String fg,fd;
			
			//parenth�ses
			if(eq.indexOf("(")==0&&posParentheseFermee(eq)==eq.length()-1) {
				n=createArbre(eq.substring(1,posParentheseFermee(eq)));
			}else if(eq.indexOf("(")==0&&posParentheseFermee(eq)<eq.length()-1) {
				fg=eq.substring(1,posParentheseFermee(eq));
				fd=eq.substring(posParentheseFermee(eq)+2,eq.length());
				n=new Noeud(Character.toString(eq.charAt(posParentheseFermee(eq)+1)),createArbre(fg),createArbre(fd));
			}else if(eq.indexOf("(")>0&&posParentheseFermee(eq)==eq.length()-1) {
				fg=eq.substring(0,eq.indexOf("(")-1);
				fd=eq.substring(eq.indexOf("(")+1,posParentheseFermee(eq));
				n=new Noeud(Character.toString(eq.charAt(eq.indexOf("(")-1)),createArbre(fg),createArbre(fd));
			}else if(eq.indexOf("(")>0&&posParentheseFermee(eq)<eq.length()-1) {
				fg=eq.substring(0,eq.indexOf("(")-1);
				fd=eq.substring(eq.indexOf("("),eq.length());
				n=new Noeud(Character.toString(eq.charAt(eq.indexOf("(")-1)),createArbre(fg),createArbre(fd));
			
			//op�rateurs + et -
			} else if(eq.contains("+")&&(eq.contains("-")&&eq.lastIndexOf("-")>0&&((eq.charAt(eq.lastIndexOf("-")-1)==')')||(Character.isDigit(eq.lastIndexOf("-")-1))))) {
				if(eq.lastIndexOf("+")>eq.lastIndexOf("-")){
					fg=eq.substring(0,eq.lastIndexOf("+"));
					fd=eq.substring(eq.lastIndexOf("+")+1,eq.length());
					n=new Noeud("+",createArbre(fg),createArbre(fd));
				} else if(eq.lastIndexOf("+")<eq.lastIndexOf("-")){
					fg=eq.substring(0,eq.lastIndexOf("-"));
					fd=eq.substring(eq.lastIndexOf("-")+1,eq.length());
					n=new Noeud("-",createArbre(fg),createArbre(fd));
				} else if(eq.lastIndexOf("-")<eq.lastIndexOf("+")){
					fg=eq.substring(0,eq.lastIndexOf("+"));
					fd=eq.substring(eq.lastIndexOf("+")+1,eq.length());
					n=new Noeud("+",createArbre(fg),createArbre(fd));
				}
		} else if (!eq.contains("+")&&(eq.contains("-"))&&eq.lastIndexOf("-")>0&&((eq.charAt(eq.lastIndexOf("-")-1)==')')||(Character.isDigit(eq.lastIndexOf("-")-1)))){
					fg=eq.substring(0,eq.lastIndexOf("-"));
					fd=eq.substring(eq.lastIndexOf("-")+1,eq.length());
					n=new Noeud("-",createArbre(fg),createArbre(fd));
			} else if (eq.contains("+")&&!eq.contains("-")){
					fg=eq.substring(0,eq.lastIndexOf("+"));
					fd=eq.substring(eq.lastIndexOf("+")+1,eq.length());
					n=new Noeud("+",createArbre(fg),createArbre(fd));
					
			//op�rateurs * et /
			} else if(eq.contains("*")&&!eq.contains("**")&&eq.contains("/")) {
				if(eq.lastIndexOf("*")<eq.lastIndexOf("/")){
					fg=eq.substring(0,eq.lastIndexOf("*"));
					fd=eq.substring(eq.lastIndexOf("*")+1,eq.length());
					n=new Noeud("*",createArbre(fg),createArbre(fd));
				} else {
					fg=eq.substring(0,eq.lastIndexOf("/"));
					fd=eq.substring(eq.lastIndexOf("/")+1,eq.length());
					n=new Noeud("/",createArbre(fg),createArbre(fd));
				}
			} else if (eq.contains("*")&&!eq.contains("**")&&!eq.contains("/")){
				fg=eq.substring(0,eq.lastIndexOf("*"));
				fd=eq.substring(eq.lastIndexOf("*")+1,eq.length());
				n=new Noeud("*",createArbre(fg),createArbre(fd));
			} else if (eq.contains("/")&&!eq.contains("*")){
				fg=eq.substring(0,eq.lastIndexOf("/"));
				fd=eq.substring(eq.lastIndexOf("/")+1,eq.length());
				n=new Noeud("/",createArbre(fg),createArbre(fd));
				
			//puissance
			} else if(eq.contains("^")) {
				int debut=0;
				for(int i=eq.lastIndexOf("^");i>=eq.length();i--) {
					if(!Character.isDigit(eq.charAt(i))) {debut=i+1;break;}
				}
				fg=eq.substring(debut,eq.lastIndexOf("^"));
				fd=eq.substring(eq.indexOf("^")+1,eq.length());
				n=new Noeud("^",new Noeud(fg),new Noeud(fd));
				
			//fonctions trigonom�triques
			} else if(eq.contains("cos")) {
				fd=eq.substring(eq.indexOf("cos")+3,eq.length());
				n=new Noeud("cos",new Noeud(fd));
			} else if(eq.contains("sin")) {
				fd=eq.substring(eq.indexOf("sin")+3,eq.length());
				n=new Noeud("sin",new Noeud(fd));
			} else if(eq.contains("tan")) {
				fd=eq.substring(eq.indexOf("tan")+3,eq.length());
				n=new Noeud("tan",new Noeud(fd));
				
			//valeur absolue
			} else if(eq.contains("abs")) {
				if(eq.charAt(3)=='-') {fd=eq.substring(eq.indexOf("abs")+3,eq.length());} 
				else {fd=eq.substring(eq.indexOf("abs-")+4,eq.length());}
				n=new Noeud("abs",new Noeud(fd));
			
			//racine carr�e
			} else if(eq.contains("rac")) {
				fd=eq.substring(eq.indexOf("rac")+3,eq.length());
				n=new Noeud("rac",new Noeud(fd));
				
			//nombre n�gatif
			} else if(eq.contains("-")&&eq.lastIndexOf("-")==0&&isDigit(eq.substring(1,eq.length()))) {
				n=new Noeud("-",new Noeud(eq));
				
			//constante ou variable
			} else if(Character.isDigit(eq.charAt(0))||(eq.charAt(0)=='x')) {
				n=new Noeud(eq);
			}
		}
		return n;
	}	
	
	
	//v�rificaiton des parenth�ses
	public static boolean parentheses(String eq) {
		int parenthese=0;
		for(int i=0;i<eq.length();i++) {
			if(eq.charAt(i)=='(') {parenthese++;}
			else if(eq.charAt(i)==')') {parenthese--;}
		}
		if(parenthese!=0) {return false;}
		return true;
	}
	
	//fonction utilis�e pour d�tecter un nombre n�gatif
	public static boolean isDigit(String eq) {
		for(int i=1;i<eq.length();i++) {
			if(!Character.isDigit(eq.charAt(i))||eq.charAt(i)!='.') {
				return false;
			} 
		}
		return true;
	}
	
	//fonction renvoyant l'indice de la parenth�se ferm�e correspondant � la celle de l'indice 0 
	public static int posParentheseFermee(String eq) {
		int pos=0;
		int parenthese=1;
		for(int i=eq.indexOf("(")+1;i<eq.length();i++) {
			if(eq.charAt(i)=='(') {parenthese++;}
			else if(eq.charAt(i)==')') {parenthese--;}
			if(parenthese==0) {System.out.println("pos="+i);pos=i;break;}
		}
		return pos;
	}
}
