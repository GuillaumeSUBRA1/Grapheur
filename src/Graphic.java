import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Graphic extends JComponent implements Runnable {

	private static final long serialVersionUID = 1L;
	
	float min=-10;
	float max=10;
	float pas=1;
	String eq="x";
	
	float plage=Math.abs(min)+max;
	
	int width=800; 
	int height=800;
	
	int GrapheX,GrapheY;
	
	
	
	Point SourisPt;
	
	public Graphic(float min, float max, float pas, String eq) {
		setPreferredSize(new Dimension(width,height));
		setMinimumSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		this.min=min;
		this.max=max;
		this.pas=pas;
		this.eq=eq;
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx=e.getX()-SourisPt.x;
                int dy=e.getY()-SourisPt.y;
                GrapheX-=dx/width*1200;
                GrapheY+=dy/height*1200;
                SourisPt=e.getPoint();
                repaint();
            }
        });
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx=e.getX()-SourisPt.x;
                int dy=e.getY()-SourisPt.y;
                GrapheX-=dx/width*1200;
                GrapheY+=dy/height*1200;
                SourisPt=e.getPoint();
                repaint();
            }
        });
		
		setFocusable(true);
		requestFocusInWindow();
		
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.blue);
		g.drawRect(0, 0, width, height);
		
		//traçage des axes
		g.drawLine(width/2, 0, width/2, height);
		g.drawLine(0, height/2, width, 400);
		g.drawString("0",width/2+5,height/2+15);
		
		
		//traçage des traits de pas
		if(pas>0) {
			for(int i=0;i<=(int)plage;i++) {//horizontale
				g.drawLine((int)(width-((width/plage)*i)), height/2, (int)(width-((width/plage)*i)), height/2-6);		
				g.drawLine(width/2,(int)(height-((height/plage)*i)), width/2+6, (int)(height-((height/plage)*i)));
			}
		}

		
		
		if(!(eq==null)||!eq.isEmpty()) {
			for(float i=min;i<=max;i+=pas) {
				System.out.println("i="+i);
				String eq1="";
				String eq2="";
				float a=i;
				
				//la commande replace ne fonctionnant pas pour une raison inconnue,
				//la substitution de 'x' dans l'expression se fait manuellement 
				if(eq.contains("x")) {
					if(eq.length()>1&&(eq.indexOf("x")==eq.length()-1)) {
						eq1=eq.substring(0,eq.indexOf("x"))+String.valueOf(i);
					}else if(eq.length()>1&&eq.indexOf("x")==0){
						eq1=String.valueOf(i)+eq.substring(eq.indexOf("x")+1,eq.length());
					}else if(eq.length()>1&&eq.indexOf("x")!=0&&eq.indexOf("x")!=eq.length()-1){
						eq1=eq.substring(0,eq.indexOf("x"))+String.valueOf(i)+eq.substring(eq.indexOf("x")+1,eq.length());
					}else if(eq.length()==1) {
						eq1=Float.toString(i);
					}
				} else {eq1=eq;}
				
				float x1=Evaluateur.Calcul(Evaluateur.createArbre(eq1));
				System.out.println("x1="+x1);

				if(eq.contains("x")) {
					if(eq.length()>1&&(eq.indexOf("x")==eq.length()-1)) {
						eq2=eq.substring(0,eq.indexOf("x"))+String.valueOf(i+1);
					}else if(eq.length()>1&&eq.indexOf("x")==0){
						eq2=String.valueOf(i+1)+eq.substring(eq.indexOf("x")+1,eq.length());
					}else if(eq.length()>1&&eq.indexOf("x")!=0&&eq.indexOf("x")!=eq.length()-1){
						eq2=eq.substring(0,eq.indexOf("x"))+String.valueOf(i+1)+eq.substring(eq.indexOf("x")+1,eq.length());
					}else if(eq.length()==1) {
						eq2=Float.toString(i+1);
					}
				} else {eq2=eq;}
				
				float x2=Evaluateur.Calcul(Evaluateur.createArbre(eq2));
				System.out.println("x2="+x2);
				
				if(x1<0) {g.setColor(Color.RED);}
				else {g.setColor(Color.GREEN);}
				
				
				//traçage d'une ligne reliant le point de coodonnées (x,f(x)) au point de coordonnées (x+1,f(x+1))
				if(x1<0&&x2<0) {g.drawLine((int)((width/2)-(((width/2)/(plage/2))*i)),(int)(((height/2))-(x1*(((height/2)/(plage/2))))),(int)((width/2)-((((width/2)/(plage/2))))*(i+1)),(int)(((height/2))-(x2*((height/2)/(plage/2)))));}
				else if(x1>=0&&x2<0) {g.drawLine((int)((width/2)+(((width/2)/(plage/2))*i)),(int)(((height/2))+(x1*(((height/2)/(plage/2))))),(int)((width/2)+((((width/2)/(plage/2))))*(i+1)),(int)(((height/2))-(x2*((height/2)/(plage/2)))));}
				else if(x1<0&&x2>=0) {g.drawLine((int)((width/2)-(((width/2)/(plage/2))*i)),(int)(((height/2))-(x1*(((height/2)/(plage/2))))),(int)((width/2)-((((width/2)/(plage/2))))*(i+1)),(int)(((height/2))+(x2*((height/2)/(plage/2)))));}
				else if(x1>=0&&x2>=0) {g.drawLine((int)((width/2)+(((width/2)/(plage/2))*i)),(int)(((height/2))-(x1*(((height/2)/(plage/2))))),(int)((width/2)+((((width/2)/(plage/2))))*(i+1)),(int)(((height/2))-(x2*((height/2)/(plage/2)))));}
				System.out.println("eq="+Evaluateur.Calcul(Evaluateur.createArbre(eq)));
			}
			
			
		}else {System.out.println("vide");}
	
	
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub	
	}
}
