package org.glycoinfo.subsumption;

import org.glycoinfo.WURCSFramework.util.WURCSException;
import org.glycoinfo.WURCSFramework.util.WURCSFactory;
import org.glycoinfo.WURCSFramework.util.array.WURCSFormatException;
import org.glycoinfo.WURCSFramework.util.graph.WURCSGraphNormalizer;
import org.glycoinfo.WURCSFramework.util.map.MAPGraphImporter;
import org.glycoinfo.WURCSFramework.util.map.analysis.MAPGraphAnalyzer;
import org.glycoinfo.WURCSFramework.wurcs.graph.*;
import org.glycoinfo.WURCSFramework.wurcs.map.MAPGraph;
import org.glycoinfo.subsumption.util.graph.analysis.SubsumptionLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Subsumption convert
 * defined level to ambiguous level
 * WURCSlinkageinfo to WURCStopology
 * WURCStopology to WURCScomposition
 * @author Keiko Tokunaga
 */
public class SubsumptionConverter {
	
	private String m_DefinedSeq = null;
	private String m_AmbiguousSeq = null;
	private String m_message = null;
	private int m_WURCSlevel = 0;
	private int m_WURCSAmbiguouslevel = 0;
	private String m_WURCSAmbiguouslevelClassName = null;
	
	private int secondLevel = 0;
	private String secondClass = null;
	private String secondAmbSeq = null;
	
	// WURCSの文字列を指定する
	/**
	 * set Defined WURCSseq
	 * @param WURCSseq String containing Defined WURCSseq 
	 */
	public void setWURCSseq(String WURCSseq) {
		m_DefinedSeq = WURCSseq;
	}
	
	// AmbiguousなWURCSの文字列を指定する
	/**
	 * set Ambiguous WURCSseq
	 * @param Ambiguousseq String containing AmbiguousWURCS
	 */
	public void setAmbiguousWURCS(String ambiguousSeq) {
		m_AmbiguousSeq = ambiguousSeq;
	}
	
	// 指定したWURCSseqを返す
	/**
	 * get Defined WURCSseq
	 * @return String containing Defined WURCSseq
	 */
	public String getWURCSseq(){
		return m_DefinedSeq;
	}
	
	// 変換したWURCSを返す
	/**
	 * get Ambiguous WURCSseq
	 * @return String containing Ambiguous WURCSseq
	 */
	public String getAmbiguousWURCSseq() {
		return m_AmbiguousSeq;
	}
	
	public int getWURCSlevel(){
		return m_WURCSlevel;
	}
	
	public int getAmbiguousWURCSlevel(){
		return m_WURCSAmbiguouslevel;
	}
	
	public String getAmbiguousWURCSlevelClassName(){
		return m_WURCSAmbiguouslevelClassName;
	}
	
	public String getSecondAmbiguousWURCSSeq () {
		return secondAmbSeq;
	}
	
	public int getSecondAmbiguousWURCSlevel () {
		return secondLevel;
	}

	public String getSecondAmbiguousWURCSlevelClassName () {
		return secondClass;
	}
	
