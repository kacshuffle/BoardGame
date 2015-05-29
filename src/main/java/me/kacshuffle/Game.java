package me.kacshuffle;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.*;


/**
 *  The logic of the game.
 * @author kacshuffle
 *
 */

public class Game {
	
	
	/**
	 * Actual position position in the table.
	 */
	private Coordinate actualPosition;
	
	
	/**
	 * Clicked position in the table.
	 */
	private Coordinate clickedPosition;
	
	
	/**
	 * @see Store the steps in a list type array
	 */
	private List<Coordinate> stepStorage = new ArrayList<Coordinate>();
	
	
	
	/**
	 * @see Store the steps in a list type array for the Gamer (enemy)
	 */
	private List<Coordinate> stepStorageForGamer = new ArrayList<Coordinate>();
	
	/**
	 * Model of the table.
	 */
	private Object o[][] = new Object[][] {
			{null, null, null, null, "jatekos", null, null},
			{null, null, null, "l", null, null, "l"},
			{"a", null, "l", null, null, null, null},
			{null, null, null, null, null, "l", null},
			{null, null, "a", null, null, null, null},
			{null, null, null, null, "a", null, null},
			{"a", null, "gamer", null, null, null, null},
	};
	/**
	 * 
	 * Structure to store data of the coordinates.
	 */

	class Coordinate {
		public int x;
		public int y;
		public Object value;

		public Coordinate(int x, int y){this.x = x; this.y = y;}
		public Coordinate(int x, int y, Object v){this.x = x; this.y = y; this.value = v;}
		public Coordinate(){this.x = 0; this.y = 0;}
	}

	/**
	 * Start place for the player and the opposite gamer.
	 */
	public Game(){
		addstepStorage(0,4); // ez nem 1-1
		actualPosition = new Coordinate(0,4);
		actualPosition.value = o[0][4];
		this.stepStorageForGamer.add(new Coordinate(6,2)); // így is hozzá férsz
	}

	/**
	 * Set the value of the clicked position.
	 * @param x Row coordinate
	 * @param y Column coordinate
	 */
	public void setclickedPosition(int x, int y){
		this.clickedPosition = new Coordinate(x,y, o[x][y]);
	}

	/**
	 * Add a new step.
	 * @param x Row coordinate
	 * @param y Column coordinate
	 */
	public void addstepStorage(int x, int y){
		stepStorage.add(new Coordinate(0,4));
	}
	/**
	 * Set the value of the clicked position.
	 * @param o Set the value of the clicked position.
	 */
	public void setClickedValue(Object o){
		clickedPosition.value = o;
	}

	/**
	 * Return back with the basic matrix.
	 * @return o Return back with the basic matrix for the table.
	 */
	public Object[][] getMap(){ return o; }

	/**
	 * Set the actual value.
	 * @param o Set the actual position value.
	 */
	public void setActValue(Object o){
		actualPosition.value = o;
	}
	/**
	 * Get the X coordinate from the last step.
	 * @return Coordinate which give back the X coordinate
	 */
	public int getLastStepX(){
		return stepStorage.get(stepStorage.size() - 1 ).x;
	}
	/**
	 * Get the Y coordinate from the last step.
	 * @return Coordinate which give back the Y coordinate
	 */
	public int getLastStepY(){
		return stepStorage.get(stepStorage.size() - 1 ).y;
	}
	/**
	 * Return with the count of steps.
	 * @return string
	 */
	public String stepcount(){
		return new Integer(stepStorage.size() -1).toString();
	}
	
	/**
	 * Handling the steps with the actual and the clicked position.
	 */
	public void step(){
		System.out.println("step-ba belépve");
		actualPosition = new Coordinate(stepStorage.get(stepStorage.size()-1).x, stepStorage.get(stepStorage.size()-1).y,
								o[stepStorage.get(stepStorage.size()-1).x][stepStorage.get(stepStorage.size()-1).y]);

		if(Math.abs(clickedPosition.x - actualPosition.x) == 1 && Math.abs(clickedPosition.y - actualPosition.y) == 0 ||
				Math.abs(clickedPosition.x - actualPosition.x) == 0 && Math.abs(clickedPosition.y - actualPosition.y) == 1) { 
			checkStepBox("jatekos", actualPosition, clickedPosition, actualPosition.value, clickedPosition.value);
			checkGamerStep();
		}
	}

