package org.glycoinfo.subsumption;

import java.io.IOException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;

public class SubsumptionTests {
//	public static Logger logger = (Logger) LoggerFactory
//			.getLogger("org.glycoinfo.subsumptionTests");
	
	@Test
	public void SubsumptionConvertertest() throws IOException {
		Subsumption_Converter testSC = new Subsumption_Converter();
		// WURCSの変換（レベル１からレベル２）
		// １つのWURCSの文字列をWURCSseqに入れる
		
		//String WURCSseq = "WURCS=2.0/4,4,3/[o222h][a2112h-1o_1-5_2*NCC/3=O][a2112h-1a_1-5_2*NCC/3=O][a2122h-1a_1-5_2*NCC/3=O]/1-2-3-4/a5-b1_b3-c1_c4-d1";
		//String WURCSseq = "WURCS=2.0/2,2,2/[h4344h_2*NCC/3=O][a2112h-1b_1-5]/1-2/a4-b1_a6-b2";
		//String WURCSseq = "WURCS=2.0/6,10,9/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1b_1-5_2*NCC/3=O]/1-2-3-4-2-5-4-2-6-2/a4-b1_b4-c1_c4-j1_d2-e1_e4-f1_g2-h1_h4-i1_d1-c3|c6_g1-c3|c6";
		//String WURCSseq = "WURCS=2.0/4,5,4/[u2122h_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5]/1-2-3-4-4/a4-b1_b4-c1_c3-d1_c6-e1";
		//String WURCSseq = "WURCS=2.0/2,2,1/[ha122h-2b_2-5][a2122h-1a_1-5]/1-2/a2-b1";
		//String WURCSseq = "WURCS=2.0/2,3,4/[a2122h-1x_1-5_3-6][a2122h-1x_1-5]/1-1-2/a1-c?_a?-b1_b?-c1_b1-b4~n";
		//String WURCSseq = "WURCS=2.0/2,2,2/[Aad21122h-2a_2-6_5*NCC/3=O][a2112h-1a_1-5]/1-2/a4-b1_a2-b6~n";
		//String WURCSseq = "WURCS=2.0/6,11,10/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5][a1221m-1a_1-5]/1-2-3-4-2-5-4-2-6-2-5/a4-b1_a6-i1_b4-c1_c3-d1_c6-g1_d2-e1_e4-f1_g2-h1_j4-k1_j1-d4|d6|g4|g6}";
		//String WURCSseq = "WURCS=2.0/4,7,6/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5]/1-2-3-4-2-4-4/a4-b1_b4-c1_c3-d1_c6-f1_e1-d2|d4_g1-f3|f6";
		//String WURCSseq = "WURCS=2.0/3,6,5/[u2122h_2*NCC/3=O][a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5]/1-2-3-3-2-3/a?-b1_b?-c1_c?-d1_c?-f1_d?-e1";
		//String WURCSseq = "WURCS=2.0/4,7,6/[a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O_4*OSO/3=O/3=O]/1-1-2-3-1-4-3/a4-b1_b4-c1_c3-d1_c6-g1_d2-e1_e4-f1";
		String WURCSseq = "WURCS=2.0/1,3,4/[ha122h-2b_2-5]/1-1-1/a1-b2_a2-c1_b1-c2_a1-a2~4:6";
		//String WURCSseq = "WURCS=2.0/2,2,1/[u2112h_2*NSO/3=O/3=O][a21EEA-1a_1-5]/1-2/a3-b1";
		//String WURCSseq = "WURCS=2.0/6,10,9/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1b_1-5_2*NCC/3=O]/1-2-3-4-2-5-4-2-6-2/a4-b1_b4-c1_c4-j1_d2-e1_e4-f1_g2-h1_h4-i1_d1-c3|c6_g1-c3|c6";
		//String WURCSseq = "WURCS=2.0/7,8,7/[o2122m_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2122h-1a_1-5_2*NCC/3=O][a2112h-1b_1-5][Aad21122h-2a_2-6_5*NCC/3=O]/1-2-3-4-5-6-7-4/a4-b1_b4-c1_c3-d1_c6-h1_d2-e1_e4-f1_f3-g2";
		//String WURCSseq = "WURCS=2.0/4,13,12/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a1221m-1x_1-5][a2112h-1x_1-5]/1-1-2-2-1-3-4-1-4-2-1-4-1/a?-b1_b?-c1_c?-d1_c?-j1_d?-e1_d?-h1_e?-f1_e?-g1_h?-i1_j?-k1_j?-m1_k?-l1";
		testSC.setWURCSseq(WURCSseq);
		try {
			testSC.convertLinkageInfo2Topology();
		} catch (SubsumptionException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
//		System.out.println(check);
		System.out.println(testSC.getWURCStopology());
		
		//Assert.assertEquals("Convert Successfully","WURCS=2.0/4,4,3/[o222h][a2112h-1o_1-5_2*NCC/3=O][a2112h-1x_1-5_2*NCC/3=O][a2122h-1x_1-5_2*NCC/3=O]/1-2-3-4/a?-b1_b?-c1_c?-d1", testSC.getWURCStopology());
		//Assert.assertEquals("Convert Successfully","WURCS=2.0/2,2,2/[h4344h_2*NCC/3=O][a2112h-1x_1-5]/1-2/a?-b1_a?-b?", testSC.getWURCStopology());
		//Assert.assertEquals("Convert Successfully","WURCS=2.0/2,2,1/[ha122h-2x_2-5][a2122h-1x_1-5]/1-2/a2-b1", testSC.getWURCStopology());
		//Assert.assertEquals("Convert Successfully","WURCS=2.0/2,2,2/[Aad21122h-2x_2-6_5*NCC/3=O][a2112h-1x_1-5]/1-2/a?-b1_a2-b?~n", testSC.getWURCStopology());
		Assert.assertEquals("Convert Successfully","WURCS=2.0/3,6,5/[u2122h_2*NCC/3=O][a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5]/1-2-3-3-2-3/a?-b1_b?-c1_c?-d1_c?-f1_d?-e1", testSC.getWURCStopology());
		//Assert.assertEquals("Convert Successfully","WURCS=2.0/4,11,10/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a2112h-1x_1-5][a1221m-1x_1-5]/1-1-2-2-1-3-2-1-4-1-3/a?-b1_a?-i1_b?-c1_c?-d1_c?-g1_d?-e1_e?-f1_g?-h1_j?-k1_j1-d?|g?}", testSC.getWURCStopology());
		//Assert.assertEquals("Convert Successfully","WURCS=2.0/2,2,1/[u2112h_2*NSO/3=O/3=O][a21EEA-1x_1-5]/1-2/a?-b1", testSC.getWURCStopology());
		//Assert.assertEquals("Convert Successfully","WURCS=2.0/4,10,9/[a1122h-1x_1-5][a2122h-1x_1-5_2*NCC/3=O][a2112h-1x_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1x_1-5_2*NCC/3=O]/1-1-1-1-1-2-1-1-3-4/a?-b?_a?-c?_a?-d?_a?-e?_c?-f?_d?-g?_e?-h?_g?-i?_h?-j?", testSC.getWURCStopology());
	}
	
}