	//変換処理の流れ
	/**
	 * Convert defined level WURCS to ambiguous level WURCS
	 * @return int containing Error log
	 * @throws SubsumptionException 
	 */
	public int convertDefined2Ambiguous() throws SubsumptionException{
		// m_DefinedSeqにWURCSが指定されているか確認
		if(m_DefinedSeq == null){
			setMessage("DefinedSeq is empty");
			throw new SubsumptionException("DefinedSeq is empty");
		}
		
		// m_DefinedSeqをWURCSFactoryに渡し、WURCSGraphを得る
		WURCSGraph t_inputWURCSGraph = null;
		WURCSGraphNormalizer t_inputWURCSGraphNormalizer = new WURCSGraphNormalizer();

		try {
			WURCSFactory t_inFactory = new WURCSFactory(m_DefinedSeq);
			t_inputWURCSGraph = t_inFactory.getGraph();
			t_inputWURCSGraphNormalizer.start(t_inputWURCSGraph);
		} catch (WURCSException e) {
			setMessage(e.getErrorMessage());
			throw new SubsumptionException(e.getErrorMessage(),e);
		}	
		
		//状態判定（Level 1 では無かった時、終了させる）
		WURCSGraphStateDeterminator tStateGet = new WURCSGraphStateDeterminator();
		SubsumptionLevel tLevel = tStateGet.getSubsumptionLevel(t_inputWURCSGraph);	
		m_WURCSlevel = tLevel.getLevel();
		
		//if ( tLevel != SubsumptionLevel.LV1 && tLevel != SubsumptionLevel.LV2  && tLevel != SubsumptionLevel.LV3){
		if (tLevel == SubsumptionLevel.LV0 || tLevel == SubsumptionLevel.LVX || tLevel == SubsumptionLevel.LV5) {
			setMessage("this WURCSseq is not Level 1 or 2 or 3");
			throw new SubsumptionException("this WURCSseq is not Level 1 or 2 or 3");
		}

		// Repeat structure is thrown exception
		if (m_DefinedSeq.indexOf('~') != -1) {
		    // 部分一致ではない
			setMessage("this WURCSseq is Repeat structure");
			throw new SubsumptionException("this WURCSseq is Repeat structure");
		}
		
		//変換メソッドを呼び出す
		WURCSGraph t_outputWURCSGraph = null;
		WURCSGraph secondGraph = null;
		try {
			if (tLevel == SubsumptionLevel.LV1)
				t_outputWURCSGraph = this.convertTopology(t_inputWURCSGraph);
			if (tLevel == SubsumptionLevel.LV2){
				//System.out.println("before convertComposition");
				t_outputWURCSGraph = this.convertCompositionWithLinkage(t_inputWURCSGraph);
				//System.out.println("after convertComposition");
			}
			if (tLevel == SubsumptionLevel.LV3){
				//System.out.println("before convertBaseComposition");
				//t_outputWURCSGraph = this.convertBaseCompositionWithLinkage(t_inputWURCSGraph);
				//System.out.println("after convertBaseComposition");
				/* To 4A */
				t_outputWURCSGraph = convertComposition(t_inputWURCSGraph);
				/* To 4B */
				secondGraph = convertBaseCompositionWithLinkage(t_inputWURCSGraph);

			}
			/* 4A or 4B to 5 */
			if (tLevel == SubsumptionLevel.LV4A || tLevel == SubsumptionLevel.LV4B) {
				t_outputWURCSGraph = convertBaseComposition(t_inputWURCSGraph);
			}
		} catch (WURCSException e) {
			setMessage(e.getErrorMessage());
			throw new SubsumptionException(e.getErrorMessage(),e);
			//e.printStackTrace();
		}
		
		/**/
		m_AmbiguousSeq = modifyWURCS(t_outputWURCSGraph);
		SubsumptionLevel ambiguousLevel = generateAmbiguousString(t_outputWURCSGraph, m_AmbiguousSeq);
		m_WURCSAmbiguouslevel = ambiguousLevel.getLevel();
		m_WURCSAmbiguouslevelClassName = ambiguousLevel.getClassName();

		/* defined level 4B */
		if (secondGraph != null) {
			secondAmbSeq = modifyWURCS(secondGraph);
			SubsumptionLevel secondSubLevel = generateAmbiguousString(t_outputWURCSGraph, secondAmbSeq);
			secondLevel = secondSubLevel.getLevel();
			secondClass = secondSubLevel.getClassName();
		}
		
		return 0;
	}
	
	private String modifyWURCS (WURCSGraph _graph) throws SubsumptionException {
		String ret = "";
		
		//WURCS graphを用いて重複処理（Factoryで）
		try {
			WURCSFactory t_outFactory = new WURCSFactory(_graph);
			//m_AmbiguousSeq = t_outFactory.getWURCS();
			ret = t_outFactory.getWURCS();
		} catch (WURCSException e) {
			setMessage(e.getErrorMessage());
			throw new SubsumptionException(e.getErrorMessage(),e);
			//e.printStackTrace();
		}
		
		return ret;
	}

	private SubsumptionLevel generateAmbiguousString (WURCSGraph t_outputWURCSGraph, String _seq) throws SubsumptionException {
		// check the converted WURCS level 
		WURCSGraph t_inputAmbiguousWURCSGraph = null;
		try {
			//WURCSFactory t_inputWURCStopologyFactory = new WURCSFactory(m_AmbiguousSeq);
			WURCSFactory t_inputWURCStopologyFactory = new WURCSFactory(_seq);
			t_inputAmbiguousWURCSGraph = t_inputWURCStopologyFactory.getGraph();
		} catch (WURCSException e) {
			setMessage(e.getErrorMessage());
			throw new SubsumptionException(e.getErrorMessage(),e);
		}
		WURCSGraphStateDeterminator t_AmbiguousStateGet = new WURCSGraphStateDeterminator();
		SubsumptionLevel t_AmbiguousLevel = t_AmbiguousStateGet.getSubsumptionLevel(t_inputAmbiguousWURCSGraph);	
		
		//m_WURCSAmbiguouslevel = t_AmbiguousLevel.getLevel();
		//m_WURCSAmbiguouslevelClassName = t_AmbiguousLevel.getClassName();
		
		return t_AmbiguousLevel;
	}
	
