package org.glycoinfo.subsumption;

import java.util.HashMap;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.glycoinfo.WURCSFramework.util.WURCSException;
import org.glycoinfo.WURCSFramework.util.WURCSFactory;
import org.glycoinfo.WURCSFramework.util.exchange.WURCSArrayToGraph;
import org.glycoinfo.WURCSFramework.util.graph.WURCSGraphNormalizer;
import org.glycoinfo.subsumption.WURCSGraphStateDeterminator;
import org.glycoinfo.subsumption.util.graph.analysis.SubsumptionLevel;
import org.glycoinfo.WURCSFramework.util.graph.visitor.WURCSVisitor;
import org.glycoinfo.WURCSFramework.util.graph.visitor.WURCSVisitorExpandRepeatingUnit;
import org.glycoinfo.WURCSFramework.wurcs.array.WURCSArray;
import org.glycoinfo.WURCSFramework.wurcs.graph.Backbone;
import org.glycoinfo.WURCSFramework.wurcs.graph.BackboneCarbon;
import org.glycoinfo.WURCSFramework.wurcs.graph.Backbone_TBD;
import org.glycoinfo.WURCSFramework.wurcs.graph.LinkagePosition;
import org.glycoinfo.WURCSFramework.wurcs.graph.Modification;
import org.glycoinfo.WURCSFramework.wurcs.graph.ModificationAlternative;
import org.glycoinfo.WURCSFramework.wurcs.graph.WURCSEdge;
import org.glycoinfo.WURCSFramework.wurcs.graph.WURCSGraph;

/**
 * Subsumption convert WURCSlinkageinfo to WURCStopology
 * @author Keiko Tokunaga
 *
 */
public class Subsumption_Converter {
	
	private String m_LinkageInfoSeq = null;
	private String m_TopologySeq = null;
	private String m_message = null;
	private int m_WURCSlevel = 0;
	private int m_WURCStopologylevel = 0;
	private String m_WURCStopologylevelClassName = null;
	
	// WURCSの文字列を指定する
	/**
	 * set WURCSseq (WURCSLinkageInfo)
	 * @param WURCSseq String containing WURCSLinkageInfo 
	 */
	public void setWURCSseq(String WURCSseq) {
		m_LinkageInfoSeq = WURCSseq;
	}
	
	// WURCSTopologyの文字列を指定する
	/**
	 * set WURCStopology
	 * @param Topologyseq String containing WURCStopology
	 */
	public void setWURCStopology(String Topologyseq) {
		m_TopologySeq = Topologyseq;
	}
	
	// 指定したWURCSseqを返す
	/**
	 * get WURCSseq (LinkageInfoSeq)
	 * @return String containing LinkageInfoSeq
	 */
	public String getWURCSseq(){
		return m_LinkageInfoSeq;
	}
	
	// 変換したWURCSを返す
	/**
	 * get WURCStopology
	 * @return String containing WURCStopology
	 */
	public String getWURCStopology() {
		return m_TopologySeq;
	}
	
	public int getWURCSlevel(){
		return m_WURCSlevel;
	}
	
	public int getWURCStopologylevel(){
		return m_WURCStopologylevel;
	}
	
	public String getWURCStopologylevelClassName(){
		return m_WURCStopologylevelClassName;
	}
	
