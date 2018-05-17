package org.glycoinfo.subsumption;

import java.util.ArrayList;

import org.glycoinfo.WURCSFramework.util.WURCSException;
import org.glycoinfo.WURCSFramework.util.WURCSFactory;
import org.glycoinfo.WURCSFramework.util.graph.WURCSGraphNormalizer;
import org.glycoinfo.WURCSFramework.wurcs.graph.WURCSGraph;
import org.glycoinfo.subsumption.util.graph.analysis.SubsumptionLevel;
import org.junit.Test;

public class SubsumptionConverterListTest {

	@Test
	public void SubsumptionConverter () {
		SubsumptionConverter testSCtopology = new SubsumptionConverter();
		ArrayList<String> sampleLists = new ArrayList<String>();
		
		StringBuilder error = new StringBuilder();
		
		sampleLists.add("WURCS=2.0/2,4,3/[a122h-1x_1-4][<Q>]/1-1-2-2/a5-b1_b3-c1_b5-d1");
		sampleLists.add("WURCS=2.0/2,2,1/[<Q>][a2122h-1a_1-5_2*N_6*OPO/3O/3=O]/1-2/a3-b1");
		sampleLists.add("WURCS=2.0/3,3,3/[a2112m-1b_1-5_2*NCC/3=O][a1121A-1a_1-5_2*NCC/3=O_3*NCC/3=O][<Q>-?b]/1-2-3/a3-b1_b4-c1_a1-c4~n");
		sampleLists.add("WURCS=2.0/6,8,7/[<Q>][Aa11122h-2x_2-6][a11221h-1x_1-5][a2122h-1x_1-5_2*NCC/3=O][a11222h-1x_1-5][a2122h-1x_1-5]/1-2-3-3-4-3-5-6/a4-b2_a5-c1_c3-d1_c7-h1_d3-e1_d7-f1_f7-g1");
		sampleLists.add("WURCS=2.0/3,6,5/[<Q>][a1122h-1a_1-5][a2112h-1a_1-5]/1-2-2-3-2-3/a5-b1_b4-c1_c2-d1_c6-e1_e2-f1");
		sampleLists.add("WURCS=2.0/2,2,2/[<Q>-?b][Aad21121m-2a_2-6_5*NCC/3=O_7*NCC/3=O]/1-2/a3-b2_a1-b4~n");
		sampleLists.add("WURCS=2.0/6,13,12/[a2122h-1a_1-5][a2122h-1b_1-5][a2211m-1a_1-5_2*OCC/3=O_3*OC][a121h-1b_1-5][a2112m-1a_1-5][<Q>]/1-1-2-2-3-4-4-4-4-4-4-5-6/a1-b1_b4-c1_c3-d1_d3-e1_e4-f1_f4-g1_g4-h1_h4-i1_i4-j1_j4-k1_k4-l1_l3-m1");
		sampleLists.add("WURCS=2.0/3,6,5/[a122h-1x_1-4][<Q>][a1122h-1x_1-5]/1-2-1-1-3-3/a3-b1_a5-c1_c2-d1_d5-e1_e2-f1");
		sampleLists.add("WURCS=2.0/5,5,5/[a2112m-1a_1-5_2*N_4*NCC/3=O][a2122m-1b_1-5_3*NCC/3=O][a1221m-1a_1-5][a1122h-1a_1-5_2*NCC/3=O][<Q>-?a]/1-2-3-4-5/a3-b1_b2-c1_b4-d1_d3-e1_a1-e2~n");
		sampleLists.add("WURCS=2.0/2,3,2/[a1122h-1x_1-5][<Q>]/1-1-2/a2-b1_b2-c1");
		sampleLists.add("WURCS=2.0/6,11,10/[<Q>][a11221h-1a_1-5][a2122h-1b_1-5][a11222h-1a_1-5][a2112h-1b_1-5][a2122h-1b_1-5_2*NCC/3=O]/1-2-2-2-3-4-5-6-5-6-5/a5-b1_b3-c1_b4-e1_c2-d1_e6-f1_f4-g1_g3-h1_h4-i1_i3-j1_j4-k1");
		sampleLists.add("WURCS=2.0/5,6,5/[<Q>_4*OP^XOP^XOCCN/5O/5=O/3O/3=O][a11221h-1x_1-5][a11221h-1x_1-5_6*OP^XOCCN/3O/3=O][a2122h-1b_1-5_6*OPO/3O/3=O][a2122h-1b_1-5]/1-2-3-2-4-5/a5-b1_b3-c1_b4-e1_c2-d1_e4-f1");
		sampleLists.add("WURCS=2.0/3,3,2/[a122h-1x_1-4][a1122h-1x_1-5][<Q>]/1-2-3/a5-b1_b2-c1");
		sampleLists.add("WURCS=2.0/7,9,8/[<Q>][a11221h-1a_1-5][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a2122h-1b_1-5][a2112h-1b_1-5][a2112h-1b_1-5_2*NCC/3=O][a2122h-1b_1-5_6*OPO/3O/3=O]/1-2-3-2-4-5-5-6-7/a5-b1_b3-c1_b4-i1_c2-d1_d2-e1_e4-f1_f4-g1_g3-h1");
		sampleLists.add("WURCS=2.0/4,4,3/[<Q>][a11221h-1a_1-5_2*OPO/3O/3=O_4*OPO/3O/3=O][a11221h-1a_1-5_6*OPO/3O/3=O][a2112h-1a_1-5_2*NCC/3=O]/1-2-3-4/a5-b1_b3-c1_c3-d1");
		sampleLists.add("WURCS=2.0/6,8,7/[<Q>_4*OP^XOP^XOCCN/5O/5=O/3O/3=O][a11221h-1x_1-5][a11221h-1x_1-5_6*OP^XOCCN/3O/3=O][a2112h-1b_1-5][a2122h-1a_1-5][a2122h-1b_1-5]/1-2-3-2-4-5-6-6/a5-b1_b3-c1_b4-g1_c2-d1_c3-f1_d3-e1_g4-h1");
		sampleLists.add("WURCS=2.0/2,6,5/[a122h-1x_1-4][<Q>]/1-1-1-2-1-2/a5-b1_b5-c1_c3-d1_c5-e1_e5-f1");
		sampleLists.add("WURCS=2.0/10,13,12/[a2122h-1a_1-5_2*N][a2122h-1b_1-5_2*N_4*OPO/3O/3=O][Aad1122h-2a_2-6_4*OPO/3O/3=O][a11221h-1a_1-5][a11221h-1a_1-5_4*OPO/3O/3=O][a2122A-1b_1-5][a2122h-1b_1-5][a2112m-1b_1-5_2*NCC/3=O_4*N][<Q>][a2112A-1a_1-5_2*N]/1-2-3-4-5-6-1-7-1-4-8-9-10/a6-b1_b6-c2_c5-d1_d3-e1_d4-h1_e2-f1_e7-g1_h4-i1_h6-m1_i4-j1_j6-k1_k3-l1");
		sampleLists.add("WURCS=2.0/2,4,3/[a122h-1x_1-4][<Q>]/1-1-2-2/a3-b1_a5-d1_b5-c1");
		sampleLists.add("WURCS=2.0/6,9,8/[<Q>][a11221h-1a_1-5][a2122h-1b_1-5][a11222h-1a_1-5][a2112h-1b_1-5][a2122h-1b_1-5_2*NCC/3=O]/1-2-2-2-3-4-5-6-5/a5-b1_b3-c1_b4-e1_c2-d1_e6-f1_f4-g1_g3-h1_h4-i1");
		sampleLists.add("WURCS=2.0/2,2,1/[<Q>][<Q>-?a]/1-2/a7-b1");
		sampleLists.add("WURCS=2.0/3,5,4/[a122h-1x_1-4][<Q>][a1122h-1x_1-5]/1-2-1-1-3/a3-b1_a5-c1_c2-d1_d5-e1");
		sampleLists.add("WURCS=2.0/4,6,5/[a2122h-1b_1-5_2*NCC/3=O][<Q>-?b][a2122h-1b_1-5][a1122h-1a_1-5]/1-1-2-3-4-4/a4-b1_b3-c1_b4-e1_b6-f1_c4-d1");
		sampleLists.add("WURCS=2.0/2,6,6/[a2112h-1b_1-5][<Q>]/1-1-2-1-2-1/a6-b1_b3-c1_b6-d1_d3-e1_d6-f1_a1-f6~n");
		sampleLists.add("WURCS=2.0/2,8,7/[a122h-1x_1-4][<Q>]/1-2-1-1-2-1-1-2/a3-b1_a5-c1_c5-d1_d3-e1_d5-f1_f5-g1_g5-h1");
		sampleLists.add("WURCS=2.0/12,18,17/[<Q>][a1122h-1a_1-5][a2112h-1a_1-5][a2122h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5_2*NCC/3=O][a2112h-1a_1-5_2*NCC/3=O][a2112m-1a_1-5][a1112m-1a_1-5_3*OC][a2122A-1b_1-5][a2122h-1a_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O]/1-2-2-3-4-4-2-4-5-3-6-7-8-9-10-8-11-12/a5-b1_b4-c1_c2-d1_c6-g1_d3-e1_e2-f1_g3-h1_h3-i1_i3-j1_j4-k1_k4-l1_l3-m1_m3-n1_m4-o1_o2-p1_p3-q1_p4-r1");
		sampleLists.add("WURCS=2.0/9,9,8/[Aad1122h-2a_2-6][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a11221h-1a_1-5][a2112h-1a_1-5][a2122h-1b_1-5_2*NCC/3=O][a2112h-1b_1-5_2*NCC/3=O][<Q>][a2112h-1a_1-5_2*NCC/3=O][a2122h-1b_1-5]/1-2-3-4-5-6-7-8-9/a5-b1_b3-c1_b4-i1_c3-d1_d2-e1_d3-f1_f3-g1_f4-h1");
		sampleLists.add("WURCS=2.0/6,12,11/[a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][<Q>][a2112h-1b_1-5][a1221m-1a_1-5]/1-1-2-3-1-4-4-1-3-1-5-6/a4-b1_a6-l1_b4-c1_c3-d1_c4-h1_c6-i1_d2-e1_d4-g1_e4-f1_i2-j1_j4-k1");
		sampleLists.add("WURCS=2.0/4,4,3/[<Q>_5*OCC/3=O][a2122h-1a_1-5][a2122h-1b_1-5][a1122h-1b_1-5]/1-2-3-4/a4-b1_b4-c1_c4-d1");
		sampleLists.add("WURCS=2.0/7,13,12/[AUd1122h][a11221h-1a_1-5][a2122h-1a_1-5][a2112h-1a_1-5][a2122m-1b_1-5_2*NCC/3=O][<Q>-?b][a2122h-1b_1-5]/1-2-2-3-4-3-5-6-6-6-2-7-3/a5-b1_b3-c1_b4-l1_c3-d1_c7-k1_d2-e1_d6-f1_f6-g1_g3-h1_h7-i1_i7-j1_l6-m1");
		sampleLists.add("WURCS=2.0/2,6,5/[a122h-1x_1-4][<Q>]/1-1-1-2-1-2/b5-c1_c5-d1_e5-f1_a?-b1_a?-e1");
		sampleLists.add("WURCS=2.0/4,4,3/[<Q>][a2122A-1a_1-5_2*NCC/3=O][a2112h-1a_1-5_2*NCC/3=O][a2122h-1b_1-5_2*N]/1-2-3-4/a4-b1_a7-d1_b4-c1");
		sampleLists.add("WURCS=2.0/1,3,2/[<Q>]/1-1-1/a1-b7_b1-c7");
		sampleLists.add("WURCS=2.0/2,2,1/[<Q>-?b][Aad21121m-2a_2-6_5*NCC/3=O_7*N_8*OCC/3=O]/1-2/a3-b2");
		sampleLists.add("WURCS=2.0/5,7,6/[<Q>][a11221h-1a_1-5][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a2112h-1b_1-5][a2122h-1b_1-5]/1-2-3-2-4-5-5/a5-b1_b3-c1_b4-f1_c2-d1_d2-e1_f4-g1");
		sampleLists.add("WURCS=2.0/4,5,4/[<Q>-?a][a1122h-1a_1-5][a2112h-1a_1-5][a2112h-1b_1-5_2*NCC/3=O]/1-2-2-3-4/a5-b1_b3-c1_c3-d1_d3-e1");
		sampleLists.add("WURCS=2.0/3,3,3/[a2112m-1a_1-5_2*NCC/3=O][a1121A-1a_1-5_2*NCC/3=O_3*NCC/3=O][<Q>-?b]/1-2-3/a3-b1_b4-c1_a1-c4~n");
		sampleLists.add("WURCS=2.0/3,3,2/[<Q>][a2122h-1a_1-5_2*N][a211h-1b_1-5_4*N]/1-2-3/a4-b1_b6-c1*OPO*/3O/3=O");
		sampleLists.add("WURCS=2.0/2,3,2/[<Q>][a1122h-1a_1-5]/1-2-2/a5-b1_b3-c1");
		sampleLists.add("WURCS=2.0/2,3,2/[a122h-1x_1-4][<Q>]/1-1-2/a5-b1_b5-c1");
		sampleLists.add("WURCS=2.0/7,8,7/[<Q>][a11221h-1a_1-5][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a2122h-1b_1-5][a2112h-1b_1-5][a2112h-1a_1-5][a2122h-1b_1-5_6*OPO/3O/3=O]/1-2-3-2-4-5-6-7/a5-b1_b3-c1_b4-h1_c2-d1_d2-e1_e4-f1_f4-g1");
		sampleLists.add("WURCS=2.0/5,5,5/[a1221m-1a_1-5][a2211m-1a_1-5][a2122h-1a_1-5][<Q>-?a][a2122h-1b_1-5_2*NCC/3=O]/1-2-3-4-5/a4-b1_b2-c1_b3-e1_c6-d1_a1-e3~n");
		sampleLists.add("WURCS=2.0/10,13,12/[a2122h-1a_1-5_2*N][a2122h-1b_1-5_2*N_4*OPO/3O/3=O][Aad1122h-2a_2-6_4*OPO/3O/3=O][a11221h-1a_1-5][a11221h-1a_1-5_4*OPO/3O/3=O][a2122A-1b_1-5][a2122h-1b_1-5][a2112m-1b_1-5_2*NCC/3=O_4*NC][<Q>][a2112A-1a_1-5_2*N]/1-2-3-4-5-6-1-7-1-4-8-9-10/a6-b1_b6-c2_c5-d1_d3-e1_d4-h1_e2-f1_e7-g1_h4-i1_h6-m1_i4-j1_j6-k1_k3-l1");
		sampleLists.add("WURCS=2.0/3,6,5/[a122h-1x_1-4][a1122h-1x_1-5][<Q>]/1-1-1-2-2-3/a3-b1_a5-f1_b2-c1_c5-d1_d2-e1");
		sampleLists.add("WURCS=2.0/2,2,1/[a1221A-1a_1-5_2*NCC/3=O_3*NCC/3=O][<Q>-?a]/1-2/a4-b1");
		sampleLists.add("WURCS=2.0/5,6,5/[<Q>][a11221h-1a_1-5][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a2112h-1b_1-5][a2122h-1b_1-5]/1-2-3-2-4-5/a5-b1_b3-c1_b4-f1_c2-d1_d2-e1");
		sampleLists.add("WURCS=2.0/2,6,5/[a344h-1x_1-5][<Q>]/1-1-1-2-1-1/a3-b1_b3-c1_b4-e1_c3-d1_e4-f1");
		sampleLists.add("WURCS=2.0/2,2,2/[<Q>-?b][Aad21121m-2a_2-6_5*N_7*NCC/3=O]/1-2/a3-b2_a1-b8~n");
		sampleLists.add("WURCS=2.0/2,4,3/[a122h-1x_1-4][<Q>]/1-1-2-1/b5-c1_a?-b1_a?-d1");
		sampleLists.add("WURCS=2.0/5,7,6/[<Q>][a11221h-1x_1-5][a2122h-1x_1-5_2*NCC/3=O][a11222h-1x_1-5][a2122h-1x_1-5]/1-2-2-3-2-4-5/a5-b1_b3-c1_b7-g1_c3-d1_c7-e1_e7-f1");
		sampleLists.add("WURCS=2.0/2,2,1/[<Q>][a2122h-1b_1-5_2*N]/1-2/a6-b1");
		sampleLists.add("WURCS=2.0/13,30,29/[<Q>][a1122h-1a_1-5][a1122h-1b_1-5][a2122m-1b_1-5_2*NCC/3=O][a1221m-1a_1-5][a2122h-1a_1-5_2*NCC/3=O][a2122h-1b_1-5][a2122h-1a_1-5][a2211m-1a_1-5][a2122m-1b_1-5_3*NCC/3=O_4*OCC/3=O][a2122m-1b_1-5_3*NCC/3=O][a4344m-1b_1-5_4*N][a4334m-1b_1-5_4*N]/1-2-3-2-4-5-6-7-5-5-8-5-8-5-5-8-5-8-5-5-8-5-9-9-10-11-7-12-13-2/a5-b1_b4-c1_b6-D1_c2-d1_c4-e1_e3-f1_f3-g1_f4-h1_h4-i1_i4-j1_j2-k1_j4-l1_l3-m1_l4-n1_n4-o1_o2-p1_o4-q1_q3-r1_q4-s1_s4-t1_t2-u1_t4-v1_v3-w1_w3-x1_x2-y1_y2-z1_z4-A1_A4-B1_B3-C1");
		sampleLists.add("WURCS=2.0/5,10,9/[a2122h-1b_1-5_2*NCC/3=O][a1122h-1b_1-5][a1122h-1a_1-5][<Q>][a1221m-1a_1-5]/1-1-2-3-4-4-3-4-4-5/a4-b1_a6-j1_b4-c1_c3-d1_c6-g1_d2-e1_d4-f1_g2-h1_g6-i1");
		sampleLists.add("WURCS=2.0/6,12,11/[a2122h-1a_1-5][a2122h-1b_1-5][a2112h-1b_1-5][a2211m-1a_1-5_3*OC][a121h-1b_1-5][<Q>]/1-1-2-3-4-5-5-5-5-5-5-6/a1-b1_b4-c1_c3-d1_d3-e1_e4-f1_f4-g1_g4-h1_h4-i1_i4-j1_j4-k1_k3-l1");
		sampleLists.add("WURCS=2.0/4,4,3/[<Q>][a2112h-1b_1-5][a2112h-1a_1-5][a2112h-1b_1-5_2*OCC/3=O]/1-2-3-4/a5-b1_b3-c1_c4-d1");
		sampleLists.add("WURCS=2.0/2,19,19/[<Q>-?b_6*OCC/3=O][<Q>-?b]/1-1-1-1-1-2-2-2-2-1-1-1-1-2-1-2-2-2-2/a1-b7_b1-c7_c1-d7_d1-e7_e1-f7_f1-g7_g1-h7_h1-i7_i1-j7_j1-k7_k1-l7_l1-m7_m1-n7_n1-o7_o1-p7_p1-q7_q1-r7_r1-s7_a7-s1~n");
		sampleLists.add("WURCS=2.0/10,12,11/[a2122h-1a_1-5_2*N][a2122h-1b_1-5_2*N_4*OPO/3O/3=O][Aad1122h-2a_2-6_4*OPO/3O/3=O][a11221h-1a_1-5][a11221h-1a_1-5_4*OPO/3O/3=O][a2122A-1b_1-5][a2122h-1b_1-5][a2112m-1b_1-5_2*NCC/3=O_4*N][<Q>][a2112A-1a_1-5_2*N]/1-2-3-4-5-6-1-7-1-8-9-10/a6-b1_b6-c2_c5-d1_d3-e1_d4-h1_e2-f1_e7-g1_h4-i1_h6-l1_i6-j1_j3-k1");
		sampleLists.add("WURCS=2.0/7,7,6/[<Q>][a11221h-1a_1-5][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a11221h-1a_1-5_3*OCC/3=O][a2112h-1b_1-5][a2122h-1b_1-5][a2122h-1b_1-5_6*OCC/3=O]/1-2-3-4-5-6-7/a5-b1_b3-c1_b4-f1_c2-d1_d2-e1_f4-g1");
		sampleLists.add("WURCS=2.0/3,3,2/[<Q>][a1122h-1a_1-5][a2112h-1a_1-5]/1-2-3/a5-b1_a7-c1");
		sampleLists.add("WURCS=2.0/2,4,3/[<Q>][a2211m-1a_1-5]/1-2-2-2/a8-b1_b3-c1_c3-d1");
		sampleLists.add("WURCS=2.0/10,12,11/[a2122h-1a_1-5_2*N][a2122h-1b_1-5_2*N_4*OPO/3O/3=O][Aad1122h-2a_2-6_4*OPO/3O/3=O][a11221h-1a_1-5][a11221h-1a_1-5_4*OPO/3O/3=O][a2122A-1b_1-5][a2122h-1b_1-5][a2112m-1b_1-5_2*N_4*NCC/3=O][<Q>][a2112A-1a_1-5_2*N]/1-2-3-4-5-6-1-7-1-8-9-10/a6-b1_b6-c2_c5-d1_d3-e1_d4-h1_e2-f1_e7-g1_h4-i1_h6-l1_i6-j1_j3-k1");
		sampleLists.add("WURCS=2.0/6,7,6/[<Q>][a11221h-1a_1-5][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a2122h-1b_1-5_6*OCC/3=O][a2112h-1b_1-5][a2122h-1b_1-5]/1-2-3-2-4-5-6/a5-b1_b3-c1_b4-g1_c2-d1_d2-e1_e4-f1");
		sampleLists.add("WURCS=2.0/5,5,4/[<Q>][a11221h-1a_1-5_2*OCC/3=O][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a11221h-1a_1-5_3*OP^XOCCN/3O/3=O][a2122h-1b_1-5_3*OCC/3=O_4*OCC/3=O_6*OPO/3O/3=O]/1-2-3-4-5/a5-b1_b3-c1_b4-e1_c2-d1");
		sampleLists.add("WURCS=2.0/4,5,4/[a2112h-1x_1-5_2*NCC/3=O][a2122h-1b_1-5_2*NCC/3=O][a1221m-1x_1-5][<Q>-?a]/1-2-3-1-4/a3-b1_c2-d1_c1-b3|b4_e1-b3|b4");
		sampleLists.add("WURCS=2.0/2,5,4/[a122h-1x_1-4][<Q>]/1-2-1-1-2/a3-b1_a5-c1_c5-d1_d5-e1");
		sampleLists.add("WURCS=2.0/5,7,6/[<Q>_4*OP^XOP^XOCCN/5O/5=O/3O/3=O][a11221h-1x_1-5][a11221h-1x_1-5_6*OP^XOCCN/3O/3=O][a2122h-1a_1-5][a2122h-1b_1-5]/1-2-3-2-4-5-5/a5-b1_b3-c1_b4-f1_c2-d1_c3-e1_f4-g1");
		sampleLists.add("WURCS=2.0/3,5,4/[a2112A-1b_1-5][a2211m-1a_1-5][<Q>]/1-2-1-1-3/a4-b1_b2-c1_b4-e1_c4-d1");
		sampleLists.add("WURCS=2.0/6,8,7/[<Q>][Aa11122h-2x_2-6][a11221h-1x_1-5][a2122h-1x_1-5_2*NCC/3=O][a2112h-1x_1-5][a2122h-1x_1-5]/1-2-3-3-4-3-5-6/a4-b2_a5-c1_c3-d1_c7-h1_d3-e1_d7-f1_f7-g1");
		sampleLists.add("WURCS=2.0/7,8,7/[<Q>][a11221h-1a_1-5][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a11221h-1a_1-5_2*OCC/3=O][a2122h-1b_1-5][a2112h-1b_1-5][a2122h-1a_1-5_6*OPO/3O/3=O]/1-2-3-4-5-6-7-5/a5-b1_b3-c1_b4-h1_c2-d1_c3-g1_d3-e1_e4-f1");
		sampleLists.add("WURCS=2.0/2,14,14/[a211h-1a_1-4][<Q>]/1-1-2-1-1-1-1-1-1-1-2-2-1-1/a3-b1_a5-d1_b5-c1_d5-e1_e5-f1_f3-g1_f5-m1_g5-h1_h5-i1_i5-j1_j3-k1_j5-l1_m5-n1_a1-n5~n");
		sampleLists.add("WURCS=2.0/3,3,3/[a2112m-1b_1-5_2*NCC/3=O][a1122A-1b_1-5_2*NCC/3=O_3*NCC/3=O][<Q>-?b]/1-2-3/a3-b1_b4-c1_a1-c4~n");
		sampleLists.add("WURCS=2.0/5,6,5/[Aad1122h-2x_2-6][a11221h-1x_1-5][a11221h-1x_1-5_3*OPO/3O/3=O][a1122h-1x_1-5][<Q>]/1-2-3-2-4-5/b3-c1_b4-e1_c7-d1_e2-f1_a?-b1");
		sampleLists.add("WURCS=2.0/4,8,7/[<Q>][a1122h-1a_1-5][a2112h-1a_1-5][a2122h-1a_1-5]/1-2-2-3-4-4-2-4/a5-b1_b4-c1_c2-d1_c6-g1_d3-e1_e2-f1_g3-h1");				
		sampleLists.add("WURCS=2.0/2,6,5/[a122h-1x_1-4][<Q>]/1-1-2-1-1-2/a5-b1_b3-c1_b5-d1_d5-e1_e5-f1");
		sampleLists.add("WURCS=2.0/3,3,3/[a2112m-1a_1-5_2*NCC/3=O][a1122A-1b_1-5_2*NCC/3=O_3*NCC/3=O][<Q>-?b]/1-2-3/a3-b1_b4-c1_a1-c4~n");
		sampleLists.add("WURCS=2.0/2,3,2/[<Q>][<Q>-?a]/1-2-2/a7-b1_b7-c1");
		sampleLists.add("WURCS=2.0/6,6,5/[Aad1122h-2a_2-?][a11221h-1x_1-5][a11221h-1a_1-5][a2122h-1a_1-?_2*NCC/3=O][a2122h-1b_1-5][<Q>_2*OCC/3=O]/1-2-3-4-5-6/a5-b1_b3-c1_b4-e1_c2-d1_e4-f1");
		sampleLists.add("WURCS=2.0/2,2,1/[<Q>-?b][Aad21121m-2a_2-6_5*NCC/3=O_7*NCC/3=O]/1-2/a3-b2");
		sampleLists.add("WURCS=2.0/4,4,4/[<Q>-?a_7-9*OC^XO*/3CO/6=O/3C_5*OCC/3=O][a2122h-1a_1-5][a2122h-1b_1-5][a1122h-1b_1-5]/1-2-3-4/a4-b1_b4-c1_c4-d1_a2-d3~n");
		sampleLists.add("WURCS=2.0/5,6,5/[<Q>][a11221h-1a_1-5][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a2122h-1b_1-5_6*OPO/3O/3=O][a2122h-1b_1-5]/1-2-3-2-4-5/a5-b1_b3-c1_b4-f1_c2-d1_d2-e1");
		sampleLists.add("WURCS=2.0/1,2,1/[<Q>]/1-1/a1-b7");
		sampleLists.add("WURCS=2.0/6,7,6/[<Q>][a11221h-1a_1-5][a11221h-1a_1-5_6*OP^XOCCN/3O/3=O][a2122h-1b_1-5][a2112h-1b_1-5][a2122h-1b_1-5_6*OPO/3O/3=O]/1-2-3-2-4-5-6/a5-b1_b3-c1_b4-g1_c2-d1_d2-e1_e4-f1");
		sampleLists.add("WURCS=2.0/2,5,4/[a122h-1x_1-4][<Q>]/1-1-1-2-1/b5-c1_c5-d1_a?-b1_a?-e1");
		sampleLists.add("WURCS=2.0/6,7,6/[<Q>][a11221h-1x_1-5][a11221h-1x_1-5_6*OP^XOCCN/3O/3=O][a2112h-1b_1-5][a2122h-1b_1-5_6*OPO/3O/3=O][a2122h-1b_1-5]/1-2-3-2-4-5-6/a5-b1_b3-c1_b4-f1_c2-d1_d3-e1_f4-g1");
		sampleLists.add("WURCS=2.0/2,2,2/[<Q>-?b][<Q>-?a]/1-2/a7-b1_a1-b7~n");
		sampleLists.add("WURCS=2.0/2,4,3/[a122h-1x_1-4][<Q>]/1-1-1-2/a5-b1_b5-c1_c5-d1");
		sampleLists.add("WURCS=2.0/7,13,12/[u2122h][a2122h-1a_1-5][a2122h-1b_1-5][a2112h-1b_1-5][a2211m-1a_1-5_3*OC][a121h-1b_1-5][<Q>]/1-2-3-4-5-6-6-6-6-6-6-6-7/a1-b1_b4-c1_c3-d1_d3-e1_e4-f1_f4-g1_g4-h1_h4-i1_i4-j1_j4-k1_k4-l1_l3-m1");
		sampleLists.add("WURCS=2.0/5,7,6/[<Q>][a11221h-1x_1-5][a2122h-1x_1-5_2*NCC/3=O][a2112h-1x_1-5][a2122h-1x_1-5]/1-2-2-3-2-4-5/a5-b1_b3-c1_b7-g1_c3-d1_c7-e1_e7-f1");
		sampleLists.add("WURCS=2.0/3,7,7/[a2112A-1x_1-5][a2211m-1x_1-5][<Q>]/1-2-1-1-1-2-3/a4-b1_b2-c1_b4-g1_c4-d1_d4-e1_e4-f1_a1-f2~n");
		sampleLists.add("WURCS=2.0/7,13,12/[u2122h][a2122h-1a_1-5][a2122h-1b_1-5][a2112h-1b_1-5][a2211m-1a_1-5_3*OC][a121h-1b_1-5][<Q>-?a]/1-2-3-4-5-6-6-6-6-6-6-6-7/a1-b1_b4-c1_c3-d1_d3-e1_e4-f1_f4-g1_g4-h1_h4-i1_i4-j1_j4-k1_k4-l1_l3-m1");
		
		for (String wurcs : sampleLists) {
			try {
				ArrayList<String> items = new ArrayList<String>();
				items.add(wurcs);

				while (!items.isEmpty()) {
					for (String item : items) {
						items = converterInterface(item);
					}
				}
			} catch (SubsumptionException e) {
				error.append(wurcs + "\n");
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}	
		
//		Assert.assertEquals("Convert Successfully","WURCS=2.0/3,4,3/[o222h][a2112h-1x_1-5_2*NCC/3=O][a2122h-1x_1-5_2*NCC/3=O]/1-2-2-3/a?-b1_b?-c1_c?-d1", testSCNormal.getAmbiguousWURCSseq());
//		Assert.assertEquals("WURCS=2.0/2,2,2/[h4344h_2*NCC/3=O][a2112h-1x_1-5]/1-2/a?-b1_a?-b?", testSCCyclic.getAmbiguousWURCSseq());
//		Assert.assertNull(testSCRepeat.getAmbiguousWURCSseq());
//		Assert.assertEquals("WURCS=2.0/4,11,10/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a2112h-1x_1-5][a1221m-1x_1-5]/1-1-2-2-1-3-2-1-4-1-3/a?-b1_a?-i1_b?-c1_c?-d1_c?-g1_d?-e1_e?-f1_g?-h1_j?-k1_j1-d?|g?}", testSCBranch.getAmbiguousWURCSseq());
//		Assert.assertEquals("WURCS=2.0/4,10,9/[a2122h-1x_1-5_2*NCC/3=O][a1122h-1x_1-5][a2112h-1x_1-5_2*NCC/3=O_4*OSO/3=O/3=O][a2112h-1x_1-5_2*NCC/3=O]/1-1-2-2-1-3-2-1-4-1/a?-b1_b?-c1_c?-d1_c?-g1_c?-j1_d?-e1_e?-f1_g?-h1_h?-i1", testSCAlternative.getAmbiguousWURCSseq());
//		Assert.assertEquals("WURCS=2.0/2,6,5/[u2122h_2*NCC/3=O][u1122h]/1-1-1-2-2-2/a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?_a?|b?|c?|d?|e?|f?}-{a?|b?|c?|d?|e?|f?",testSCtopology.getAmbiguousWURCSseq());
		
		System.out.println(error);
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
