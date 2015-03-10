
public enum Direction 
{
	NORTH,
	EAST,
	SOUTH,
	WEST;
	
	public static Direction toDirection(int i)
	{
		switch(i)
		{
		case 0: return NORTH;
		case 1: return EAST;
		case 2: return SOUTH;
		case 3: return WEST;
		default: return null;
		}
	}
	
	public Direction opposite()
	{
		switch(this)
		{
		case NORTH: return SOUTH;
		case EAST: return WEST;
		case SOUTH: return NORTH;
		case WEST: return EAST;
		default: return null;
		}
	}
	
	public int toInt()
	{
		switch(this)
		{
		case NORTH: return 0;
		case EAST: return 1;
		case SOUTH: return 2;
		case WEST: return 3;
		default: return -1;
		}
	}
}