	//変換処理の流れ
	/**
	 * Convert WURCSlinkageinfo to WURCStopology
	 * @return int containing Error log
	 * @throws SubsumptionException 
	 */
	public int convertLinkageInfo2Topology() throws SubsumptionException{
		// m_LinkageInfoSeqにWURCSが指定されているか確認
		if(m_LinkageInfoSeq == null){
			setMessage("LinkageInfoSeq is empty");
			throw new SubsumptionException("LinkageInfoSeq is empty");
		}
		
		// m_LinkageInfoSeqをWURCSFactoryに渡し、WURCSGraphを得る
		WURCSGraph t_inputWURCSGraph = null;
		WURCSGraphNormalizer t_inputWURCSGraphNormalizer = new WURCSGraphNormalizer();
		//WURCSVisitorExpandRepeatingUnit t_oExpandRep = new WURCSVisitorExpandRepeatingUnit();
		//WURCSArrayToGraph t_oA2G = new WURCSArrayToGraph();
		try {
			WURCSFactory t_inFactory = new WURCSFactory(m_LinkageInfoSeq);
			t_inputWURCSGraph = t_inFactory.getGraph();
			
			t_inputWURCSGraphNormalizer.start(t_inputWURCSGraph);
//			WURCSArray t_oWURCS = t_inFactory.getArray();
//			t_oA2G.start(t_oWURCS);
//			
			//t_oExpandRep.start(t_inputWURCSGraph);
		} catch (WURCSException e) {
			// TODO 自動生成された catch ブロック
			setMessage(e.getErrorMessage());
			throw new SubsumptionException(e.getErrorMessage(),e);
		}	
		
		//状態判定（Level 1 では無かった時、終了させる）
		WURCSGraphStateDeterminator tStateGet = new WURCSGraphStateDeterminator();
		SubsumptionLevel tLevel = tStateGet.getSubsumptionLevel(t_inputWURCSGraph);	
		m_WURCSlevel = tLevel.getLevel();
		if ( tLevel != SubsumptionLevel.LV1 ){
			setMessage("this WURCSseq is not Level 1");
			throw new SubsumptionException("this WURCSseq is not Level 1");
		}
		
//		// Cyclic structure is thrown exception
//		if (t_inputWURCSGraphNormalizer.hasCyclic()){
//			setMessage("this WURCSseq is Cyclic structure");
//			throw new SubsumptionException("this WURCSseq is Cyclic structure");
//		};	
		
		
		// isExpandedRepeatingUnitが正しくない？
//		if (t_oA2G.isExpandedRepeatingUnit()){
//			setMessage("this WURCSseq is Repeat structure");
//			throw new SubsumptionException("this WURCSseq is Repeat structure");
//		};	
		// Repeat structure is thrown exception
		if (m_LinkageInfoSeq.indexOf('~') != -1) {
		    // 部分一致ではない
			setMessage("this WURCSseq is Repeat structure");
			throw new SubsumptionException("this WURCSseq is Repeat structure");
		}
		
		//変換メソッドを呼び出す
		WURCSGraph t_outputWURCSGraph = null;
		try {
			t_outputWURCSGraph = this.convert(t_inputWURCSGraph);
		} catch (WURCSException e) {
			// TODO 自動生成された catch ブロック
			setMessage(e.getErrorMessage());
			throw new SubsumptionException(e.getErrorMessage(),e);
			//e.printStackTrace();
		}
		
		//WURCS graphを用いて重複処理（Factoryで）
		try {
			WURCSFactory t_outFactory = new WURCSFactory(t_outputWURCSGraph);
			m_TopologySeq = t_outFactory.getWURCS();
		} catch (WURCSException e) {
			// TODO 自動生成された catch ブロック
			setMessage(e.getErrorMessage());
			throw new SubsumptionException(e.getErrorMessage(),e);
			//e.printStackTrace();
		}
		//TopologySeq = "WURCS=2.0/4,4,3/[o222h][a2112h-1x_1-5_2*NCC/3=O][a2112h-1x_1-5_2*NCC/3=O][a2122h-1x_1-5_2*NCC/3=O]/1-2-3-4/a?-b1_b?-c1_c?-d1";
		
		// test case topology level
		WURCSGraph t_inputWURCStopologyGraph = null;
		try {
			WURCSFactory t_inputWURCStopologyFactory = new WURCSFactory(m_TopologySeq);
			t_inputWURCStopologyGraph = t_inputWURCStopologyFactory.getGraph();
		} catch (WURCSException e) {
			// TODO 自動生成された catch ブロック
			setMessage(e.getErrorMessage());
			throw new SubsumptionException(e.getErrorMessage(),e);
		}
		WURCSGraphStateDeterminator t_topologyStateGet = new WURCSGraphStateDeterminator();
		SubsumptionLevel t_topologyLevel = t_topologyStateGet.getSubsumptionLevel(t_inputWURCStopologyGraph);	
		m_WURCStopologylevel = t_topologyLevel.getLevel();
		m_WURCStopologylevelClassName = t_topologyLevel.getClassName();
		
		return 0;
	}
	
	/**
	 * set Message 
	 * @param m String containing Error message
	 */
	private void setMessage(String m){
		m_message = m;
	}
	
	/**
	 * get Message
	 * @return String containing Error message
	 */
	public String getMessage(){
		return m_message;
	}
	
