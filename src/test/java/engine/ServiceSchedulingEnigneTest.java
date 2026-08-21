package engine;

import model.ServiceRequest; 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; 


public class ServiceSchedulingEnigneTest {

    @Test
    public void testDispatchSchedulePriorityOrdering() {
        ServiceSchedulingEngine engine = new ServiceSchedulingEngine();

        engine.receiveRequest(new ServiceRequest("REQ-1", "Zone A", 2));
        engine.receiveRequest(new ServiceRequest("REQ-2", "Zone B", 5)); // Highest priority
        engine.receiveRequest(new ServiceRequest("REQ-3", "Zone C", 1));

        ServiceRequest[] schedule = engine.generateDispatchSchedule();

        assertEquals("REQ-2", schedule[0].getRequestId()); // Critical priority first
        assertEquals("REQ-1", schedule[1].getRequestId());
        assertEquals("REQ-3", schedule[2].getRequestId()); // Lowest priority last
    }
    
}
