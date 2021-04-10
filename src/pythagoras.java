// Cameron Meyer
// Assignment 4 - Task B - Pythagoras
// CS 4361.001


import java.awt.*;
import java.awt.event.*;


public class pythagoras extends Frame implements ActionListener
{
	protected MenuItem pythagoras, exit;
	protected Menu menu;
	
	public static void main(String[] args) {new pythagoras();}

	pythagoras()
	{
		super("Pythagoras Window");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) {System.exit(0);}
		});
      
		MenuBar menuBar = new MenuBar();
		setMenuBar(menuBar);
		menu = new Menu("Options");
		menuBar.add(menu);
		
		pythagoras = new MenuItem("Pythagoras", new MenuShortcut(KeyEvent.VK_P));
		exit = new MenuItem("Quit", new MenuShortcut(KeyEvent.VK_Q));
		menu.add(pythagoras);
		menu.add(exit);
		
		pythagoras.addActionListener(this);
		exit.addActionListener(this);
      
		setSize(400, 300);
		add("Center", new CvPythagoras());
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() instanceof MenuItem)
		{
	        MenuItem menuItem = (MenuItem) ae.getSource();
	        
	        if(menuItem == pythagoras)
	        {
	        	CvPythagoras.selectPythagoras();
	        }
	        else if(menuItem == exit)
	        {
	        	System.exit(0);
	        }
		}
	}
}

class CvPythagoras extends Canvas
{
	int centerX, centerY;
	float pixelSize, rWidth = 10.0F, rHeight = 10.0F, xP = 1000000, yP;
	static boolean pythagorasSelected = false;
	boolean pointADone = false;
	boolean isInit = true;
	Vertex A = new Vertex(0, 0);
	Vertex B = new Vertex(0, 0);

	CvPythagoras()
	{
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				Graphics g = getGraphics();
				
				// Ensure Pythagoras was selected from the options menu before recording points
				if(pythagorasSelected)
				{
					if(pointADone)
					{
						B.x = fx(e.getX());  
						B.y = fy(e.getY());
						pointADone = false;
						repaint(); 
					}
					else
					{
						A.x = fx(e.getX());  
						A.y = fy(e.getY());
						pointADone = true;
						isInit = false; 
    	    		 
						repaint();
					} 
				}
			}
		});
	}
	
	// Call when Pythagoras was selected from the options menu
	public static void selectPythagoras() { pythagorasSelected = true; }

	int iX(float x) {return Math.round(centerX + x / pixelSize);}
	int iY(float y) {return Math.round(centerY - y / pixelSize);}
	float fx(int x) {return (x - centerX) * pixelSize;}
	float fy(int y) {return (centerY - y) * pixelSize;}

	public void paint(Graphics g)
	{
		Dimension d = getSize();
		int maxX = d.width - 1, maxY = d.height - 1;
		pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
		centerX = maxX / 2; centerY = maxY / 2;

		if(!isInit)
		{
			if(!pointADone)
			{
				//Draw base of square using points A and B given by the user
				g.drawLine(iX(A.x), iY(A.y), iX(B.x), iY(B.y)); //A to B
				drawSquare(A, B, g); // Begin recursion with a square
			}
		}
	}
	
	public void drawSquare(Vertex A, Vertex B, Graphics g)
	{
		Vertex C = new Vertex(0, 0);
		Vertex D = new Vertex(0, 0);
		
		// Calculate points C and D of square perpendicular to A and B
		C.x = B.x + (A.y - B.y);
		C.y = B.y + (B.x - A.x);  
		D.x = A.x + (A.y - B.y);
		D.y = A.y + (B.x - A.x);
		
		// Only need to draw remaining 3 sides of square
		g.drawLine(iX(B.x), iY(B.y), iX(C.x), iY(C.y)); //B to C
		g.drawLine(iX(C.x), iY(C.y), iX(D.x), iY(D.y)); //C to D -- this line acts as base of the triangle
		g.drawLine(iX(D.x), iY(D.y), iX(A.x), iY(A.y)); //D to A
		
		drawTriangle(C, D, g); // Draw a triangle on top of the square
	}
	
	public void drawTriangle(Vertex C, Vertex D, Graphics g)
	{
		// Calculate third point of 45 45 90 right triangle
		Vertex E = new Vertex(0, 0);	
		E.x = (C.x - C.y + D.x + D.y) / 2;
		E.y = (C.x + C.y - D.x + D.y) / 2;
		
		// Only need to draw remaining 2 sides of triangle -- each will be a base to a new square
		g.drawLine(iX(C.x), iY(C.y), iX(E.x), iY(E.y)); //C to E
		g.drawLine(iX(D.x), iY(D.y), iX(E.x), iY(E.y)); //D to E
		
		// If points are far enough apart, recursively draw two more squares
		if(distance(C, E) > 0.1f)
		{
			drawSquare(E, C, g);
			drawSquare(D, E, g);
		}
	}
	
	public float distance(Vertex P, Vertex Q)
	{
		// Return the distance between two points
		return (float) Math.sqrt(Math.pow((Q.y - P.y), 2) + Math.pow((Q.x - P.x), 2));
	}
}

class Vertex
{
	float x, y;
	Vertex(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
}
