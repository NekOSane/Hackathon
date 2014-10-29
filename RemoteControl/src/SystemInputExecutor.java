import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


public class SystemInputExecutor {

	private static SystemInputExecutor instance;
	private Robot robot = new Robot();

	private SystemInputExecutor() throws AWTException {
		robot.setAutoDelay(40);
    	robot.setAutoWaitForIdle(true);

		//robot.delay(4000);
	    //robot.mouseMove(40, 130);
	}

	public static SystemInputExecutor getInstance() throws AWTException {
		if (instance == null)
			instance = new SystemInputExecutor();

		return instance;
	}

	public void key(int keyEvent) {
		robot.delay(40);
		robot.keyPress(keyEvent);
      	robot.keyRelease(keyEvent);
	}

	public void left() {
		key(KeyEvent.VK_LEFT);
	}

	public void right() {
		key(KeyEvent.VK_RIGHT);
	}

	public void up() {
		key(KeyEvent.VK_UP);
	}

	public void down() {
		key(KeyEvent.VK_DOWN);
	}

	private void down(int times) {
		for (int i = 0; i < times; i++) {
			robot.delay(2000);
	      	robot.keyPress(KeyEvent.VK_DOWN);
	      	robot.keyRelease(KeyEvent.VK_DOWN);
	    }
	}

	private void leftClick() {
	    robot.mousePress(InputEvent.BUTTON1_MASK);
	    robot.delay(200);
	    robot.mouseRelease(InputEvent.BUTTON1_MASK);
	    robot.delay(200);
  	}
}
