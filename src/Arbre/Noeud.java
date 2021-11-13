package Arbre;

public class Noeud {
	
	String Valeur;
	Noeud FilsGauche;
	Noeud FilsDroit;
	
	//constructeur pour variable ou constante
	public Noeud(String valeur) {
		this.Valeur=valeur;
		this.FilsDroit=null;
		this.FilsGauche=null;
	}

	//constructeur pour fonction unaire
	public Noeud(String valeur,Noeud FD) {
		this.Valeur=valeur;
		this.FilsDroit=FD;
		this.FilsGauche=null;
	}

	//constructeur général
	public Noeud(String valeur,Noeud FG,Noeud FD) {
		this.Valeur=valeur;
		this.FilsDroit=FD;
		this.FilsGauche=FG;
	}

	public String getValeur() {return Valeur;}
	public Noeud getFilsGauche() {return FilsGauche;}
	public Noeud getFilsDroit() {return FilsDroit;}

	public void setValeur(String valeur) {Valeur = valeur;}
	public void setFilsGauche(Noeud filsGauche) {FilsGauche = filsGauche;}
	public void setFilsDroit(Noeud filsDroit) {FilsDroit = filsDroit;}
	
}
