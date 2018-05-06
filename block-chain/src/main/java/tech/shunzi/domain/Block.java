package tech.shunzi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tech.shunzi.util.StringUtils;

/**
 * Version:v1.0 (description:  ) Date:2018/5/4 0004  Time:23:03
 */
public class Block {

	private String hash;

	private String previousHash;

	private String merkleRoot;

	private long timestamp;

	private int nonce;

	public List<Transaction> transactions = new ArrayList<Transaction>(); //our merkleRoot will be a simple message.

	public Block(String previousHash) {
		this.previousHash = previousHash;
		this.timestamp = new Date().getTime();
		this.hash = generateHash();
	}

	public Block(String previousHash,String merkleRoot) {
		this.previousHash = previousHash;
		this.timestamp = new Date().getTime();
		this.merkleRoot = merkleRoot;
		this.hash = generateHash();
	}

	public String generateHash() {
		return StringUtils.encodeBySha256(
				previousHash + Long.toString(timestamp) + Integer.toString(nonce) + merkleRoot);
	}

	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty])
				.replace('\0', '0'); //Create a string with difficulty * "0"
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = generateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

	public boolean addTransaction(Transaction transaction) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if (null == transaction) {
			return false;
		}

		if ("0".equals(previousHash) && !transaction.processTransaction()) {
			System.out.println("Transaction failed to process. Discarded.");
			return false;
		}

		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getMerkleRoot() {
		return merkleRoot;
	}

	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
