package break_out.controller;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import break_out.Constants;

/**
 * The <code>JSONReader</code> reads the content of an json file.
 * 
 * @author dmlux, modified by I.Schumacher
 *
 */
public class JSONReader {

	/**
	 * The project path to the JSON file
	 */
	private String path;

	/**
	 * The stones stored as List<List<Long>>
	 */
	private List<List<Long>> rects = new ArrayList<List<Long>>();
		
	/**
	 * The stones stored as 2D-int-array
	 */
	private int[][] stones = new int[Constants.SQUARES_Y][Constants.SQUARES_X];
	
	/**
	 * The counter with the number of trials that are allowed to break out the stones of the level
	 */
	private Long lifecount = null;
	
	/**
	 * The constructor needs an path to create the JSONReader
	 * 
	 * @param path
	 *            The absolute path to the JSON file
	 */
	public JSONReader(String path) {
		this.path = path;
		loadJsonValues();
	}

	/**
	 * Getter for the stones of the JSON file
	 * 
	 * @return The list of long lists of stones
	 */
	public List<List<Long>> getStonesListOfLists() {
		return rects;
	}
	
	/**
	 * Getter for the stones of the JSON file
	 * 
	 * @return The stones as 2D-Array
	 */
	public int[][] getStones2DArray() {	
		for (int i = 0; i < rects.size(); i++) {
			for (int j = 0; j < rects.get(i).size(); j++) {
				stones[i][j] = (int)rects.get(i).get(j).longValue();
			}
		}
		return stones;
	}
	
	/**
	 * Getter for the lifeCounter of the JSON file
	 * 
	 * @return The lifeCounter
	 */
	public int getLifeCounter() {
		return (int)lifecount.longValue();
	}
	
	/**
	 * Loader for the both values "fields" and "maxDrops" of the JSON file
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void loadJsonValues() {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(path));

			String jsonStr = obj.toString();
			JSONObject json = (JSONObject) JSONValue.parse(jsonStr);
			rects = (List<List<Long>>) json.get("field");
			lifecount = (Long)json.get("maxLives");

		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

