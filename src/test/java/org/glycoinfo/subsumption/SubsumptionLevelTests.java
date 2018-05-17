package org.glycoinfo.subsumption;

import java.io.IOException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;

public class SubsumptionLevelTests {
//	public static Logger logger = (Logger) LoggerFactory
//			.getLogger("org.glycoinfo.subsumptionTests");
	
	@Test
	public void SubsumptionConvertertest() throws IOException {
		Subsumption_Converter testSCNormal = new Subsumption_Converter();
		Subsumption_Converter testSCCyclic = new Subsumption_Converter();
		Subsumption_Converter testSCRepeat = new Subsumption_Converter();
		Subsumption_Converter testSCBranch = new Subsumption_Converter();
		Subsumption_Converter testSCAlternative = new Subsumption_Converter();
		Subsumption_Converter testSCtopology = new Subsumption_Converter();
		
		String WURCSseqNormal = null;
		String WURCSseqCyclic = null;
		String WURCSseqRepeat = null;
		String WURCSseqBranch = null;
		String WURCSseqAlternative = null;
		String WURCSseqtopology = null;
		
		// WURCSの変換（レベル１からレベル２）
		// １つのWURCSの文字列をWURCSseqに入れる
		
		// Normal structure
		WURCSseqNormal = "WURCS=2.0/4,4,3/[o222h][a2112h-1b_1-5_2*NCC/3=O][a2112h-1a_1-5_2*NCC/3=O][a2122h-1a_1-5_2*NCC/3=O]/1-2-3-4/a5-b1_b3-c1_c4-d1";
		//WURCSseqNormal = "WURCS=2.0/6,10,9/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1b_1-5_2*NCC/3=O]/1-2-3-4-2-5-4-2-6-2/a4-b1_b4-c1_c4-j1_d2-e1_e4-f1_g2-h1_h4-i1_d1-c3|c6_g1-c3|c6";
		//WURCSseqNormal = "WURCS=2.0/4,13,12/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a1221m-1x_1-5][a2112h-1x_1-5]/1-1-2-2-1-3-4-1-4-2-1-4-1/a?-b1_b?-c1_c?-d1_c?-j1_d?-e1_d?-h1_e?-f1_e?-g1_h?-i1_j?-k1_j?-m1_k?-l1";
		//WURCSseqNormal = "WURCS=2.0/7,8,7/[o2122m_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2122h-1a_1-5_2*NCC/3=O][a2112h-1b_1-5][Aad21122h-2a_2-6_5*NCC/3=O]/1-2-3-4-5-6-7-4/a4-b1_b4-c1_c3-d1_c6-h1_d2-e1_e4-f1_f3-g2";
		//WURCSseqNormal = "WURCS=2.0/2,2,1/[u2112h_2*NSO/3=O/3=O][a21EEA-1a_1-5]/1-2/a3-b1";
		//WURCSseqNormal = "WURCS=2.0/4,7,6/[a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O_4*OSO/3=O/3=O]/1-1-2-3-1-4-3/a4-b1_b4-c1_c3-d1_c6-g1_d2-e1_e4-f1";
		//WURCSseqNormal = "WURCS=2.0/4,5,4/[u2122h_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5]/1-2-3-4-4/a4-b1_b4-c1_c3-d1_c6-e1";
		
		// Cyclic structure 
		WURCSseqCyclic = "WURCS=2.0/2,2,2/[h4344h_2*NCC/3=O][a2112h-1b_1-5]/1-2/a4-b1_a6-b2";
		//WURCSseqCyclic = "WURCS=2.0/2,2,1/[ha122h-2b_2-5][a2122h-1a_1-5]/1-2/a2-b1";
		
		// Repeat structure
		WURCSseqRepeat = "WURCS=2.0/2,3,4/[a2122h-1x_1-5_3-6][a2122h-1x_1-5]/1-1-2/a1-c?_a?-b1_b?-c1_b1-b4~n";
		//WURCSseqRepeat = "WURCS=2.0/2,2,2/[Aad21122h-2a_2-6_5*NCC/3=O][a2112h-1a_1-5]/1-2/a4-b1_a2-b6~n";
		//WURCSseqRepeat = "WURCS=2.0/1,3,4/[ha122h-2b_2-5]/1-1-1/a1-b2_a2-c1_b1-c2_a1-a2~4:6";
		
		// Branch structure
		WURCSseqBranch = "WURCS=2.0/6,11,10/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5][a1221m-1a_1-5]/1-2-3-4-2-5-4-2-6-2-5/a4-b1_a6-i1_b4-c1_c3-d1_c6-g1_d2-e1_e4-f1_g2-h1_j4-k1_j1-d4|d6|g4|g6}";
		
		// Alternative structure
		WURCSseqAlternative = "WURCS=2.0/6,10,9/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1b_1-5_2*NCC/3=O]/1-2-3-4-2-5-4-2-6-2/a4-b1_b4-c1_c4-j1_d2-e1_e4-f1_g2-h1_h4-i1_d1-c3|c6_g1-c3|c6";
		//WURCSseqAlternative = "WURCS=2.0/4,7,6/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5]/1-2-3-4-2-4-4/a4-b1_b4-c1_c3-d1_c6-f1_e1-d2|d4_g1-f3|f6";

		// topology structure
		WURCSseqtopology = "WURCS=2.0/3,6,5/[u2122h_2*NCC/3=O][a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5]/1-2-3-3-2-3/a?-b1_b?-c1_c?-d1_c?-f1_d?-e1";
				
		testSCNormal.setWURCSseq(WURCSseqNormal);
		try {
			System.out.println("WURCSseqNormal");
			testSCNormal.convertLinkageInfo2Topology();
			// test case for WURCS level
			System.out.println("WURCSlevel is: "+testSCNormal.getWURCSlevel());
			// test case for WURCStopology level
			System.out.println("WURCStopologylevel is: "+testSCNormal.getWURCStopologylevel());
			System.out.println("WURCStopologylevel class Name is: " +testSCNormal.getWURCStopologylevelClassName());
			System.out.println(testSCNormal.getWURCStopology());
		} catch (SubsumptionException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		testSCCyclic.setWURCSseq(WURCSseqCyclic);
		try {
			System.out.println("WURCSseqCyclic");
			testSCCyclic.convertLinkageInfo2Topology();
			// test case for WURCS level
			System.out.println("WURCSlevel is: "+testSCCyclic.getWURCSlevel());
			// test case for WURCStopology level
			System.out.println("WURCStopologylevel is: "+testSCCyclic.getWURCStopologylevel());
			System.out.println("WURCStopologylevel class Name is: " +testSCCyclic.getWURCStopologylevelClassName());
			System.out.println(testSCCyclic.getWURCStopology());
		} catch (SubsumptionException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		testSCRepeat.setWURCSseq(WURCSseqRepeat);
		try {
			System.out.println("WURCSseqRepeat");
			testSCRepeat.convertLinkageInfo2Topology();
			// test case for WURCS level
			System.out.println("WURCSlevel is: "+testSCRepeat.getWURCSlevel());
			// test case for WURCStopology level
			System.out.println("WURCStopologylevel is: "+testSCRepeat.getWURCStopologylevel());
			System.out.println("WURCStopologylevel class Name is: " +testSCRepeat.getWURCStopologylevelClassName());
			System.out.println(testSCRepeat.getWURCStopology());
		} catch (SubsumptionException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		testSCBranch.setWURCSseq(WURCSseqBranch);
		try {
			System.out.println("WURCSseqBranach");
			testSCBranch.convertLinkageInfo2Topology();
			// test case for WURCS level
			System.out.println("WURCSlevel is: "+testSCBranch.getWURCSlevel());
			// test case for WURCStopology level
			System.out.println("WURCStopologylevel is: "+testSCBranch.getWURCStopologylevel());
			System.out.println("WURCStopologylevel class Name is: " +testSCBranch.getWURCStopologylevelClassName());
			System.out.println(testSCBranch.getWURCStopology());
		} catch (SubsumptionException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		testSCAlternative.setWURCSseq(WURCSseqAlternative);
		try {
			System.out.println("WURCSseqAlternative");
			testSCAlternative.convertLinkageInfo2Topology();
			// test case for WURCS level
			System.out.println("WURCSlevel is: "+testSCAlternative.getWURCSlevel());
			// test case for WURCStopology level
			System.out.println("WURCStopologylevel is: "+testSCAlternative.getWURCStopologylevel());
			System.out.println("WURCStopologylevel class Name is: " +testSCAlternative.getWURCStopologylevelClassName());
			System.out.println(testSCAlternative.getWURCStopology());
		} catch (SubsumptionException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		testSCtopology.setWURCSseq(WURCSseqtopology);
		try {
			System.out.println("WURCSseqtopology");
			testSCtopology.convertLinkageInfo2Topology();
			// test case for WURCS level
			System.out.println("WURCSlevel is: "+testSCtopology.getWURCSlevel());
			// test case for WURCStopology level
			System.out.println("WURCStopologylevel is: "+testSCtopology.getWURCStopologylevel());
			System.out.println("WURCStopologylevel class Name is: " +testSCtopology.getWURCStopologylevelClassName());
			System.out.println(testSCtopology.getWURCStopology());
		} catch (SubsumptionException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		Assert.assertEquals("Convert Successfully","WURCS=2.0/4,4,3/[o222h][a2112h-1o_1-5_2*NCC/3=O][a2112h-1x_1-5_2*NCC/3=O][a2122h-1x_1-5_2*NCC/3=O]/1-2-3-4/a?-b1_b?-c1_c?-d1", testSCNormal.getWURCStopology());
		Assert.assertEquals("WURCS=2.0/2,2,2/[h4344h_2*NCC/3=O][a2112h-1x_1-5]/1-2/a?-b1_a?-b?", testSCCyclic.getWURCStopology());
		Assert.assertNull(testSCRepeat.getWURCStopology());
		Assert.assertEquals("WURCS=2.0/4,11,10/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a2112h-1x_1-5][a1221m-1x_1-5]/1-1-2-2-1-3-2-1-4-1-3/a?-b1_a?-i1_b?-c1_c?-d1_c?-g1_d?-e1_e?-f1_g?-h1_j?-k1_j1-d?|g?}", testSCBranch.getWURCStopology());
		Assert.assertEquals("WURCS=2.0/4,10,9/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a2112h-1x_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1x_1-5_2*NCC/3=O]/1-1-2-2-1-3-2-1-4-1/a?-b1_b?-c1_c?-d1_c?-g1_c?-j1_d?-e1_e?-f1_g?-h1_h?-i1", testSCAlternative.getWURCStopology());
		Assert.assertNull(testSCtopology.getWURCStopology());
		
	}
	
}
