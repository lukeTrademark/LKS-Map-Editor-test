package WorldChunkManager;

public class Vertex 
{
	double x = 0;
	double y = 0;
	double z = 0;
	double u = 0;
	double v = 0;
	public Vertex(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void setUV(double u, double v) 
	{
		this.u = u;
		this.v = v;
	}
	public double getX() 
	{
		return x;
	}
	public double getY() 
	{
		return y;
	}
	public double getZ() 
	{
		return z;
	}
}
