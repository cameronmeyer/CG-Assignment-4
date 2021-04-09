// Cameron Meyer
// Assignment 4 - Task B - Pythagoras
// CS 4361.001


import java.awt.*;
import java.awt.event.*;


public class pythagoras extends Frame {
   public static void main(String[] args) {new pythagoras();}

   pythagoras() {
      super("Pythagoras Window");
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {System.exit(0);}
      });
      setSize(400, 300);
      add("Center", new CvPythagoras());
      setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      setVisible(true);
   }
}

class CvPythagoras extends Canvas {
   int centerX, centerY;
   float pixelSize, rWidth = 10.0F, rHeight = 10.0F, xP = 1000000, yP;
   float AX, AY, BX, BY;
   boolean pointADone = false;
   boolean isInit = true;

   CvPythagoras() {
      addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent e) {
        	 Graphics g = getGraphics();
        	 
        	 if(pointADone) {
        		 BX = fx(e.getX());  
	    		 BY = fy(e.getY());
	    		 pointADone = false;
	    		 repaint(); 
        	 } else {
        		 AX = fx(e.getX());  
	    		 AY = fy(e.getY());
	    		 pointADone = true;
	    		 isInit = false; 
	    		 
	    		 repaint();
        	 }
         }
      });
   }

   int iX(float x) {return Math.round(centerX + x / pixelSize);}
   int iY(float y) {return Math.round(centerY - y / pixelSize);}
   float fx(int x) {return (x - centerX) * pixelSize;}
   float fy(int y) {return (centerY - y) * pixelSize;}

   public void paint(Graphics g) {
	  Dimension d = getSize();
	  int maxX = d.width - 1, maxY = d.height - 1;
	  pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
	  centerX = maxX / 2; centerY = maxY / 2;
      
	  //Calculate points C and D based on the vector perpendicular to AB
	  float CX = BX + (AY - BY);
	  float CY = BY + (BX - AX);  
	  float DX = AX + (AY - BY);
	  float DY = AY + (BX - AX);

	  if(!isInit) {
		  //g.drawString("A", iX(AX)-3, iY(AY)-3);
		  //g.fillOval(iX(AX)-3, iY(AY)-3, 6, 6);
	  
		  if(!pointADone) {
			  //Draw vertices of square
			  //g.fillOval(iX(BX)-3, iY(BY)-3, 6, 6);
			  //g.fillOval(iX(CX)-3, iY(CY)-3, 6, 6);
			  //g.fillOval(iX(DX)-3, iY(DY)-3, 6, 6);
      
			  //Label points
			  //g.drawString("B", iX(BX)-3, iY(BY)-3);
			  //g.drawString("C", iX(CX)-3, iY(CY)-3);
			  //g.drawString("D", iX(DX)-3, iY(DY)-3);
      
			  //Draw edges of square
			  g.drawLine(iX(AX), iY(AY), iX(BX), iY(BY)); //A to B
			  g.drawLine(iX(BX), iY(BY), iX(CX), iY(CY)); //B to C
			  g.drawLine(iX(CX), iY(CY), iX(DX), iY(DY)); //C to D
			  g.drawLine(iX(DX), iY(DY), iX(AX), iY(AY)); //D to A
		  }
	  }
   }
}
