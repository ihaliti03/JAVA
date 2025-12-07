package com.jobFinder.api.Service;

import com.jobFinder.api.Pojo.Entity.Bid;
import com.jobFinder.api.Pojo.Entity.Work;
import com.jobFinder.api.Pojo.Entity.Worker;
import com.jobFinder.api.Pojo.Input.BidInput;

public interface BidService {
    Bid createBid(BidInput bidInput,int worker_id, int work_id);
    Bid acceptBid(int workId,int bidId);
}
