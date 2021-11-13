import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;


public class Fenetre extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	//composantes
	Float Xmin;
	Float Ymin;
	Float Xmax;
	Float Ymax;
	Float Pas;
	
	JPanel Coordonnees= new JPanel();
	JPanel Action= new JPanel();
	JPanel Grapher= new JPanel();
	JPanel Evaluation= new JPanel();
	
	//composants NORTH
	JLabel x=new JLabel("x = ");
	JLabel y=new JLabel("y = ");
	JLabel fonctionPos=new JLabel("f(x) = ");
	
	JTextArea SourisxText=new JTextArea(0,6);
	JTextArea SourisyText=new JTextArea(0,6);
	JTextArea fonctionText=new JTextArea(0,6);
	
	//composants WEST
	JLabel xmin=new JLabel("x min");
	JLabel xmax=new JLabel("x max");
	JLabel ymin=new JLabel("y min");
	JLabel ymax=new JLabel("y max");
	JLabel pas=new JLabel("pas");
	JLabel xgrid=new JLabel("x grid");
	JLabel ygrid=new JLabel("y grid");
	
	JTextArea xminText=new JTextArea(1,10);
	JTextArea xmaxText=new JTextArea(1,10);
	JTextArea yminText=new JTextArea(1,10);
	JTextArea ymaxText=new JTextArea(1,10);
	JTextArea pasText=new JTextArea(1,10);
	JTextArea xgridText=new JTextArea(1,10);
	JTextArea ygridText=new JTextArea(1,10);
	
	JButton Refresh=new JButton("Refresh");
	JButton ZoomPlus=new JButton("+");
	JButton ZoomMoins=new JButton("-");
	
	JButton eval=new JButton("Eval");
	JLabel fonctionEval=new JLabel("f(x) = ");
	JTextArea fonctionEvalText=new JTextArea(0,30);
	
	public Fenetre () {
		super("Grapher");
		setSize(1200,1200);
		setVisible(true);
	
		
		Coordonnees.add(x);
		Coordonnees.add(SourisxText);
		Coordonnees.add(y);
		Coordonnees.add(SourisyText);
		Coordonnees.add(fonctionPos);
		Coordonnees.add(fonctionText);
		
		xminText.setText("-10");
		xmaxText.setText("10");
		yminText.setText("-10");
		ymaxText.setText("10");
		pasText.setText("0.2");
		
		Action.setLayout(null);
		Action.setPreferredSize(new Dimension(120,800));
		xmin.setBounds(5, 5, 50, 20);Action.add(xmin);
		xminText.setBounds(55, 5, 50, 20);Action.add(xminText);
		xmax.setBounds(5, 35, 50, 20);Action.add(xmax);
		xmaxText.setBounds(55, 35, 50, 20);Action.add(xmaxText);
		ymin.setBounds(5, 65, 50, 20);Action.add(ymin);
		yminText.setBounds(55, 65, 50, 20);Action.add(yminText);
		ymax.setBounds(5, 95, 50, 20);Action.add(ymax);
		ymaxText.setBounds(55, 95, 50, 20);Action.add(ymaxText);
		pas.setBounds(5, 125, 50, 20);Action.add(pas);
		pasText.setBounds(55, 125, 50, 20);Action.add(pasText);
		xgrid.setBounds(5, 155, 50, 20);Action.add(xgrid);
		xgridText.setBounds(55, 155, 50, 20);Action.add(xgridText);
		ygrid.setBounds(5, 185, 50, 20);Action.add(ygrid);
		ygridText.setBounds(55, 185, 50, 20);Action.add(ygridText);
		ZoomPlus.setBounds(5, 215, 50, 20);Action.add(ZoomPlus);
		ZoomMoins.setBounds(55, 215, 50, 20);Action.add(ZoomMoins);
		Refresh.setBounds(5, 245, 100, 20);Action.add(Refresh);
		Refresh.addActionListener(this);
		
		fonctionEvalText.setText("x^2");
		Evaluation.add(eval);
		Evaluation.add(fonctionEval);
		Evaluation.add(fonctionEvalText);
		
		Grapher.add(new Graphic(Float.parseFloat(xminText.getText()),Float.parseFloat(xmaxText.getText()),Float.parseFloat(pasText.getText()),fonctionEvalText.getText()));
		Grapher.setBackground(Color.WHITE);
		eval.addActionListener(this);
		
	    this.getContentPane().add(Coordonnees,BorderLayout.NORTH);
	    this.getContentPane().add(Action,BorderLayout.WEST);
	    this.getContentPane().add(Evaluation,BorderLayout.SOUTH);
	    this.getContentPane().add(Grapher,BorderLayout.CENTER);
		
	    pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String f=fonctionEvalText.getText();
		Xmin=Float.parseFloat(xminText.getText());
		Xmax=Float.parseFloat(xmaxText.getText());
		Pas=Float.parseFloat(pasText.getText());
		
		if(source==eval) {
			Graphic g;
			Grapher.removeAll();
			if(!f.isEmpty()) {
				g=new Graphic(Float.parseFloat(xminText.getText()),Float.parseFloat(xmaxText.getText()),(Float.parseFloat(pasText.getText())),f);
			}else {g=new Graphic(Float.parseFloat(xminText.getText()),Float.parseFloat(xmaxText.getText()),(float)0.1,f);}
			Grapher.add(g);
			Float res=Evaluateur.Calcul(Evaluateur.createArbre(fonctionEvalText.getText()));
			if(!(f.contains("x"))) {fonctionEvalText.setText(Float.toString(res));}
			revalidate();
			repaint();
		} else if (source==Refresh) {
			Grapher.removeAll();
			Grapher.add(new Graphic(Float.parseFloat(xminText.getText()),Float.parseFloat(xmaxText.getText()),(Float.parseFloat(pasText.getText())),f));
		}

	}
	
	public static void main(String [] args){
	      JFrame f = new Fenetre();
	      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
	
	
	
}
