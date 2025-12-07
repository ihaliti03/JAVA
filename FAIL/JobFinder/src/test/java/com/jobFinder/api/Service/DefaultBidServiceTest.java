package com.jobFinder.api.Service;

import com.jobFinder.api.Pojo.Entity.*;
import com.jobFinder.api.Pojo.Input.BidInput;
import com.jobFinder.api.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DefaultBidServiceTest {

    @Mock
    private WorkRepository workRepository;

    @Mock
    private WorkerRepository workerRepository;

    @Mock
    private BidRepository bidRepository;

    @InjectMocks
    private DefaultBidService bidService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createBid_ShouldCreateBidSuccessfully() {
        BidInput bidInput = new BidInput();
        bidInput.setPrice(100.55);
        int workerId = 1;
        int workId = 1;
        Worker worker = new Worker();
        worker.setId(workerId);
        Work work = new Work();
        work.setId(workId);
        when(workerRepository.findWorkerById(workerId)).thenReturn(worker);
        when(workRepository.findById(workId)).thenReturn(work);
        Bid expectedBid = new Bid(100.0, work, worker);
        when(bidRepository.save(any(Bid.class))).thenReturn(expectedBid);

        Bid createdBid = bidService.createBid(bidInput, workerId, workId);

        assertNotNull(createdBid);
        assertEquals(expectedBid.getPrice(), createdBid.getPrice());
        assertEquals(expectedBid.getWork(), createdBid.getWork());
        assertEquals(expectedBid.getWorker(), createdBid.getWorker());
    }

    @Test
    public void createBid_ShouldReturnNullWhenWorkerNotFound() {
        BidInput bidInput = new BidInput();
        int invalidWorkerId = -1;
        int workId = 1;
        when(workerRepository.findWorkerById(invalidWorkerId)).thenReturn(null);

        Bid createdBid = bidService.createBid(bidInput, invalidWorkerId, workId);

        assertNull(createdBid);
    }

    @Test
    public void createBid_ShouldReturnNullWhenWorkNotFound() {
        BidInput bidInput = new BidInput();
        int workerId = 1;
        int invalidWorkId = -1;
        Worker worker = new Worker();
        worker.setId(workerId);
        when(workerRepository.findWorkerById(workerId)).thenReturn(worker);
        when(workRepository.findById(invalidWorkId)).thenReturn(null);

        Bid createdBid = bidService.createBid(bidInput, workerId, invalidWorkId);

        assertNull(createdBid);
    }

    @Test
    public void acceptBid_ShouldUpdateBidWithWork() {
        int workId = 1;
        int bidId = 1;
        Work work = new Work();
        work.setId(workId);
        Bid bid = new Bid();
        when(workRepository.findById(workId)).thenReturn(work);
        when(bidRepository.findById(bidId)).thenReturn(bid);
        Bid expectedBid = new Bid();
        expectedBid.setWork(work);
        when(bidRepository.save(any(Bid.class))).thenReturn(expectedBid);

        Bid updatedBid = bidService.acceptBid(workId, bidId);

        assertNotNull(updatedBid);
        assertEquals(expectedBid.getWork(), updatedBid.getWork());
    }

    @Test
    public void acceptBid_ShouldReturnNullWhenWorkNotFound() {
        int invalidWorkId = -1;
        int bidId = 1;
        when(workRepository.findById(invalidWorkId)).thenReturn(null);
        Bid bid = new Bid();
        when(bidRepository.findById(bidId)).thenReturn(bid);

        Bid updatedBid = bidService.acceptBid(invalidWorkId, bidId);

        assertNull(updatedBid);
    }



}