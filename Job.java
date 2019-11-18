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

import java.io.IOException;

import org.javasim.RestartException;
import org.javasim.Scheduler;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;
import org.javasim.streams.ExponentialStream;

public class Job
{
    public Job(double mean)
    {
        boolean empty = false;

        STime = new ExponentialStream(mean);
        
        ResponseTime = 0.0;
        ArrivalTime = Scheduler.currentTime();
        ServiceTime = generateServiceTime();
        
        M = null;

        empty = MachineShop.JobQ.isEmpty();
        MachineShop.JobQ.enqueue(this);
        MachineShop.TotalJobs++;

        if (!empty && !MachineShop.IdleQ.IsEmpty() )
        {
            try
            {
            	M = MachineShop.IdleQ.Dequeue();
            	M.activate();
            }
            catch (SimulationException e)
            {
            }
            catch (RestartException e)
            {
            }
        }
    }
    
    public void finished ()
    {
        ResponseTime = Scheduler.currentTime() - ArrivalTime;
        MachineShop.TotalResponseTime += ResponseTime;
        MachineShop.IdleQ.Enqueue(M);
        this.STime = new ExponentialStream(25);
        MachineShop.JobQ2.enqueue(this);
        if (!MachineShop.JobQ2.isEmpty() && !MachineShop.IdleQ2.IsEmpty()){
            this.ServiceTime = this.generateServiceTime();
            M = MachineShop.IdleQ2.Dequeue();
            try {
                M.activate();
            } catch (SimulationException e)
            {
            } catch (RestartException e)
            {
            }
        }
    }
    
    public void finished2 ()
    {
        MachineShop.IdleQ2.Enqueue(M);
        MachineShop.JobQ3.enqueue(this);
        this.STime = new ExponentialStream(40);
        if (!MachineShop.JobQ3.isEmpty() && !MachineShop.IdleQ3.IsEmpty()){
            this.ServiceTime = this.generateServiceTime();
            M = MachineShop.IdleQ3.Dequeue();
            try {
                M.activate();
            } catch (SimulationException e) 
            {
            } catch (RestartException e) 
            {
            }
        }
        
    }
    
    public void finished3 ()
    {
        MachineShop.IdleQ3.Enqueue(M);
        M = null;
    }
    
    public double generateServiceTime ()
    {
        try
        {
            return STime.getNumber();
        }
        catch (IOException e)
        {
            return 0.0;
        }
    }
    
    public double getServiceTime()
    {
    	return ServiceTime;
    }
    
    private ExponentialStream STime;

    private double ResponseTime;

    private double ArrivalTime;
    
    private double ServiceTime;
    
    public SimulationProcess M;
}
