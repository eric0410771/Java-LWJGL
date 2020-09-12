package displaymanager;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DisplayManager {
	private static final int WIDTH = 1600;
	private static final int HEIGHT = 1024;
	private static final int FPS = 60;
	private static long lastFrameTime;
	private static float delta;
	
	public void create()
	{
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create();
		}catch(LWJGLException e)
		{
			
		}
		lastFrameTime = getCurrentTime();
	}
	public void update() {
		Display.update();
		Display.sync(FPS);
		long currentFrameTime = getCurrentTime();
		delta = (float)(currentFrameTime-lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	public void destroy() {
		Display.destroy();
	}
	public long getCurrentTime()
	{
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public static float getFrameTimeSecond() {
		return delta;
	}
}