	/**
	 * Wall detects, handling steps, where can the player move in the table and how.
	 * @param gamer The player.
	 * @param actualPosition Actual position.
	 * @param clickedPosition Clicked position, where the player wanna go.
	 * @param actValue Actual value.
	 * @param clickedPositionValue The clicked position value.
	 */
	private void checkStepBox(String gamer,Coordinate actualPosition, Coordinate clickedPosition, Object actValue, Object clickedPositionValue){
		String lGamer = "l-" + gamer;
		String aGamer = "a-" + gamer;
		System.out.println("lGamer: " + lGamer + " aGamer: " + aGamer);
		if(clickedPositionValue == null && gamer.equals(actValue)){ 
			setTableValue(gamer,null, actualPosition, clickedPosition);
		} else if(("jatekos".equals(actValue) || "gamer".equals(actValue)) && clickedPositionValue == "jatekos" || clickedPositionValue == "gamer"){ 

			System.out.printf("Ön Nyert");
			addstepStorage(0, 0);
			
			/* ----------------------------------- l doboz ------------------------------------ */
		} else if("l".equals(clickedPositionValue)) { // ha l-be lépsz, azonos x-en, actY - clikY == 1 && azonos y, x-ek kül. -1
			if(actualPosition.x == clickedPosition.x && (actualPosition.y - clickedPosition.y) == 1){ // jobbról balra lépek be
				setTableValue(gamer ,"l", actualPosition, clickedPosition);
			} else if (actualPosition.y == clickedPosition.y && (actualPosition.x - clickedPosition.x) == -1){ // ha fentről lépek be
				setTableValue(gamer ,"l", actualPosition, clickedPosition);
			}
		} else if(("l-gamer".equals(actValue) || "l-jatekos".equals(actValue)) && clickedPositionValue == null){ // ki akarok lépni l-jatekos-ból
			if(actualPosition.x == clickedPosition.x && (actualPosition.y - clickedPosition.y) == -1){ // jobbra lépek ki a l-jatekosból
				setTableValue(gamer, lGamer, actualPosition, clickedPosition);
			} else if (actualPosition.y == clickedPosition.y && (actualPosition.x - clickedPosition.x) == 1){ // felfele lépek ki l-jatekos-ból
				setTableValue(gamer, lGamer, actualPosition, clickedPosition);
			}
			/* ----------------------------------- a doboz ------------------------------------ */
		} else if ("a".equals(clickedPositionValue)){ // ha a-ba akarok lépni
			if(!(actualPosition.x == clickedPosition.x && (actualPosition.y - clickedPosition.y) == 1)){ // fent l-nek meg van már írva annak a negáltja ( balról ne engedje)
				setTableValue(gamer, "a", actualPosition, clickedPosition);
			}
		} else if (("a-gamer".equals(actValue) || "a-jatekos".equals(actValue)) && clickedPositionValue == null){ // ha ki akarok lépni üres területbe
			if(!(actualPosition.x == clickedPosition.x && (actualPosition.y - clickedPosition.y) == -1)){ // balról jobbra lépki ennek a negáltja
				setTableValue(gamer, aGamer, actualPosition, clickedPosition);
		} else if (("jatekos".equals(actValue) || "gamer".equals(actValue)) && clickedPositionValue == "jatekos" || clickedPositionValue == "gamer"){ // ha ki akarok lépni üres területbe
			System.out.println("Nyert");
			
		} 
		}
	}

	Coordinate gamerNewPos;
	Coordinate gameractualPosition;

	/**
	 * Handling the "enemy" player step.
	 */
	private void checkGamerStep(){
		gameractualPosition = new Coordinate(stepStorageForGamer.get(stepStorageForGamer.size() - 1).x, stepStorageForGamer.get(stepStorageForGamer.size() - 1).y,
									o[stepStorageForGamer.get(stepStorageForGamer.size()-1).x][stepStorageForGamer.get(stepStorageForGamer.size()-1).y]);
		gamerNewPos = new Coordinate();
		if(clickedPosition.x > actualPosition.x) { // ha x nőt akkor
			gamerNewPos.x = gameractualPosition.x - 1;
		} else if (clickedPosition.x < actualPosition.x) {
			gamerNewPos.x = gameractualPosition.x + 1;
		} else {
			gamerNewPos.x = gameractualPosition.x;
		}

		if(clickedPosition.y > actualPosition.y) { // ha y nőt akkor
			gamerNewPos.y = gameractualPosition.y - 1;
		} else if (clickedPosition.y < actualPosition.y) {
			gamerNewPos.y = gameractualPosition.y + 1;
		} else {
			gamerNewPos.y = gameractualPosition.y;
		}

		try {
			checkStepBox("gamer", gameractualPosition, gamerNewPos, o[gameractualPosition.x][gameractualPosition.y], o[gamerNewPos.x][gamerNewPos.y]);
		} catch (ArrayIndexOutOfBoundsException e){
			checkStepBox("gamer", gameractualPosition, gamerNewPos, o[gameractualPosition.x][gameractualPosition.y], o[gameractualPosition.x][gameractualPosition.y]);
		}

	}
	/**
	 * Sync the data from the Matrix to the step
	 * 
	 * @param gamer Player.
	 * @param c Value of the table
	 * @param actualPosition Actual position in the table.
	 * @param clickedPosition Clicked position in the table.
	 */
	public void setTableValue(String gamer,String c, Coordinate actualPosition, Coordinate clickedPosition){

		if (c == null){
			o[actualPosition.x][actualPosition.y] = null;
			o[clickedPosition.x][clickedPosition.y] = gamer; 
		} else if (c.length() == 1) {
			o[actualPosition.x][actualPosition.y] = null;
			o[clickedPosition.x][clickedPosition.y] = c + "-" + gamer;
		} else if (c.equals("a-gamer")|| c.equals("l-gamer") || c.equals("a-jatekos") || c.equals("l-jatekos") ){
			String temp = actualPosition.value.toString();
			System.out.println(temp);
			if(temp.substring(1).equals("-jatekos") || temp.substring(1).equals("-gamer")){
				o[actualPosition.x][actualPosition.y] = String.valueOf(temp.charAt(0));
				o[clickedPosition.x][clickedPosition.y] = gamer;
			}
		}

		if ("jatekos".equals(gamer)){
			stepStorage.add(new Coordinate(clickedPosition.x,clickedPosition.y));
		} else {
			stepStorageForGamer.add(new Coordinate(gamerNewPos.x,gamerNewPos.y));
		}
	}

}