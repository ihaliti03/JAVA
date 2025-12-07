package com.jobFinder.api.Service;

import com.jobFinder.api.Pojo.Entity.Bid;
import com.jobFinder.api.Pojo.Entity.Work;
import com.jobFinder.api.Pojo.Entity.Worker;
import com.jobFinder.api.Pojo.Input.BidInput;
import com.jobFinder.api.Repository.BidRepository;
import com.jobFinder.api.Repository.WorkRepository;
import com.jobFinder.api.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
