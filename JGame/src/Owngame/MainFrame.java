package Owngame;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.*;
import java.util.ArrayList;


public class MainFrame {
	private Airplane airplane;
	private long lastframe;
	private int delta;
	private boolean isover = true;
	private ArrayList<Point> pointlist;
	private class Airplane {
		public boolean right;
		public boolean left;
		public boolean up;
		public boolean down;
		public boolean shout;
		private int x;
		private int y;
		private int width = 40;
		private int height = 40;
		public Airplane() {
			x= 300;
			y = 300;
		}
		public void draw()
		{
			glBegin(GL_LINES);
				glVertex2i(x+20,y);
				glVertex2i(x+15,y+5);
				glVertex2i(x+15,y+5);
				glVertex2i(x+15,y+15);
				glVertex2i(x+15,y+15);
				glVertex2i(x+10,y+20);
				glVertex2i(x+10,y+20);
				glVertex2i(x+30,y+20);
				glVertex2i(x+30,y+20);
				glVertex2i(x+25,y+15);
				glVertex2i(x+25,y+15);
				glVertex2i(x+25,y+5);
				glVertex2i(x+25,y+5);
				glVertex2i(x+20,y);
			glEnd();
			
		}	
		
		private int getX() {
			return x;
		}
		private int getY() {
			return y;
		}
		
		private void update(double dx,double dy)
		{
			this.x+=(int)dx;
			this.y+=(int)dy;
		}
	}
	private class Point{
		public int x;
		public int y;
		private PointThread pointthread;
		
		public Point()
		{
			pointthread = new PointThread();
			x = airplane.getX()+20;
			y = airplane.getY();
			pointthread.start();
		}
		private void draw()
		{
			glRecti(x-5,y-5,x+10,y+10);
		}
		private class PointThread extends Thread{
			public void run()
			{
				while(y>0)
				{
					try {
						
						Thread.sleep(50);
						y-=5;
					}catch(InterruptedException e)
					{
					}
				}
				pointlist.remove(Point.this);
			}
		}
	}
	private class PointCheckThread extends Thread{
		
	}
	private class PointShoutThread extends Thread{
		public void run()
		{
			while(true)
			{
				try {
					Thread.sleep(200);
					if(airplane.shout)
					{	
						pointlist.add(new Point());
					}
				}catch(InterruptedException e)
				{}
			}
		}
	}
	private class AirplaneThread extends Thread{
		private double dx;
		private double dy;
		public AirplaneThread() {
			dx = 0;
			dy = 0;
		}
		public void run()
		{
			while(true)
			{
				try {
					Thread.sleep(50);
					dx = 0;
					dy = 0;
					if(airplane.right)
					{
						dx += 0.3;
					}
					if(airplane.left)
					{
						dx -=0.3;
					}
					if(airplane.up)
					{
						dy -=0.3;
					}
					if(airplane.down)
					{
						dy +=0.3;
					}
					airplane.update(delta*dx,delta*dy);
				}catch(InterruptedException e)
				{
					
				}
			}
		}
	}
	private long getTime() {
		return (Sys.getTime()*1000)/(Sys.getTimerResolution());
	}
	
	private int getDelta() {
		long current = getTime();
		int delta = (int)(current-lastframe);
		lastframe = getTime();
		return delta;
	}
	
	public MainFrame(){
		try {
			Display.setDisplayMode(new DisplayMode(640,480));
			Display.setTitle("test1");
			Display.create();
		}catch(LWJGLException e)
		{	
		}
		
		airplane = new Airplane();
		pointlist = new ArrayList<Point>();
		AirplaneThread airplanethread = new AirplaneThread();
		PointShoutThread pointshoutthread = new PointShoutThread();
		PointCheckThread pointcheckthread = new PointCheckThread();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,640,480,0,-1,1);
		glMatrixMode(GL_MODELVIEW);
		
		airplanethread.start();
		pointshoutthread.start();
		pointcheckthread.start();
		
		ArrayList<Point> copylist = new ArrayList<Point>();
		
		while(!Display.isCloseRequested())
		{
			glClear(GL_COLOR_BUFFER_BIT);
			
			
			while(Keyboard.next())
			{
				if(Keyboard.getEventKey()==Keyboard.KEY_RIGHT)
				{	
					if(Keyboard.getEventKeyState())
						airplane.right = true;
					else 
						airplane.right = false;
				}
				else if(Keyboard.KEY_LEFT==Keyboard.getEventKey())
				{
					if(Keyboard.getEventKeyState())
						airplane.left = true;
					else 
						airplane.left = false;
				}
				else if(Keyboard.KEY_UP==Keyboard.getEventKey())
				{
					if(Keyboard.getEventKeyState())
						airplane.up = true;
					else 
						airplane.up = false;
				}
				else if(Keyboard.getEventKey()==Keyboard.KEY_DOWN)
				{
					if(Keyboard.getEventKeyState())
						airplane.down = true;
					else 
						airplane.down = false;
				}
				if(Keyboard.getEventKey()==Keyboard.KEY_Z)
				{
					if(Keyboard.getEventKeyState())
						airplane.shout = true;
					else 
						airplane.shout = false;
				}
			}
			
			if(isover)
			{
				isover = false;
				copylist = pointlist;
			}

			for(int i =0 ; i<copylist.size();i++)
			{
				copylist.get(i).draw();
				if(i==copylist.size()-1)
					isover = true;
			}
			delta = getDelta();
			airplane.draw();
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
		
	}
	
	public static void main(String args[])
	{
		new MainFrame();
	}
}
