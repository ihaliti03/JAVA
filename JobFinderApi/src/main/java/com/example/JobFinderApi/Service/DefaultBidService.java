package com.example.JobFinderApi.Service;

import com.example.JobFinderApi.Pojo.Entity.Bid;
import com.example.JobFinderApi.Pojo.Entity.Work;
import com.example.JobFinderApi.Pojo.Entity.Worker;
import com.example.JobFinderApi.Pojo.Input.BidInput;
import com.example.JobFinderApi.Repository.BidRepository;
import com.example.JobFinderApi.Repository.WorkRepository;
import com.example.JobFinderApi.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultBidService implements BidService{

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkRepository workRepository;

    @Override
    public Bid createBid(BidInput bidInput,int worker_id, int work_id) {
        Worker worker = workerRepository.findWorkerById(worker_id);
        Work work = workRepository.findById(work_id);
        Bid bid = new Bid(bidInput.getPrice(),work,worker);
        return bidRepository.save(bid);
    }

    @Override
    public Bid acceptBid(int workId, int bidId) {
        Work work = workRepository.findById(workId);
        Bid bid = bidRepository.findById(bidId);
        bid.setWork(work);
        return bidRepository.save(bid);
    }
}
