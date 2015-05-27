import static org.junit.Assert.*;
import me.kacshuffle.Game;

import org.junit.Before;
import org.junit.Test;


public class Tester {

	private Game game;
	
	@Before
	public void init() {
		game = new Game();
	}
	
	
	@Test
	public void getMaptest() {
		assertNotNull(game.getMap());
	}

	@Test(expected=NullPointerException.class)
	public void stepTest1() {
		game.setClickedValue("l"); 
	}
	
	@Test
	public void StepStorage() {
		int s = Integer.parseInt(game.stepcount());
		game.addstepStorage(0, 0);
		assertEquals("Size increase by 1",s+1,Integer.parseInt(game.stepcount()));
	}
}
