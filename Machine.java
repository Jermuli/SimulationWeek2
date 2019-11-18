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
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;
import org.javasim.streams.ExponentialStream;

public class Machine extends SimulationProcess
{
    public Machine()
    {
        operational = true;
        working = false;
        J = null;
    }

    public void run ()
    {
        double ActiveStart, ActiveEnd;

        while (!terminated())
        {
            working = true;

            while (!MachineShop.JobQ.isEmpty())
            {
                ActiveStart = currentTime();
                MachineShop.CheckFreq++;

                MachineShop.JobsInQueue += MachineShop.JobQ.queueSize();
                J = MachineShop.JobQ.dequeue();
                

                try
                {
                    hold(J.getServiceTime() );
                }
                catch (SimulationException e)
                {
                }
                catch (RestartException e)
                {
                }

                ActiveEnd = currentTime();
                MachineShop.MachineActiveTime += ActiveEnd - ActiveStart;
                MachineShop.ProcessedJobs++;
                MachineShop.Machine1Jobs++;
                MachineShop.PrepTime += ActiveEnd - ActiveStart;

                /*
                 * Introduce this new method because we usually rely upon the
                 * destructor of the object to do the work in C++.
                 */

                J.finished();
            }

            while (!MachineShop.JobQ2.isEmpty())
            {
                ActiveStart = currentTime();
                MachineShop.CheckFreq++;

                MachineShop.JobsInQueue += MachineShop.JobQ2.queueSize();
                J = MachineShop.JobQ2.dequeue();
                

                try
                {
                    hold(J.getServiceTime() );
                }
                catch (SimulationException e)
                {
                }
                catch (RestartException e)
                {
                }

                ActiveEnd = currentTime();
                MachineShop.MachineActiveTime += ActiveEnd - ActiveStart;
                MachineShop.ProcessedJobs++;
                MachineShop.Machine2Jobs++;
                MachineShop.OperTime += ActiveEnd - ActiveStart;

                /*
                 * Introduce this new method because we usually rely upon the
                 * destructor of the object to do the work in C++.
                 */

                J.finished2();
            }
            while (!MachineShop.JobQ3.isEmpty())
            {
                ActiveStart = currentTime();
                MachineShop.CheckFreq++;

                MachineShop.JobsInQueue += MachineShop.JobQ2.queueSize();
                J = MachineShop.JobQ3.dequeue();
                

                try
                {
                    hold(J.getServiceTime() );
                }
                catch (SimulationException e)
                {
                }
                catch (RestartException e)
                {
                }

                ActiveEnd = currentTime();
                MachineShop.MachineActiveTime += ActiveEnd - ActiveStart;
                MachineShop.ProcessedJobs++;
                MachineShop.Machine3Jobs++;
                MachineShop.RecTime += ActiveEnd - ActiveStart;

                /*
                 * Introduce this new method because we usually rely upon the
                 * destructor of the object to do the work in C++.
                 */

                J.finished3();
            }
            
            working = false;

            try
            {
                cancel();
            }
            catch (RestartException e)
            {
            }
        }
    }

    public void broken ()
    {
        operational = false;
    }

    public void fixed ()
    {
        operational = true;
    }

    public boolean isOperational ()
    {
        return operational;
    }

    public boolean processing ()
    {
        return working;
    }

    public Job getCurrentJob() {
    	return J;
    }

    private boolean operational;

    private boolean working;

    private Job J;
}