	//public int convert
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
	
	/**
	 * Convert WURCSlinkageinfo of backbone, linkage and modification to WURCStopology one
	 * Change Linkage position (anomeric position is remain, else is unknown "?")
	 * Change Anomeric symbol ("a" or "b" to "x")
	 * @param a_origGraph WURCSGraph: containing WURCSlinkageinfo WURCSGraph
	 * @return convert WURCSGraph: WURCStopology WURCSGraph
	 * @throws WURCSException
	 */
	private WURCSGraph convertTopology(WURCSGraph a_origGraph) throws WURCSException{
		//System.out.println("enter convertTopology");
		WURCSGraph convert = new WURCSGraph();
		HashMap<Backbone, Backbone> a_hashOrigToConvertBackbone = new HashMap<Backbone, Backbone>();
		HashMap<Modification, Modification> a_hashOrigToConvertModification = new HashMap<Modification, Modification>();
		HashMap<WURCSEdge,WURCSEdge> a_hashOrigToConvertLinkage = new HashMap<WURCSEdge,WURCSEdge>();
		
		for ( Backbone t_origBack : a_origGraph.getBackbones() ) {
			
			char t_AS = t_origBack.getAnomericSymbol();
	
			Backbone t_convertBack = null;
			if( t_AS != 'a' && t_AS != 'b'){
				t_convertBack = t_origBack.copy();
			}else{
				// TODO x,u,oでなかった場合、makeXBackboneで変換を行う
				if (t_origBack instanceof Backbone_TBD)
					t_convertBack = this.makeXBackbone((Backbone_TBD)t_origBack); 
				if (t_origBack instanceof BackboneUnknown_TBD)
					t_convertBack = this.makeXBackboneUnknown(t_origBack);
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

	/**
	 * MonosaccharideCompositionWithLinkage
	 * Convert WURCSTopology of backbone, linkage and modification to WURCScomposition one
	 * @param a_origGraph WURCSGraph: containing WURCStopology WURCSGraph
	 * @return convert WURCSGraph: WURCScomposition WURCSGraph
	 * @throws WURCSException
	 */
	private WURCSGraph convertCompositionWithLinkage(WURCSGraph a_origGraph) throws WURCSException{
		//System.out.println("enter convertComposition");
		WURCSGraph t_convertGraph = a_origGraph.copy();
		WURCSGraph convert = new WURCSGraph();
		
		//TODO 1 originalのWURCSGraphからRINGの情報を消す
		t_convertGraph = this.deleteRING(a_origGraph);
		
		//TODO 2 BackboneのCarbon Descriptionを変換する
		ArrayList<Backbone> t_convertBackList = new ArrayList<Backbone>();
		ArrayList<Backbone> t_NotGlicosicBackList = new ArrayList<Backbone>();
		ArrayList<WURCSEdge> t_NotGlicosicEdge = new ArrayList<WURCSEdge>();
		ArrayList<Modification> t_isNotGlycosidic = new ArrayList<Modification>();
		ArrayList<Modification> t_isGlycosidic = new ArrayList<Modification>();
		WURCSEdge t_convertEdge = new WURCSEdge();
		
		for (Backbone t_Backbone : t_convertGraph.getBackbones()){
			Backbone t_convertBack = null;
			int t_AnomericPosition = t_Backbone.getAnomericPosition();
			if (t_Backbone instanceof Backbone_TBD) {
				t_convertBack = this.covertAnomericPositionCarbonDescriptor(t_Backbone,t_AnomericPosition);				
			}
			if (t_Backbone instanceof BackboneUnknown_TBD) {
				t_convertBack = t_Backbone.copy();
			}
			t_convertBackList.add(t_convertBack);
			//convert.addBackbone(t_convertBack);
		}
		
		int countGlicosic = 0;
		for (Backbone t_convertBack : t_convertGraph.getBackbones()) {
			for ( WURCSEdge t_origEdge : t_convertBack.getEdges() ) {
				Modification t_origModif = t_origEdge.getModification();
				//TODO !isGlycosidicのListを作る
				if (!t_origModif.isGlycosidic()){
					t_NotGlicosicBackList.add(t_convertBackList.get(countGlicosic));
					t_isNotGlycosidic.add(t_origModif);
					t_NotGlicosicEdge.add(t_origEdge);
				}
			}
			countGlicosic++;
		}
		
		//TODO isGlycosidicのListを作る
		for (Modification t_origModif : t_convertGraph.getModifications()){
			if (t_origModif.isGlycosidic()){
				t_isGlycosidic.add(t_origModif);
				t_convertEdge = t_origModif.getEdges().get(0);
			}	
		}
		
		//TODO 3 ModificationのType判定を行う
		int count_isNotGlicosidic = 0;
		for (Modification t_convertModif: t_isNotGlycosidic){
			//TODO 4 3の結果がTypeIIIの場合は、結合情報の部分にMAを利用して修飾基の情報を移動
			//TODO 5 TypeIIのModificationをconvert.addResiduesで追加する（!isGlycosidicで判定）
			String t_MAPCode = t_convertModif.getMAPCode();
			if (t_MAPCode ==""){
				convert.addResidues(t_NotGlicosicBackList.get(count_isNotGlicosidic), t_NotGlicosicEdge.get(count_isNotGlicosidic), t_convertModif);
				count_isNotGlicosidic++;
			}else if (this.checkModifType(t_convertModif).isTypeII()){
				convert.addResidues(t_NotGlicosicBackList.get(count_isNotGlicosidic), t_NotGlicosicEdge.get(count_isNotGlicosidic), t_convertModif);
				count_isNotGlicosidic++;
			}else if (this.checkModifType(t_convertModif).isTypeIII()){
				ModificationAlternative t_MdifAlter = new ModificationAlternative(t_MAPCode);
				for (Backbone t_MABackbone : t_convertBackList){				
					WURCSEdge t_LeadInLink = new WURCSEdge();
					t_LeadInLink = this.changeUnknownLinkage(t_convertEdge);
					t_MdifAlter.addLeadInEdge(t_LeadInLink);				
					convert.addResidues(t_MABackbone, t_LeadInLink, t_MdifAlter);
				}
				count_isNotGlicosidic++;
			}else {
				count_isNotGlicosidic++;
			}
		}
		
		//TODO 6 単糖間の結合情報をMA作成メソッドを使用して作成し、convert.addResiduesで追加する
		for (Modification t_GlycosidicModif : t_isGlycosidic){
			String t_MAPCode = t_GlycosidicModif.getMAPCode();
			ModificationAlternative t_ModifAlter = new ModificationAlternative(t_MAPCode);
			for (Backbone t_MABackbone : t_convertBackList){				
				WURCSEdge t_LeadInLink = new WURCSEdge();
				WURCSEdge t_LeadOutLink = new WURCSEdge();
				t_LeadInLink = this.changeUnknownLinkage(t_convertEdge);
				t_LeadOutLink = this.changeUnknownLinkage(t_convertEdge);
				t_ModifAlter.addLeadInEdge(t_LeadInLink);				
				t_ModifAlter.addLeadOutEdge(t_LeadOutLink);
				convert.addResidues(t_MABackbone, t_LeadInLink, t_ModifAlter);
				convert.addResidues(t_MABackbone, t_LeadOutLink, t_ModifAlter);	
			}
		}
		return convert;
	}
	
	/**
	 * BaseCompositionWithLinkage
	 * Convert WURCScomposition of backbone, linkage and modification to WURCSbasecomposition one
	 * @param a_origGraph WURCSGraph: containing WURCScomposition WURCSGraph
	 * @return convert WURCSGraph: WURCSbasecomposition WURCSGraph
	 * @throws WURCSException
	 */
	private WURCSGraph convertBaseCompositionWithLinkage(WURCSGraph a_origWURCSGraph) throws WURCSException {
		WURCSGraph convert = new WURCSGraph();
		HashMap<Backbone, Backbone> a_hashOrigToConvertBackbone = new HashMap<Backbone, Backbone>();
		HashMap<Modification, Modification> a_hashOrigToConvertModification = new HashMap<Modification, Modification>();
		HashMap<WURCSEdge,WURCSEdge> a_hashOrigToConvertLinkage = new HashMap<WURCSEdge,WURCSEdge>();

		for (Backbone t_Backbone : a_origWURCSGraph.getBackbones()){
			Backbone t_convertBack = null;
			if (t_Backbone instanceof Backbone_TBD) { 
				t_convertBack = this.covertAmbiguousCarbonDescriptor(t_Backbone);
			}
			if (t_Backbone instanceof BackboneUnknown_TBD) {
				t_convertBack = t_Backbone.copy();
			}
			a_hashOrigToConvertBackbone.put(t_Backbone, t_convertBack);

			for ( WURCSEdge t_origEdge : t_Backbone.getEdges() ) {
				
				Modification t_origModif = t_origEdge.getModification(); 
				if ( !a_hashOrigToConvertModification.containsKey(t_origModif) )
					a_hashOrigToConvertModification.put(t_origModif, t_origModif.copy()); //変換に変える
				Modification t_copyModif = a_hashOrigToConvertModification.get(t_origModif);
				WURCSEdge t_convertLink = a_hashOrigToConvertLinkage.get(t_origEdge);
				// TODO Linkageを判定して、コピーかコンバートする
				t_convertLink = t_origEdge.copy();
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

	/**
	 * Generate a subsumption level 4a from level 3.
	 * @param _graph
	 * @return
	 */
	private WURCSGraph convertComposition (WURCSGraph _graph) throws WURCSException {
		WURCSGraph ret = new WURCSGraph();
		
		for (Backbone bb : _graph.getBackbones()) {
			Backbone copy_bb = bb.copy();
			
			/* remove WURCSEdges */
			for (WURCSEdge we : removeGlycosidicLinkage(bb)) {
				copy_bb.addEdge(we);
			}
			
			for (WURCSEdge ce : copy_bb.getChildEdges()) {
				Modification mod = ce.getModification();
				Modification copy_mod = mod.copy();
				WURCSEdge copy_ce = ce.copy();
				ret.addResidues(copy_bb, copy_ce, copy_mod);

				/* For alternative unit copy*/
				if (!(mod instanceof ModificationAlternative ) ) continue;
				/* For lead in edges*/
				if (((ModificationAlternative) mod).getLeadInEdges().contains(ce))
					((ModificationAlternative) copy_mod).addLeadInEdge(copy_ce);
				/* For lead out edges */
				if (((ModificationAlternative) mod).getLeadOutEdges().contains(ce))
					((ModificationAlternative) copy_mod).addLeadOutEdge(copy_ce);
			}
			
			if (!ret.getBackbones().contains(copy_bb)) ret.addBackbone(copy_bb);
		}

		return ret;
	}

	/**
	 * Generate a subsumption level 5 from level 4a or 4b.
	 * @param _graph
	 * @return
	 */
	private WURCSGraph convertBaseComposition (WURCSGraph _graph) throws WURCSException {
		WURCSGraph ret = new WURCSGraph();
		
		for (Backbone bb : _graph.getBackbones()) {
			Backbone copy_bb = null;
			if (bb instanceof Backbone_TBD)
				copy_bb = covertAmbiguousCarbonDescriptor(bb);
			if (bb instanceof BackboneUnknown_TBD)
				copy_bb = bb.copy();
			
			/* remove glycosidic linkage */
			for (WURCSEdge we : removeGlycosidicLinkage(bb)) {
				copy_bb.addEdge(we);
			}

			/* append substituents */
			for (WURCSEdge ce : copy_bb.getChildEdges()) {
				Modification mod = ce.getModification();
				Modification copy_mod = mod.copy();
				WURCSEdge copy_ce = ce.copy();
				ret.addResidues(copy_bb, copy_ce, copy_mod);

				/* For alternative unit copy*/
				if (!(mod instanceof ModificationAlternative ) ) continue;
				/* For lead in edges*/
				if (((ModificationAlternative) mod).getLeadInEdges().contains(ce))
					((ModificationAlternative) copy_mod).addLeadInEdge(copy_ce);
				/* For lead out edges */
				if (((ModificationAlternative) mod).getLeadOutEdges().contains(ce))
					((ModificationAlternative) copy_mod).addLeadOutEdge(copy_ce);
			}
			
			if (!ret.getBackbones().contains(copy_bb)) ret.addBackbone(copy_bb);
		}

		return ret;
	}

	private LinkedList<WURCSEdge> removeGlycosidicLinkage (Backbone _backbone) throws WURCSException {
		LinkedList<WURCSEdge> edges = _backbone.getChildEdges();
		
		for (WURCSEdge ce : _backbone.getChildEdges()) {
			if (!ce.getModification().isGlycosidic()) continue;
			if (!(ce.getNextComponent() instanceof ModificationAlternative)) continue;

			edges.remove(ce);
		}

		return edges;
	}

	private boolean hasGlycosidicLinkage (Backbone _backbone) {
		boolean ret = false;
		for (WURCSEdge ce : _backbone.getChildEdges()) {
			if (ce.getModification().isGlycosidic()) ret = true;
		}

		return ret;
	}

	private Backbone_TBD covertAmbiguousCarbonDescriptor(Backbone a_Backbone) {
		// change ambiguous carbon descriptor （1,2,3,4→x 5,6,7,8→X e,z→f E,Z→F?）
		Backbone_TBD t_convertBack = new Backbone_TBD();
		for (BackboneCarbon t_oBC : a_Backbone.getBackboneCarbons()){
			char t_oBCchar = t_oBC.getDesctriptor().getChar();
			BackboneCarbon bc = t_oBC;
			if ( t_oBCchar == '1' || t_oBCchar == '2' || t_oBCchar == '3' || t_oBCchar == '4')
				bc = this.makexcarbonDescriptor(a_Backbone);				
			if ( t_oBCchar == '5' || t_oBCchar == '6' || t_oBCchar == '7' || t_oBCchar == '8')
				bc = this.makeXcarbonDescriptor(a_Backbone);
			if ( t_oBCchar == 'e' || t_oBCchar == 'z')
				bc = this.makefcarbonDescriptor(a_Backbone);	
			if ( t_oBCchar == 'E' || t_oBCchar == 'Z')
				bc = this.makeFcarbonDescriptor(a_Backbone);	
			t_convertBack.addBackboneCarbon(bc);
		}		
		return t_convertBack;
	}

	private BackboneCarbon makeFcarbonDescriptor(Backbone a_Backbone) {
		CarbonDescriptor_TBD t_CD_TBD = CarbonDescriptor_TBD.DZ2_CISTRANS_XU;
		return new BackboneCarbon(a_Backbone, t_CD_TBD);
	}

	private BackboneCarbon makefcarbonDescriptor(Backbone a_Backbone) {
		CarbonDescriptor_TBD t_CD_TBD = CarbonDescriptor_TBD.DZ2_CISTRANS_XL;
		return new BackboneCarbon(a_Backbone, t_CD_TBD);
	}

	private BackboneCarbon makeXcarbonDescriptor(Backbone a_Backbone) {
		CarbonDescriptor_TBD t_CD_TBD = CarbonDescriptor_TBD.SZ3_STEREO_X_U;
		return new BackboneCarbon(a_Backbone, t_CD_TBD);
	}

	private BackboneCarbon makexcarbonDescriptor(Backbone a_Backbone) {
		CarbonDescriptor_TBD t_CD_TBD = CarbonDescriptor_TBD.SZ3_STEREO_X_L;
		return new BackboneCarbon(a_Backbone, t_CD_TBD);
	}

	/**
	 * Change a_origEdge's LinkagePosition, Make Backbone position "?" 
	 * @param WURCSEdge a_origEdge
	 * @return WURCSEdge t_convertLink
	 */
	private WURCSEdge changeUnknownLinkage(WURCSEdge a_origEdge) {
		WURCSEdge t_convertLink = new WURCSEdge();
		LinkedList<LinkagePosition> t_AP = a_origEdge.getLinkages();							
		LinkagePosition a_AP_LinkagePosition = t_AP.get(0);
		DirectionDescriptor t_DD = DirectionDescriptor.X;
		LinkagePosition t_makeLP = null;
//		t_makeLP = new LinkagePosition(-1, a_AP_LinkagePosition.getDirection(), true, a_AP_LinkagePosition.getModificationPosition() , true);
		t_makeLP = new LinkagePosition(-1, t_DD, true, a_AP_LinkagePosition.getModificationPosition() , true);
		t_convertLink.addLinkage(t_makeLP);
		
		return t_convertLink;	
	}
	
	/**
	 * Check the MAPcode type of Modification
	 * @param Modification a_convertModif
	 * @return MAPGraphAnalyzer t_oMAPAnal
	 * @throws WURCSFormatException 
	 */
	private MAPGraphAnalyzer checkModifType(Modification a_convertModif) throws WURCSFormatException {
		String t_MAPCode = a_convertModif.getMAPCode();
		MAPGraph t_oMAP = (new MAPGraphImporter()).parseMAP(t_MAPCode);
		MAPGraphAnalyzer t_oMAPAnal = new MAPGraphAnalyzer(t_oMAP);
		//System.out.println(t_oMAPAnal.getType());
		return t_oMAPAnal;
	}
	
	/**
	 * convert the Carbon Descriptor of Backbone
	 * @param a_AnomericPosition 
	 * @param Backbone a_Backbone
	 * @return Backbone_TBD t_convertBack
	 */
	private Backbone_TBD covertAnomericPositionCarbonDescriptor(Backbone a_Backbone, int a_AnomericPosition) {
		// change carbon descriptor if 'a' or 'o' in aldose and if 'a' or 'O' in ketose
		Backbone_TBD t_convertBack = new Backbone_TBD();
		LinkedList<BackboneCarbon> t_oBC = a_Backbone.getBackboneCarbons();
		t_convertBack = (Backbone_TBD) a_Backbone.copy();
		int t_APInArray = 0;
		if (a_AnomericPosition > 1)	t_APInArray = a_AnomericPosition - 1;
		char t_AnomericBC = t_oBC.get(t_APInArray).getDesctriptor().getChar();
		
		if ( a_AnomericPosition == 1 && (t_AnomericBC == 'a' || t_AnomericBC == 'o')){
			BackboneCarbon bc = this.makeucarbonDescriptor(a_Backbone);
			t_convertBack.getBackboneCarbons().remove(t_APInArray);
			t_convertBack.getBackboneCarbons().add(t_APInArray, bc);
			t_convertBack.setAnomericPosition(0);
			t_convertBack.setAnomericSymbol('x');

		}
		if ( a_AnomericPosition == 2 && (t_AnomericBC == 'a' || t_AnomericBC == 'O')){
				BackboneCarbon bc = this.makeUcarbonDescriptor(a_Backbone);
				t_convertBack.getBackboneCarbons().remove(t_APInArray);
				t_convertBack.getBackboneCarbons().add(t_APInArray, bc);
				t_convertBack.setAnomericPosition(0);
				t_convertBack.setAnomericSymbol('x');
		}
		
		return t_convertBack;
	}
	
	/**
	 * Make "u" Carbon Descriptor
	 * @param Backbone a_oBackbone
	 * @return new BackboneCarbon 
	 */
	public BackboneCarbon makeucarbonDescriptor(Backbone a_oBackbone) {
		CarbonDescriptor_TBD t_CD_TBD = CarbonDescriptor_TBD.SZX_UNDEF_L;
		return new BackboneCarbon(a_oBackbone, t_CD_TBD);
	}
	
	/**
	 * Make "U" Carbon Descriptor
	 * @param Backbone a_oBackbone
	 * @return new BackboneCarbon 
	 */
	public BackboneCarbon makeUcarbonDescriptor(Backbone a_oBackbone) {
		CarbonDescriptor_TBD t_CD_TBD = CarbonDescriptor_TBD.SSX_UNDEF_U;
		return new BackboneCarbon(a_oBackbone, t_CD_TBD);
	}
	
	/**
	 * delete RING of original Graph
	 * @param WURCSGraph a_origGraph
	 * @return WURCSGraph a_origGraph after converted
	 * @throws WURCSException
	 */
	public WURCSGraph deleteRING(WURCSGraph a_origGraph) throws WURCSException {
		ArrayList<Modification> t_deleteModif = new ArrayList<Modification>();
		for (Modification t_origModif: a_origGraph.getModifications()){
			if (t_origModif.isRing())
				t_deleteModif.add(t_origModif);
		}
		for (Modification t_origModif: t_deleteModif){
				a_origGraph.removeModification(t_origModif);
		}
		return a_origGraph;
	}

	public BackboneUnknown_TBD makeXBackboneUnknown(Backbone _backbone) {
		BackboneUnknown_TBD convert = new BackboneUnknown_TBD('x');
		convert.removeAllEdges();
		return convert;
	}
	
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

