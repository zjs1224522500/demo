package tech.shunzi.domain;

import com.google.gson.GsonBuilder;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import tech.shunzi.util.StringUtils;

/**
 * Version:v1.0 (description:  ) Date:2018/5/4 0004  Time:23:16
 */
public class BlockChain {

	public static ArrayList<Block> blockChain = new ArrayList<Block>();

	//list of all unspent transactions.
	public static HashMap<String, TransactionOutput> unspentTransactionOutputs = new HashMap<>();

	public static double minimumTransaction = 0.1f;

	public static Wallet walletA;

	public static Wallet walletB;

	public static int difficulty = 5;

	public static Transaction genesisTransaction;

	public static void main(String[] args) {
		//addSimpleBlock();
		//mineBlock();
		//testWallet();
		testWalletTwo();
	}

	public static Boolean isChainValid() {

		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		for (int i = 1; i < blockChain.size(); i++) {
			Block currentBlock = blockChain.get(i);
			Block previousBlock = blockChain.get(i - 1);

			if (!currentBlock.getHash().equals(currentBlock.generateHash())) {
				System.out.println("Current Block Hashes not equal !!!");
				return false;
			}

			if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				System.out.println("Previous Block Hashes not equal !!!");
				return false;
			}

			//check if hash is solved
			if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}

		return true;
	}

	public static void addSimpleBlock() {
		//add our blocks to the blockChain ArrayList:
		blockChain.add(new Block("0", "Hi im the first block"));
		blockChain.add(new Block(blockChain.get(blockChain.size() - 1).getHash(),
				"Yo im the second block"));
		blockChain.add(new Block(blockChain.get(blockChain.size() - 1).getHash(),
				"Hey im the third block"));

		String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
		System.out.println(blockChainJson);
	}

	public static void mineBlock() {
		blockChain.add(new Block("0", "Hi im the first block"));
		System.out.println("Trying to Mine block 1... ");
		blockChain.get(0).mineBlock(difficulty);

		blockChain.add(new Block(blockChain.get(blockChain.size() - 1).getHash(),
				"Yo im the second block"));
		System.out.println("Trying to Mine block 2... ");
		blockChain.get(1).mineBlock(difficulty);

		blockChain.add(new Block(blockChain.get(blockChain.size() - 1).getHash(),
				"Hey im the third block"));
		System.out.println("Trying to Mine block 3... ");
		blockChain.get(2).mineBlock(difficulty);

		System.out.println("\nblockChain is Valid: " + isChainValid());

		String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockChainJson);
	}

	public static void testWallet() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		walletA = new Wallet();
		walletB = new Wallet();

		//Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println("[Private:]" + StringUtils.getStringFromKey(walletA.getPrivateKey()));
		System.out.println("[Public:]" + StringUtils.getStringFromKey(walletA.getPublicKey()));

		//Create a test transaction from WalletA to walletB
		Transaction transaction = new Transaction(walletA.getPublicKey(), walletB.getPublicKey(), 5,
				null);
		transaction.generateSignature(walletA.getPrivateKey());

		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifySignature());
	}

	public static void testWalletTwo() {
		//add our blocks to the blockchain ArrayList:
		Security.addProvider(
				new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider

		//Create wallets:
		walletA = new Wallet();
		walletB = new Wallet();
		Wallet coinbase = new Wallet();

		//create genesis transaction, which sends 100 NoobCoin to walletA:
		genesisTransaction = new Transaction(coinbase.getPublicKey(), walletA.getPublicKey(), 100d,
				null);
		//manually sign the genesis transaction
		genesisTransaction.generateSignature(coinbase.getPrivateKey());
		//manually set the transaction id
		genesisTransaction.setTransactionId("0");
		//manually add the Transactions Output
		genesisTransaction.getOutputs().add(new TransactionOutput(genesisTransaction.getRecipient(),
				genesisTransaction.getValue(),
				genesisTransaction.getTransactionId()));
		//its important to store our first transaction in the UTXOs list.
		unspentTransactionOutputs.put(genesisTransaction.getOutputs().get(0).getId(),
				genesisTransaction.getOutputs()
						.get(0));

		System.out.println("Creating and Mining Genesis block... ");
		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction);
		addBlock(genesis);

		//testing
		Block block1 = new Block(genesis.getHash());
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
		block1.addTransaction(walletA.sendFunds(walletB.getPublicKey(), 40d));
		addBlock(block1);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Block block2 = new Block(block1.getHash());
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFunds(walletB.getPublicKey(), 1000d));
		addBlock(block2);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Block block3 = new Block(block2.getHash());
		System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
		block3.addTransaction(walletB.sendFunds(walletA.getPublicKey(), 20));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		isChainValid();

	}

	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockChain.add(newBlock);
	}

}
