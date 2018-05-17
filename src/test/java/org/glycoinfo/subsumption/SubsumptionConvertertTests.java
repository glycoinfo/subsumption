package org.glycoinfo.subsumption;

import org.glycoinfo.WURCSFramework.util.WURCSException;
import org.glycoinfo.WURCSFramework.util.WURCSFactory;
import org.glycoinfo.WURCSFramework.util.graph.WURCSGraphNormalizer;
import org.glycoinfo.WURCSFramework.wurcs.graph.WURCSGraph;
import org.glycoinfo.subsumption.util.graph.analysis.SubsumptionLevel;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class SubsumptionConvertertTests {
//	public static Logger logger = (Logger) LoggerFactory
//			.getLogger("org.glycoinfo.subsumptionTests");
	
	@Test
	public void SubsumptionConvertertest() throws IOException {
		SubsumptionConverter testSCNormal = new SubsumptionConverter();
		SubsumptionConverter testSCCyclic = new SubsumptionConverter();
		SubsumptionConverter testSCRepeat = new SubsumptionConverter();
		SubsumptionConverter testSCBranch = new SubsumptionConverter();
		SubsumptionConverter testSCAlternative = new SubsumptionConverter();
		SubsumptionConverter testSCtopology = new SubsumptionConverter();
		
		String WURCSseqNormal = null;
		String WURCSseqCyclic = null;
		String WURCSseqRepeat = null;
		String WURCSseqBranch = null;
		String WURCSseqAlternative = null;
		String WURCSseqtopology = null;

		//G45005ZF Test
		WURCSseqtopology = 
				//"WURCS=2.0/2,2,1/[<Q>][<Q>-?a]/1-2/a7-b1";
				"WURCS=2.0/4,6,5/[a2122h-1b_1-5_2*NCC/3=O][<Q>-?b][a2122h-1b_1-5][a1122h-1a_1-5]/1-1-2-3-4-4/a4-b1_b3-c1_b4-e1_b6-f1_c4-d1";
				//"WURCS=2.0/4,5,4/[u2122h_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5]/1-2-3-4-4/a4-b1_b4-c1_c3-d1_c6-e1";
				//"WURCS=2.0/2,103,102/[a1122h-1x_1-5][a122h-1x_1-4]/1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-1-2-2-2-2-2-2-2-2-2-2-2-2-2-2-2-2-2-2-2-2-2-2-1-2-2-1-2-2-2-2-2-2-2-2-2-2-2-2-2-2-2-2-1-2-2-1-2-2-2-2-2-2-1-2-2-1-2-2-2-2-2-2-1-2-2-1-2-2-2-2-2-2-1-2-2-1-1-1-1-1-1-1-1-1-1-1/a?-b1_b?-c1_b?-aY1_c?-d1_d?-e1_d?-aX1_e?-f1_e?-aW1_f?-g1_g?-h1_g?-aV1_h?-i1_h?-aU1_i?-j1_j?-k1_j?-aT1_k?-l1_k?-aS1_l?-m1_m?-n1_m?-aR1_n?-o1_n?-aQ1_o?-p1_p?-q1_p?-aP1_q?-r1_r?-s1_s?-t1_t?-u1_u?-v1_u?-aF1_v?-w1_w?-x1_x?-y1_x?-av1_y?-z1_z?-A1_A?-B1_A?-al1_B?-C1_C?-D1_D?-E1_D?-ab1_E?-F1_F?-G1_G?-H1_G?-R1_H?-I1_I?-J1_J?-K1_K?-L1_K?-O1_L?-M1_M?-N1_O?-P1_P?-Q1_R?-S1_S?-T1_S?-X1_T?-U1_U?-V1_V?-W1_X?-Y1_Y?-Z1_Z?-aa1_ab?-ac1_ac?-ad1_ad?-ae1_ae?-af1_ae?-ai1_af?-ag1_ag?-ah1_ai?-aj1_aj?-ak1_al?-am1_am?-an1_an?-ao1_ao?-ap1_ao?-as1_ap?-aq1_aq?-ar1_as?-at1_at?-au1_av?-aw1_aw?-ax1_ax?-ay1_ay?-az1_ay?-aC1_az?-aA1_aA?-aB1_aC?-aD1_aD?-aE1_aF?-aG1_aG?-aH1_aH?-aI1_aI?-aJ1_aI?-aM1_aJ?-aK1_aK?-aL1_aM?-aN1_aN?-aO1";
	
//		// WURCSの変換（レベル１からレベル２）
//		// １つのWURCSの文字列をWURCSseqに入れる
//		
//		// Normal structure
//		//WURCSseqNormal = "WURCS=2.0/4,4,3/[o222h][a2112h-1b_1-5_2*NCC/3=O][a2112h-1a_1-5_2*NCC/3=O][a2122h-1a_1-5_2*NCC/3=O]/1-2-3-4/a5-b1_b3-c1_c4-d1";
//		//WURCSseqNormal = "WURCS=2.0/6,10,9/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1b_1-5_2*NCC/3=O]/1-2-3-4-2-5-4-2-6-2/a4-b1_b4-c1_c4-j1_d2-e1_e4-f1_g2-h1_h4-i1_d1-c3|c6_g1-c3|c6";
//		//WURCSseqNormal = "WURCS=2.0/4,13,12/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a1221m-1x_1-5][a2112h-1x_1-5]/1-1-2-2-1-3-4-1-4-2-1-4-1/a?-b1_b?-c1_c?-d1_c?-j1_d?-e1_d?-h1_e?-f1_e?-g1_h?-i1_j?-k1_j?-m1_k?-l1";
//		//WURCSseqNormal = "WURCS=2.0/7,8,7/[o2122m_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2122h-1a_1-5_2*NCC/3=O][a2112h-1b_1-5][Aad21122h-2a_2-6_5*NCC/3=O]/1-2-3-4-5-6-7-4/a4-b1_b4-c1_c3-d1_c6-h1_d2-e1_e4-f1_f3-g2";
//		//WURCSseqNormal = "WURCS=2.0/2,2,1/[u2112h_2*NSO/3=O/3=O][a21EEA-1a_1-5]/1-2/a3-b1";
//		//WURCSseqNormal = "WURCS=2.0/4,7,6/[a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O_4*OSO/3=O/3=O]/1-1-2-3-1-4-3/a4-b1_b4-c1_c3-d1_c6-g1_d2-e1_e4-f1";
//		//WURCSseqNormal = "WURCS=2.0/4,5,4/[u2122h_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5]/1-2-3-4-4/a4-b1_b4-c1_c3-d1_c6-e1";
//		WURCSseqNormal = "WURCS=2.0/4,5,4/[u2122h_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5]/1-2-3-4-4/a4-b1_b4-c1_c3-d1_c6-e1";
//		// Cyclic structure 
//		WURCSseqCyclic = "WURCS=2.0/2,2,2/[h4344h_2*NCC/3=O][a2112h-1b_1-5]/1-2/a4-b1_a6-b2";
//		//WURCSseqCyclic = "WURCS=2.0/2,2,1/[ha122h-2b_2-5][a2122h-1a_1-5]/1-2/a2-b1";
//		
//		// Repeat structure
//		WURCSseqRepeat = "WURCS=2.0/2,3,4/[a2122h-1x_1-5_3-6][a2122h-1x_1-5]/1-1-2/a1-c?_a?-b1_b?-c1_b1-b4~n";
//		//WURCSseqRepeat = "WURCS=2.0/2,2,2/[Aad21122h-2a_2-6_5*NCC/3=O][a2112h-1a_1-5]/1-2/a4-b1_a2-b6~n";
//		//WURCSseqRepeat = "WURCS=2.0/1,3,4/[ha122h-2b_2-5]/1-1-1/a1-b2_a2-c1_b1-c2_a1-a2~4:6";
//		
//		// Branch structure
//		WURCSseqBranch = "WURCS=2.0/6,11,10/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5][a1221m-1a_1-5]/1-2-3-4-2-5-4-2-6-2-5/a4-b1_a6-i1_b4-c1_c3-d1_c6-g1_d2-e1_e4-f1_g2-h1_j4-k1_j1-d4|d6|g4|g6}";
//		
//		// Alternative structure
//		WURCSseqAlternative = "WURCS=2.0/6,10,9/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1b_1-5_2*NCC/3=O]/1-2-3-4-2-5-4-2-6-2/a4-b1_b4-c1_c4-j1_d2-e1_e4-f1_g2-h1_h4-i1_d1-c3|c6_g1-c3|c6";
//		//WURCSseqAlternative = "WURCS=2.0/4,7,6/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5]/1-2-3-4-2-4-4/a4-b1_b4-c1_c3-d1_c6-f1_e1-d2|d4_g1-f3|f6";
//
//		// topology structure
//		WURCSseqtopology = "WURCS=2.0/5,7,6/[a2122h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O_4*OSO/3=O/3=O]/1-2-3-4-2-5-4/a4-b1_b4-c1_c3-d1_c6-g1_d2-e1_e4-f1";
//		//		WURCSseqtopology = "WURCS=2.0/3,6,5/[u2122h_2*NCC/3=O][a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5]/1-2-3-3-2-3/a?-b1_b?-c1_c?-d1_c?-f1_d?-e1";
////		WURCSseqtopology = "WURCS=2.0/3,7,6/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a2112h-1x_1-5_2*NCC/3=O_4*OSO/3=O/3=O]/1-1-2-2-1-3-2/a?-b1_b?-c1_c?-d1_c?-g1_d?-e1_e?-f1";
////		WURCSseqtopology = "WURCS=2.0/2,10,9/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5]/1-2-2-2-2-2-2-2-2-2/a?-b1_b?-c1_b?-g1_c?-d1_c?-f1_d?-e1*OPO*/3O/3=O_g?-h1_g?-j1_h?-i1*OPO*/3O/3=O";
////
////		WURCSseqtopology = "WURCS=2.0/2,6,5/[u2122h_2*NCC/3=O][u1122h]/1-1-1-2-2-2/a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?";
//		WURCSseqtopology = "WURCS=2.0/3,3,2/[a2122h-1b_1-5_2*NCC/3=O][a2112h-1b_1-5][Aad21122h-2a_2-6_5*NCC/3=O]/1-2-3/a4-b1_b3-c2";		
//		WURCSseqtopology = "WURCS=2.0/2,3,2/[a1122h-1x_1-5][a2112h-1x_1-5]/1-2-1/a?-b1_b?-c1*OPO*/3O/3=O";
////		WURCSseqtopology = "WURCS=2.0/2,3,2/[u1122h][u2112h]/1-1-2/a?|b?|c?}-{a?|b?|c?_a?|b?|c?}-{a?|b?|c?*OPO*/3O/3=O";
//		WURCSseqtopology = "WURCS=2.0/2,2,1/[a2122h-1x_1-5][a2122h-1x_1-5_3-6]/1-2/a?-b1";
////		WURCSseqtopology = "WURCS=2.0/7,8,7/[A3d344xh_2-7][a11221h-1x_1-5][a11221h-1x_1-5_6*OP^XOCCN/3O/3=O][a11221h-1x_1-5_3*OP^XOCCN/3O/3=O][a2122h-1x_1-5][a2112h-1x_1-5][a2122h-1x_1-5_6*OPO/3O/3=O]/1-2-3-4-5-6-7-6/a?-b1_b?-c1_b?-g1_c?-d1_d?-e1_e?-f1_g?-h1";
//		WURCSseqtopology = "WURCS=2.0/5,5,4/[h2112h_2*NCC/3=O][a2122h-1x_1-?_3-6_2*NCC/3=O][a2112h-1x_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1x_1-5][a1221m-1x_1-5]/1-2-3-4-5/a?-b1_a?-d1_b?-c1_d?-e1";
	
//		testSCNormal.setWURCSseq(WURCSseqNormal);
//		try {
//			System.out.println("WURCSseqNormal");
//			testSCNormal.convertDefined2Ambiguous();
//			// test case for WURCS level
//			System.out.println("");
//			System.out.println("WURCSlevel is: "+testSCNormal.getWURCSlevel());
//			// test case for WURCStopology level
//			System.out.println("WURCStopologylevel is: "+testSCNormal.getAmbiguousWURCSlevel());
//			System.out.println("WURCStopologylevel class Name is: " +testSCNormal.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCNormal.getAmbiguousWURCSseq());
//			System.out.println("");
//			String testTopology = testSCNormal.getAmbiguousWURCSseq();
//			testSCNormal.setWURCSseq(testTopology);
//			testSCNormal.convertDefined2Ambiguous();
//			System.out.println("");
//			System.out.println("WURCScompositionlevel is: "+testSCNormal.getAmbiguousWURCSlevel());
//			System.out.println("WURCScompositionlevel class Name is: " +testSCNormal.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCNormal.getAmbiguousWURCSseq());
//			System.out.println("");
//			String testComposition = testSCNormal.getAmbiguousWURCSseq();
//			testSCNormal.setWURCSseq(testComposition);
//			testSCNormal.convertDefined2Ambiguous();
//			System.out.println("");
//			System.out.println("WURCSbasecompositionlevel is: "+testSCNormal.getAmbiguousWURCSlevel());
//			System.out.println("WURCSbasecompositionlevel class Name is: " +testSCNormal.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCNormal.getAmbiguousWURCSseq());
//			
//		} catch (SubsumptionException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		
//		testSCCyclic.setWURCSseq(WURCSseqCyclic);
//		try {
//			System.out.println("WURCSseqCyclic");
//			testSCCyclic.convertDefined2Ambiguous();
//			// test case for WURCS level
//			System.out.println("WURCSlevel is: "+testSCCyclic.getWURCSlevel());
//			// test case for WURCStopology level
//			System.out.println("WURCStopologylevel is: "+testSCCyclic.getAmbiguousWURCSlevel());
//			System.out.println("WURCStopologylevel class Name is: " +testSCCyclic.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCCyclic.getAmbiguousWURCSseq());
//			
//			String testTopology = testSCCyclic.getAmbiguousWURCSseq();
//			testSCCyclic.setWURCSseq(testTopology);
//			testSCCyclic.convertDefined2Ambiguous();
//			System.out.println("WURCScompositionlevel is: "+testSCCyclic.getAmbiguousWURCSlevel());
//			System.out.println("WURCScompositionlevel class Name is: " +testSCCyclic.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCCyclic.getAmbiguousWURCSseq());
//			String testComposition = testSCCyclic.getAmbiguousWURCSseq();
//			testSCCyclic.setWURCSseq(testComposition);
//			testSCCyclic.convertDefined2Ambiguous();
//			System.out.println("WURCSbasecompositionlevel is: "+testSCCyclic.getAmbiguousWURCSlevel());
//			System.out.println("WURCSbasecompositionlevel class Name is: " +testSCCyclic.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCCyclic.getAmbiguousWURCSseq());
//		} catch (SubsumptionException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		
//		testSCRepeat.setWURCSseq(WURCSseqRepeat);
//		try {
//			System.out.println("WURCSseqRepeat");
//			testSCRepeat.convertDefined2Ambiguous();
//			// test case for WURCS level
//			System.out.println("WURCSlevel is: "+testSCRepeat.getWURCSlevel());
//			// test case for WURCStopology level
//			System.out.println("WURCStopologylevel is: "+testSCRepeat.getAmbiguousWURCSlevel());
//			System.out.println("WURCStopologylevel class Name is: " +testSCRepeat.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCRepeat.getAmbiguousWURCSseq());
//			
//			String testTopology = testSCRepeat.getAmbiguousWURCSseq();
//			testSCRepeat.setWURCSseq(testTopology);
//			testSCRepeat.convertDefined2Ambiguous();
//			System.out.println("WURCScompositionlevel is: "+testSCRepeat.getAmbiguousWURCSlevel());
//			System.out.println("WURCScompositionlevel class Name is: " +testSCRepeat.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCRepeat.getAmbiguousWURCSseq());
//			String testComposition = testSCRepeat.getAmbiguousWURCSseq();
//			testSCRepeat.setWURCSseq(testComposition);
//			testSCRepeat.convertDefined2Ambiguous();
//			System.out.println("WURCSbasecompositionlevel is: "+testSCRepeat.getAmbiguousWURCSlevel());
//			System.out.println("WURCSbasecompositionlevel class Name is: " +testSCRepeat.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCRepeat.getAmbiguousWURCSseq());
//		} catch (SubsumptionException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		
//		testSCBranch.setWURCSseq(WURCSseqBranch);
//		try {
//			System.out.println("WURCSseqBranach");
//			testSCBranch.convertDefined2Ambiguous();
//			// test case for WURCS level
//			System.out.println("WURCSlevel is: "+testSCBranch.getWURCSlevel());
//			// test case for WURCStopology level
//			System.out.println("WURCStopologylevel is: "+testSCBranch.getAmbiguousWURCSlevel());
//			System.out.println("WURCStopologylevel class Name is: " +testSCBranch.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCBranch.getAmbiguousWURCSseq());
//			
//			String testTopology = testSCBranch.getAmbiguousWURCSseq();
//			testSCBranch.setWURCSseq(testTopology);
//			testSCBranch.convertDefined2Ambiguous();
//			System.out.println("WURCScompositionlevel is: "+testSCBranch.getAmbiguousWURCSlevel());
//			System.out.println("WURCScompositionlevel class Name is: " +testSCBranch.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCBranch.getAmbiguousWURCSseq());
//			String testComposition = testSCBranch.getAmbiguousWURCSseq();
//			testSCBranch.setWURCSseq(testComposition);
//			testSCBranch.convertDefined2Ambiguous();
//			System.out.println("WURCSbasecompositionlevel is: "+testSCBranch.getAmbiguousWURCSlevel());
//			System.out.println("WURCSbasecompositionlevel class Name is: " +testSCBranch.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCBranch.getAmbiguousWURCSseq());
//		} catch (SubsumptionException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		
//		testSCAlternative.setWURCSseq(WURCSseqAlternative);
//		try {
//			System.out.println("WURCSseqAlternative");
//			testSCAlternative.convertDefined2Ambiguous();
//			// test case for WURCS level
//			System.out.println("WURCSlevel is: "+testSCAlternative.getWURCSlevel());
//			// test case for WURCStopology level
//			System.out.println("WURCStopologylevel is: "+testSCAlternative.getAmbiguousWURCSlevel());
//			System.out.println("WURCStopologylevel class Name is: " +testSCAlternative.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCAlternative.getAmbiguousWURCSseq());
//			
//			String testTopology = testSCAlternative.getAmbiguousWURCSseq();
//			testSCAlternative.setWURCSseq(testTopology);
//			testSCAlternative.convertDefined2Ambiguous();
//			System.out.println("WURCScompositionlevel is: "+testSCAlternative.getAmbiguousWURCSlevel());
//			System.out.println("WURCScompositionlevel class Name is: " +testSCAlternative.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCAlternative.getAmbiguousWURCSseq());
//			String testComposition = testSCAlternative.getAmbiguousWURCSseq();
//			testSCAlternative.setWURCSseq(testComposition);
//			testSCAlternative.convertDefined2Ambiguous();
//			System.out.println("WURCSbasecompositionlevel is: "+testSCAlternative.getAmbiguousWURCSlevel());
//			System.out.println("WURCSbasecompositionlevel class Name is: " +testSCAlternative.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCAlternative.getAmbiguousWURCSseq());
//		} catch (SubsumptionException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
		
		try {
			//System.out.println("WURCSseqtopology");
			//testSCtopology.convertDefined2Ambiguous();
			// test case for WURCS level
			//System.out.println("WURCSlevel is: "+testSCtopology.getWURCSlevel());
			//System.out.println("WURCSlevel is: "+testSCtopology.getWURCSseq());
			//System.out.println("Convert");
			// test case for WURCStopology level
			//System.out.println("WURCScompositionlevel is: "+testSCtopology.getAmbiguousWURCSlevel());
			//System.out.println("WURCScompositionlevel class Name is: " +testSCtopology.getAmbiguousWURCSlevelClassName());
			//System.out.println(testSCtopology.getAmbiguousWURCSseq());

			ArrayList<String> items = new ArrayList<String>();
			items.add(WURCSseqtopology);
			
			while (!items.isEmpty()) {
				for (String item : items) {
					items = converterInterface(item);
				}
			}
			
//			System.out.println("Convert");
//			String testTopology = testSCtopology.getAmbiguousWURCSseq();
//			testSCtopology.setWURCSseq(testTopology);
//			testSCtopology.convertDefined2Ambiguous();
//			System.out.println("WURCScompositionlevel is: "+testSCtopology.getAmbiguousWURCSlevel());
//			System.out.println("WURCScompositionlevel class Name is: " +testSCtopology.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCtopology.getAmbiguousWURCSseq());
//			String testComposition = testSCtopology.getAmbiguousWURCSseq();
//			testSCtopology.setWURCSseq(testComposition);
//			testSCtopology.convertDefined2Ambiguous();
//			System.out.println("WURCSbasecompositionlevel is: "+testSCtopology.getAmbiguousWURCSlevel());
//			System.out.println("WURCSbasecompositionlevel class Name is: " +testSCtopology.getAmbiguousWURCSlevelClassName());
//			System.out.println(testSCtopology.getAmbiguousWURCSseq());
		} catch (SubsumptionException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
//		Assert.assertEquals("Convert Successfully","WURCS=2.0/3,4,3/[o222h][a2112h-1x_1-5_2*NCC/3=O][a2122h-1x_1-5_2*NCC/3=O]/1-2-2-3/a?-b1_b?-c1_c?-d1", testSCNormal.getAmbiguousWURCSseq());
//		Assert.assertEquals("WURCS=2.0/2,2,2/[h4344h_2*NCC/3=O][a2112h-1x_1-5]/1-2/a?-b1_a?-b?", testSCCyclic.getAmbiguousWURCSseq());
//		Assert.assertNull(testSCRepeat.getAmbiguousWURCSseq());
//		Assert.assertEquals("WURCS=2.0/4,11,10/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a2112h-1x_1-5][a1221m-1x_1-5]/1-1-2-2-1-3-2-1-4-1-3/a?-b1_a?-i1_b?-c1_c?-d1_c?-g1_d?-e1_e?-f1_g?-h1_j?-k1_j1-d?|g?}", testSCBranch.getAmbiguousWURCSseq());
//		Assert.assertEquals("WURCS=2.0/4,10,9/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a2112h-1x_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1x_1-5_2*NCC/3=O]/1-1-2-2-1-3-2-1-4-1/a?-b1_b?-c1_c?-d1_c?-g1_c?-j1_d?-e1_e?-f1_g?-h1_h?-i1", testSCAlternative.getAmbiguousWURCSseq());
//		Assert.assertEquals("WURCS=2.0/2,6,5/[u2122h_2*NCC/3=O][u1122h]/1-1-1-2-2-2/a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?",testSCtopology.getAmbiguousWURCSseq());
		
	}
	
	public static ArrayList<String> converterInterface (String _input) throws SubsumptionException {
		ArrayList<String> ret = new ArrayList<String>();
		SubsumptionLevel subLevel = checkSubsumptionLevel(_input);
		
		if (subLevel.equals(SubsumptionLevel.LV5) || subLevel.equals(SubsumptionLevel.LVX)) {
			return ret;
		}
		if (subLevel.equals(SubsumptionLevel.LV3)) {
			SubsumptionConverter subConv = convertSubsumption(_input);
			ret.add(subConv.getAmbiguousWURCSseq());
			ret.add(subConv.getSecondAmbiguousWURCSSeq());
			return ret;
		}
		
		ret.add(convertSubsumption(_input).getAmbiguousWURCSseq());
		return ret;
	}
	
	public static SubsumptionConverter convertSubsumption (String _input) throws SubsumptionException {
		SubsumptionConverter testSCtopology = new SubsumptionConverter();
		testSCtopology.setWURCSseq(_input);
		testSCtopology.convertDefined2Ambiguous();

		SubsumptionLevel inputSub = checkSubsumptionLevel(_input);
		SubsumptionLevel outSub = checkSubsumptionLevel(testSCtopology.getAmbiguousWURCSseq());
		
		System.out.println(inputSub + " to " + outSub);
		System.out.println(inputSub + " : " + testSCtopology.getWURCSseq());
		System.out.println(outSub + " : " + testSCtopology.getAmbiguousWURCSseq());
		if (testSCtopology.getSecondAmbiguousWURCSSeq() != null) {
			System.out.println(checkSubsumptionLevel(testSCtopology.getSecondAmbiguousWURCSSeq()) + " : " + testSCtopology.getSecondAmbiguousWURCSSeq());
		}
		System.out.println("");
		
		return testSCtopology;
	}
	
	public static SubsumptionLevel checkSubsumptionLevel (String _input) {
		SubsumptionLevel subLevel = SubsumptionLevel.LVX;
		try {
			WURCSFactory wf = new WURCSFactory(_input);
			WURCSGraph graph = wf.getGraph();
			WURCSGraphNormalizer wgNorm = new WURCSGraphNormalizer();
			wgNorm.start(graph);
			
			WURCSGraphStateDeterminator wgsd = new WURCSGraphStateDeterminator();
			subLevel = wgsd.getSubsumptionLevel(graph);
		} catch (WURCSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return subLevel;
	}
	
}
