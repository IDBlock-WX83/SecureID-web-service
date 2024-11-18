package com.example.webService.Blockchain.interfaces.rest;

import com.example.webService.Blockchain.domain.model.Block;
import com.example.webService.Blockchain.domain.model.Identification;
import com.example.webService.Blockchain.domain.services.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {
    private final BlockchainService blockchainService;

    @Autowired
    public BlockchainController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    //para gaurar iamgnes
    /*
    *
    * @PostMapping("/addIdentification")
public void addIdentification(
        @RequestParam("identification") Identification identification,
        @RequestParam("firma") MultipartFile firma,
        @RequestParam("dniFrontal") MultipartFile dniFrontal,
        @RequestParam("dniPosterior") MultipartFile dniPosterior) {

    try {
        blockchainService.addIdentification(
                identification,
                firma.getBytes(),
                dniFrontal.getBytes(),
                dniPosterior.getBytes());
    } catch (IOException e) {
        e.printStackTrace();
    }
}
*/

    @PostMapping("/addIdentification")
    public void addIdentification(@RequestBody Identification identification) {
        blockchainService.addIdentification(identification);
    }

    @GetMapping("/validate")
    public boolean validateBlockchain() {
        return blockchainService.validateBlockchain();
    }

    @GetMapping("/blocks")
    public List<Block> getBlockchain() {
        return blockchainService.getBlockchain();
    }
  /* @GetMapping("/blocks")
   public @ResponseBody List<Block> getBlockchain() {
       List<Block> blockchain = blockchainService.getBlockchain();
       blockchain.forEach(block -> {
           System.out.println("Block hash: " + block.getHash());
           block.getIdentifications().forEach(id -> System.out.println("Identification ID: " + id.getId()));
       });
       return blockchain;
   }*/

    @GetMapping("/getIdentification/{id}")
    public ResponseEntity<Identification> getIdentification(@PathVariable Long id) {
        Optional<Identification> identification = blockchainService.getIdentificationById(id);
        return identification.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
