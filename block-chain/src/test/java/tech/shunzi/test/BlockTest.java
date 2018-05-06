package tech.shunzi.test;

import org.junit.Test;

import tech.shunzi.domain.Block;

/**
 * Version:v1.0 (description:  ) Date:2018/5/4 0004  Time:23:13
 */
public class BlockTest {

	@Test
	public void testGenerateBlock() {

		Block genesisBlock = new Block("Hi im the first block", "0");
		System.out.println("Hash for block 1 : " + genesisBlock.getHash());

		Block secondBlock = new Block("Yo im the second block",genesisBlock.getHash());
		System.out.println("Hash for block 2 : " + secondBlock.getHash());

		Block thirdBlock = new Block("Hey im the third block",secondBlock.getHash());
		System.out.println("Hash for block 3 : " + thirdBlock.getHash());

		String target = new String(new char[10])
				.replace('\0', '0');
		System.out.println(target);
	}
}
