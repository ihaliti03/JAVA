package com.jobFinder.api.Controller;

import com.jobFinder.api.Pojo.Entity.Bid;
import com.jobFinder.api.Pojo.Input.BidInput;
import com.jobFinder.api.Repository.BidRepository;
import com.jobFinder.api.Service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BidController {

    @Autowired
    private BidService bidService;

    @Autowired
    private BidRepository bidRepository;

    @GetMapping("bids")
    public Iterable<Bid> listBids() {
        return bidRepository.findAll();
    }

    @GetMapping("bid/{id}")
    public Bid getBid(@PathVariable int id) {
        return bidRepository.findById(id);
    }

    @PostMapping("/bid/{work_id}/{worker_id}")
    public Bid placeBid(@RequestBody BidInput bidInput, @PathVariable int work_id,@PathVariable int worker_id){
        return bidService.createBid(bidInput,worker_id,work_id);
    }

    @PostMapping("bid/accept/{workId}/{bidId}")
    public Bid acceptBid(@PathVariable int workId,@PathVariable int bidId) {
        return bidService.acceptBid(workId,bidId);
    }

    @DeleteMapping("bid/{id}")
    public void deleteBid(@PathVariable int id) {
        bidRepository.deleteById(id);
    }

}
