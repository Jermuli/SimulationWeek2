package org.javasim.examples.SimulationWeek2;

public class LehmerGenerator 
{
	public LehmerGenerator() {
		this.X = System.currentTimeMillis();
	}
	
	public LehmerGenerator(long seed) {
		this.X = seed;
	}
	
	private long X;
	
	private long a = 23;
	
	private long m = (long) (Math.pow(10, 8) + 1);
	
	public double next() {
		X = (a*X) % m;
		return X;
	}
	
	public double scaledNext() {
		return (this.next() / m);
	}
}
