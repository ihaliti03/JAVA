package com.example.JobFinderApi.Service;

import com.example.JobFinderApi.Pojo.Entity.Bid;
import com.example.JobFinderApi.Pojo.Input.BidInput;

public interface BidService {
    Bid createBid(BidInput bidInput,int worker_id, int work_id);
    Bid acceptBid(int workId,int bidId);
}
