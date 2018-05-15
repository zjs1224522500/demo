package tech.shunzi.test;

import org.junit.Test;

import org.springframework.util.Assert;
import tech.shunzi.domain.Block;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	@Test
	public void testJava8Stream() {
		TestStream testStream = new TestStream();
//		testStream.getMultiStr().stream().forEach(str -> {
//			System.out.println(str);
//		});
		testStream.setInnerStr(new HashSet<>());

		Assert.isNull(testStream.getInnerStr(),"null");
		testStream.getInnerStr().stream().forEach(str -> {
			System.out.println(str.getAttr());
		});
	}

	class TestStream {
		String singleStr;
		List<String> multiStr;
		Set<InnerInnerClass> innerStr;

		class InnerInnerClass {
			String attr;

			public String getAttr() {
				return attr;
			}

			public void setAttr(String attr) {
				this.attr = attr;
			}
		}

		public String getSingleStr() {
			return singleStr;
		}

		public void setSingleStr(String singleStr) {
			this.singleStr = singleStr;
		}

		public List<String> getMultiStr() {
			return multiStr;
		}

		public void setMultiStr(List<String> multiStr) {
			this.multiStr = multiStr;
		}

		public Set<InnerInnerClass> getInnerStr() {
			return innerStr;
		}

		public void setInnerStr(Set<InnerInnerClass> innerStr) {
			this.innerStr = innerStr;
		}
	}
}
