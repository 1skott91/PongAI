package cv;

public class Vector 
{
	int x, y, z;
	
	public Vector(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector add(Vector vector)
	{
		x += vector.x;
		y += vector.y;
		z += vector.z;
		
		return this;
	}
	
	public Vector sub(Vector vector)
	{
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		
		return this;
	}
	
	@Override
	public String toString() 
	{
		return "x: " + x + " y: " + y;
	}

}
