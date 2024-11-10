package com.example.webService.Blockchain.domain.model;

import java.util.ArrayList;

public class Blockchain {
    private ArrayList<Block> blockchain = new ArrayList<>();
    private int difficulty = 4;

    public Blockchain() {
        // El bloque génesis se crea en el servicio si es necesario
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Block createGenesisBlock() {
        return new Block(new ArrayList<>(), "0");
    }

    public Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    public boolean isChainValid() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Hashes actuales no coinciden.");
                return false;
            }
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Hashes previos no coinciden.");
                return false;
            }
        }
        return true;
    }
}
