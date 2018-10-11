package generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import processor.pipeline.*;
import processor.Clock;

public class EventQueue {
	
	PriorityQueue<Event> queue;
	
	public EventQueue()
	{
		queue = new PriorityQueue<Event>(new EventComparator());
	}
	
	public void addEvent(Event event)
	{
		queue.add(event);
	}

	public void processEvents()
	{
		while(queue.isEmpty() == false && queue.peek().getEventTime() <= Clock.getCurrentTime())
		{
			Event event = queue.poll();
			event.getProcessingElement().handleEvent(event);
		}
	}
	
	public boolean isEmpty() {
		if(queue.size() == 0) {
			return true;
		}
		else 
			return false;
	}
	
	public void printElements() {
		// create iterator from the queue
	      Iterator it = queue.iterator();

	      System.out.println("Priority queue values are: ");
	      
	      while (it.hasNext()) {
	    	 Event test = (Event) it.next();
	         System.out.println("Current time -> : "+ Clock.getCurrentTime() +" Time Schedule -> : "+ test.getEventTime() + " Requesting Element -> : "+ test.getRequestingElement() + " Processing element -> : "+ test.getProcessingElement()); 
	      }
	}
	
	public void deleteElements(long currentClock) {

		System.out.println("jwiarhfiohwihiw"+queue.size());
		Iterator<Event> itr=queue.iterator();
		Event e;
		while(itr.hasNext()) {
			e=itr.next();
			if(e.requestingElement instanceof InstructionFetch  ) {
				
				System.out.println("kefnhoerfqbkhewfkhio");
			//	((InstructionFetch)e.requestingElement).IF_OF_Latch.setOF_enable(true);
				/*Commenting the above because the new instruction comes with a latency if you open IF_OF_LAtch so you take the previous saved value again-
				 * IOW the value in OF_Latch will be over written after 40 cycles, and if you open IF_OF_Latch now then it will take the same older instruction and pass it to the Execute stage*/
				((InstructionFetch)e.requestingElement).IF_EnableLatch.setIF_busy(false);
				itr.remove();
				
			}
		}
		System.out.println("jwiarhfiohwihiw"+queue.size());
		
//		Collections.sort(nums, new EventComparator());

//		System.out.println(nums);
		
//		Iterator it2 = queue.iterator();
		
//		while (it.hasNext()) {
//			Event test = (Event) it.next();
//			if(test.getEventTime() == nums.get(nums.size() - 1).getEventTime() || test.getEventTime() == nums.get(nums.size() - 2).getEventTime()) {
//				it.remove();
//			}
	}
		
		
}


class EventComparator implements Comparator<Event>
{
	@Override
    public int compare(Event x, Event y)
    {
		if(x.getEventTime() < y.getEventTime())
		{
			return -1;
		}
		else if(x.getEventTime() > y.getEventTime())
		{
			return 1;
		}
		else
		{
			return 0;
		}
    }
}
