
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
}
