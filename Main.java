/*
 * Copyright 1990-2008, Mark Little, University of Newcastle upon Tyne
 * and others contributors as indicated 
 * by the @authors tag. All rights reserved. 
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors. 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 * 
 * (C) 1990-2008,
 */

package org.javasim.examples.SimulationWeek2;

public class Main
{
    public static void main (String[] args)
    {
        boolean isBreaks = false;

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equalsIgnoreCase("-help"))
            {
                System.out.println("Usage: Main [-breaks] [-help]");
                System.exit(0);
            }
            if (args[i].equalsIgnoreCase("-breaks"))
                isBreaks = true;
        }

        MachineShop m = new MachineShop(isBreaks);

        m.await();
        
        System.out.println("Original Lehmer generator test:");
        
        LehmerGenerator rng = new LehmerGenerator();
        for(int tests = 0; tests < 5; tests++) {
        	int testArraySize = (int)Math.pow(10, 2);
            double[] testArray = new double[testArraySize];
            double avg = 0;
            long categories = 5;
            int[] countArray = new int[(int)categories];
            for(int i = 0; i < testArraySize; i++) {
            	double next = rng.scaledNext();
            	testArray[i] = next;
            	avg += next;
            	if(next < 1.0/categories) countArray[0]++;
            	else if(next < 2.0/categories) countArray[1]++;
            	else if(next < 3.0/categories) countArray[2]++;
            	else if(next < 4.0/categories) countArray[3]++;
            	else countArray[4]++;
            }
            double smallest = countArray[0];
            double largest = countArray[0]; 
            for(int i : countArray) {
            	if (i < smallest) smallest = i;
            	else if (i > largest) largest = i;
            }
            avg = avg / testArraySize;
            double difference = 100*((largest - smallest) / smallest);
            
            System.out.println("Average:");
            System.out.println(avg);
            System.out.println("Percent difference between largest and smallest category:");
            System.out.println(difference);
        }
        

        System.exit(0);
    }
}
