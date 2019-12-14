package break_out.model;

import java.awt.Color;

public class Stone implements IStone{

	/**
	 * the type, value, colour and position of the stone
	 */
	private int type;
	private int value;
	private Color color;
	private Position position;
	
	/**
	 * the constructor for a stone with its type and position
	 * @param type the stones type
	 * @param position the stones position
	 */
	
	public Stone(int type, Position position) {
		this.type = type;
		this.position = position;
		
		if(type == 1) {
			this.value = 5;
			this.color = Color.darkGray;
		}else if(type == 2) {
			this.value = 10;
			this.color = Color.blue;
		}else if(type == 3) {
			this.value = 15;
			this.color = Color.red;
		}
	}
	
	/**
	 * getter for the stone type
	 * @return type the stone type
	 */
	@Override
	public int getType() {
		return this.type;
	}
	
	/**
	 * getter for the stone value
	 * @return value the stone value
	 */
	@Override
	public int getValue() {
		return this.value;
	}
	
	/**
	 * getter for the stone color
	 * @return type the stone color
	 */
	@Override
	public Color getColor() {
		return this.color;
	}

	/**
	 * getter for the stone position
	 * @return type the stone position
	 */
	@Override
	public Position getPosition() {
		return this.position;
	}

	/**
	 * setter for the stone type
	 * @param type the stone type
	 */
	@Override
	public void setType(int type) {
		this.type = type;
		if(type == 1) {
			this.value = 5;
			this.color = Color.darkGray;
		}else if(type == 2) {
			this.value = 10;
			this.color = Color.blue;
		}else if(type == 3) {
			this.value = 15;
			this.color = Color.red;
		}
	}

	/**
	 * setter for the stone value
	 * @param type the stone value
	 */
	@Override
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * setter for the stone color
	 * @param type the stone color
	 */
	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * setter for the stone position
	 * @param type the stone position
	 */
	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

}
