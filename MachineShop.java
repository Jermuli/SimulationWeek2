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

import org.javasim.RestartException;
import org.javasim.Scheduler;
import org.javasim.Simulation;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class MachineShop extends SimulationProcess
{
    public MachineShop(boolean isBreaks)
    {
        useBreaks = isBreaks;

        TotalResponseTime = 0.0;
        TotalJobs = 0;
        ProcessedJobs = 0;
        JobsInQueue = 0;
        CheckFreq = 0;
        MachineActiveTime = 0.0;
        MachineFailedTime = 0.0;
    }

    public void run ()
    {
        try
        {
            Breaks B = null;
            Arrivals A = new Arrivals(25);
            for(int i = 0; i < PrepRooms; i++) {
            	Machine newMachine = new Machine();
            	IdleQ.Enqueue(newMachine);
            }
            
            Machine machine2 = new Machine();
            IdleQ2.Enqueue(machine2);

            for(int i = 0; i < 3; i++){
                Machine newMachine3 = new Machine();
                IdleQ3.Enqueue(newMachine3);
            }
            
            Job J = new Job(40);
            monitor.activate();
            A.activate();
            
            if (useBreaks)
            {
                B = new Breaks();
                B.activate();
            }

            Simulation.start();
            hold(5000);
            monitor.restart();

            hold(5000);
            monitor.report();
            while (MachineShop.ProcessedJobs < 1000)
                hold(1000);

            System.out.println("Current time "+currentTime());
            System.out.println("Total number of jobs present " + TotalJobs);
            System.out.println("Total number of jobs processed "
                    + ProcessedJobs);
            System.out.println("Total response time of " + TotalResponseTime);
            System.out.println("Average response time = "
                    + (TotalResponseTime / ProcessedJobs));
            System.out
                    .println("Probability that machine is working = "
                            + ((MachineActiveTime - MachineFailedTime) / currentTime()));
            System.out.println("Probability that machine has failed = "
                    + (MachineFailedTime / MachineActiveTime));
            System.out.println("Average number of jobs present = "
                    + (JobsInQueue / CheckFreq));
            System.out.println("Preparations done " + Machine1Jobs + " times");
            System.out.println("Operations done " + Machine2Jobs + " times");
            System.out.println("Recovery done " + Machine3Jobs + " times");
            System.out.println("Time spent on preperation " + PrepTime);
            System.out.println("Time spent on operation " + OperTime);
            System.out.println("Time spent on recovery " + RecTime + "\n");

            Simulation.stop();

            A.terminate();
            monitor.terminate();
            while(!IdleQ.IsEmpty()) {
            	IdleQ.Dequeue().terminate();
            }
            while(!IdleQ2.IsEmpty()) {
                IdleQ2.Dequeue().terminate();
            }
            while(!IdleQ3.IsEmpty()) {
                IdleQ3.Dequeue().terminate();
            }

            if (useBreaks)
                B.terminate();

            SimulationProcess.mainResume();
        }
        catch (SimulationException e)
        {
        }
        catch (RestartException e)
        {
        }
    }

    public void await ()
    {
        this.resumeProcess();
        SimulationProcess.mainSuspend();
    }
    
    public static int PrepRooms = 3;
    
    public static ProcessQueue IdleQ = new ProcessQueue();
    
    public static ProcessQueue IdleQ2 = new ProcessQueue();
    
    public static ProcessQueue IdleQ3 = new ProcessQueue();
    
    public static Queue JobQ2 = new Queue();
    
    public static Queue JobQ3 = new Queue();
    
    public static Reporter monitor = new Reporter(100);
    
    public static Queue JobQ = new Queue();

    public static double TotalResponseTime = 0.0;

    public static long TotalJobs = 0;

    public static long ProcessedJobs = 0;

    public static long JobsInQueue = 0;

    public static long CheckFreq = 0;

    public static double MachineActiveTime = 0.0;

    public static double MachineFailedTime = 0.0;
    
    public static double PrepTime = 0.0;
    
    public static double OperTime = 0.0;
    
    public static double RecTime = 0.0;
    
    public static long Machine1Jobs = 0;
    
    public static long Machine2Jobs = 0;
    
    public static long Machine3Jobs = 0;

    private boolean useBreaks;
}
