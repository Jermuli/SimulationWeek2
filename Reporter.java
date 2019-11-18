package org.javasim.examples.SimulationWeek2;

import org.javasim.*;
import org.javasim.stats.*;
import org.javasim.simset.*;

import java.io.IOException;


public class Reporter extends SimulationProcess
{
public Reporter(long interval)
{
    check=interval;
}
public void run ()
{
    for (;;)
    {
        try
        {
        hold(check);
        }
        catch (SimulationException e)
        {
        }
        catch (RestartException e)
        {
        }

        checkcount++;
        avgQue += MachineShop.JobQ.queueSize();
        avgUtil += MachineShop.PrepTime / (MachineShop.PrepRooms*check);
    }
}
public void report()
{
    System.out.println("Checks "+checkcount);
    System.out.println("Average que time for preparation is: " + (avgQue/checkcount));
    System.out.println("Average utilization of preparation rooms is: " + (avgUtil/checkcount)+"%\n");
}
public void restart()
{
checkcount=0;
avgQue = 0;
avgUtil = 0;
}

private long check;
private long checkcount;
private long avgUtil;
private long avgQue;

}