	//Linkage アノマー位そのまま、その他?にする
	//アノマー位a,bをxにする
	/**
	 * Convert WURCSlinkageinfo of backbone, linkage and modification to WURCStopology one
	 * @param a_origGraph WURCSGraph: containing WURCSlinkageinfo WURCSGraph
	 * @return convert WURCSGraph: WURCStopology WURCSGraph
	 * @throws WURCSException
	 */
	private WURCSGraph convert(WURCSGraph a_origGraph) throws WURCSException{
		WURCSGraph convert = new WURCSGraph();
		HashMap<Backbone, Backbone> a_hashOrigToConvertBackbone = new HashMap<Backbone, Backbone>();
		HashMap<Modification, Modification> a_hashOrigToConvertModification = new HashMap<Modification, Modification>();
		HashMap<WURCSEdge,WURCSEdge> a_hashOrigToConvertLinkage = new HashMap<WURCSEdge,WURCSEdge>();
		
		for ( Backbone t_origBack : a_origGraph.getBackbones() ) {
			// TODO 以下の２つをチェックするif文を作る
			Backbone_TBD t_convertBack = null;
			char t_AS = t_origBack.getAnomericSymbol();
			if( t_AS != 'a' && t_AS != 'b'){
				// TODO x,u,oは別の関数でチェックする？copyBackboneでコピーする？
				t_convertBack = ((Backbone_TBD)t_origBack).copy();
			}else{
				// TODO x,u,oでなかった場合、makeXBackboneで変換を行う
				t_convertBack = this.makeXBackbone((Backbone_TBD)t_origBack); //コピーを変換に変える　アノマーの変換を行う
			}
			a_hashOrigToConvertBackbone.put(t_origBack, t_convertBack);
			
			
			for ( WURCSEdge t_origEdge : t_origBack.getEdges() ) {
				
				Modification t_origModif = t_origEdge.getModification(); //オリジナルのModificationを取得
				if ( !a_hashOrigToConvertModification.containsKey(t_origModif) )
					a_hashOrigToConvertModification.put(t_origModif, t_origModif.copy()); //変換に変える
				Modification t_copyModif = a_hashOrigToConvertModification.get(t_origModif);
				WURCSEdge t_convertLink = a_hashOrigToConvertLinkage.get(t_origEdge);
				// TODO Linkageを判定して、コピーかコンバートする
				LinkedList<LinkagePosition> t_AP = t_origEdge.getLinkages();
				LinkagePosition a_AP_LinkagePosition = t_AP.get(0);
				int t_AP_iBackbonePosition = a_AP_LinkagePosition.getBackbonePosition();
				int t_AnomericPosition = t_origBack.getAnomericPosition();
				t_convertLink = t_origEdge.copy();
				//　糖同士の結合で、AnomericPositionがLinkageの位置と違う時-> Linkageを'?'に上書きする
				if(t_origModif.isGlycosidic() && t_AP_iBackbonePosition != t_AnomericPosition){
					t_convertLink = this.makeNewEdge(t_origEdge, a_AP_LinkagePosition); 
				}
				convert.addResidues( t_convertBack, t_convertLink, t_copyModif );
				
				// For alternative unit copy
				if ( !(t_origModif instanceof ModificationAlternative ) ) continue;
				// For lead in edges
				if ( ((ModificationAlternative)t_origModif).getLeadInEdges().contains( t_origEdge ) )
					((ModificationAlternative)t_copyModif).addLeadInEdge(t_convertLink);
				// For lead out edges
				if ( ((ModificationAlternative)t_origModif).getLeadOutEdges().contains( t_origEdge ) )
					((ModificationAlternative)t_copyModif).addLeadOutEdge(t_convertLink);

			}
		}
		return convert;
	}

	// Backboneがa,bであった時、xに変換する関数
	/**
	 * Convert backbone of WURCSlikageinfo to WURCStopology one, if the backbone is a or b
	 * @param a_bb Backbone_TBD containing information of WURCSlikageinfo's backbone
	 * @return convert Backbone_TBD containing convert information of WURCSlinkageinfo to WURCStopology
	 */
	public Backbone_TBD makeXBackbone(Backbone_TBD a_bb) {
		Backbone_TBD convert = new Backbone_TBD();
		for ( BackboneCarbon bc : a_bb.getBackboneCarbons() ) {
			convert.addBackboneCarbon(bc.copy(convert));
		}
		convert.setAnomericPosition(a_bb.getAnomericPosition());
		convert.setAnomericSymbol('x');
		convert.removeAllEdges();
		return convert;
	}
	
	// AnomericPositionがアノマー位と違う時、?にする関数
	/**
	 * Convert linkage of WURCSlikageinfo to WURCStopology one, if the linkage is not AnomericPosition
	 * @param a_we WURCSEdge containing the original Edge information 
	 * @param a_ql LinkagePosition containing the original LinkagePosion information
	 * @return convert newEdge: the Edge which unknown linkage information '?'
	 */
	public WURCSEdge makeNewEdge(WURCSEdge a_we, LinkagePosition a_ql) {
		LinkagePosition t_makeLP = new LinkagePosition(-1, a_ql.getDirection(), true, a_ql.getModificationPosition(), true);
		WURCSEdge newEdge = new WURCSEdge();
			newEdge.addLinkage(t_makeLP);
		if(a_we.isReverse()){
			newEdge.reverse();
		}else{
			newEdge.forward();
		}
		return newEdge;
	}
}

