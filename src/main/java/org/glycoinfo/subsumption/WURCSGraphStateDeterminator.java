package org.glycoinfo.subsumption;

import org.glycoinfo.WURCSFramework.util.array.WURCSFormatException;
import org.glycoinfo.WURCSFramework.util.map.MAPGraphImporter;
import org.glycoinfo.WURCSFramework.util.map.analysis.MAPGraphAnalyzer;
import org.glycoinfo.WURCSFramework.wurcs.graph.*;
import org.glycoinfo.WURCSFramework.wurcs.map.MAPGraph;
import org.glycoinfo.subsumption.util.graph.analysis.SubsumptionLevel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class WURCSGraphStateDeterminator {

	public WURCSGraphStateDeterminator() {

	}

	public SubsumptionLevel getSubsumptionLevel(WURCSGraph a_oGraph) {
		// For monosaccharide
		if ( a_oGraph.getBackbones().size() == 1 )
			return SubsumptionLevel.LVX;

		// Check glycosidic linkages
		// TODO: LV4 check
//		boolean t_bHasGlycosidicLinkage = false;
//		Iterator<Modification> t_itModification = a_oGraph.getModificationIterator();
//		while ( t_itModification.hasNext() ) {
//			Modification t_oModification = t_itModification.next();
//			if ( t_oModification.isGlycosidic() )
//				t_bHasGlycosidicLinkage = true;
//		}
//
//		if ( !t_bHasGlycosidicLinkage )
//			return SubsumptionLevel.LV3;

		// Check WURCS Composition (level 3)
		// Has the Backbone "a" or "b"?	
		boolean t_bCheckCD = false;
		boolean t_bis1_8ez = false;
		boolean t_bHas1_8ez = true;
		int isBackboneCount = 0;
		
		Iterator<Backbone> t_itBackbone = a_oGraph.getBackboneIterator();
		while ( t_itBackbone.hasNext() ) {
			Backbone t_oBackbone = t_itBackbone.next();
			int t_AP = t_oBackbone.getAnomericPosition();
			// Check checkCarbonDesctriptor　a o 
			if ( this.checkCarbonDesctriptor(t_oBackbone, t_AP) )
				t_bCheckCD = true;	
			// Check 1-8 and e, z, E, Z
			if ( this.has1_8ezEZ_BackboneCarbones(t_oBackbone))
				t_bis1_8ez = true;
			
			isBackboneCount++;
		}
		if (!t_bis1_8ez) t_bHas1_8ez = false;
			
		int isGlycosidicCount = 0;
		ArrayList<Modification> t_GlycosidicList = new ArrayList<Modification>();
		boolean t_bCheckAlternativeLinkage = false;
		for (Modification t_Modification : a_oGraph.getModifications()){
			if(t_Modification.isGlycosidic()){
				t_GlycosidicList.add(t_Modification);
				isGlycosidicCount++;
				//System.out.println("isGlycosidicCount:"+isGlycosidicCount);
			}
		}

		if(isGlycosidicCount == 0) {
			if (t_bHas1_8ez) return SubsumptionLevel.LV4A;
			else return SubsumptionLevel.LV5;
			//return SubsumptionLevel.LVX;
		}
		
		
		for (Modification t_Modification : t_GlycosidicList){
			try {
				t_bCheckAlternativeLinkage = this.checkAlternativeLinkage(t_Modification, isBackboneCount);
			} catch (WURCSFormatException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			//System.out.println("t_bCheckAlternativeLinkage:"+t_bCheckAlternativeLinkage);
			if(!t_bCheckAlternativeLinkage) break;
		}
		
//		Iterator<Backbone> t_itBackbone3 = a_oGraph.getBackboneIterator();
//		while ( t_itBackbone3.hasNext() ) {
//			Backbone t_oBackbone3 = t_itBackbone3.next();
//			// true: backbone count much alternative linkage and all ? Linkage/ false: not Level 3
//			t_bCheckAlternativeLinkage = this.checkAlternativeLinkage(t_oBackbone3.getEdges(), t_count);
//			//System.out.println("t_bNodeCountSameAlternativeLinkage:"+t_bCheckAlternativeLinkage);
//		}

		if (t_bCheckCD == false && t_bHas1_8ez == false && t_bCheckAlternativeLinkage == true)
			//return SubsumptionLevel.LV4;
			return SubsumptionLevel.LV4B;
		
		if (t_bCheckCD == false && t_bCheckAlternativeLinkage == true)
			return SubsumptionLevel.LV3;
		
		
			
		// Check linkages
//		boolean t_bFullyDefined = true;
		boolean t_bHasDefinedAcceptorPosition = false;
		boolean t_bHasDefinedAnomericSymbol = false;
		Iterator<Backbone> t_itBackbone2 = a_oGraph.getBackboneIterator();
		while ( t_itBackbone2.hasNext() ) {
			Backbone t_oBackbone2 = t_itBackbone2.next();
			// Check acceptor position
			if ( this.hasDefinedAcceptorPosition( t_oBackbone2.getEdges() ) )
				t_bHasDefinedAcceptorPosition = true;

			// Ignore open chain
			if ( this.isOpenChain(t_oBackbone2) ) continue;

			// Check anomer
			if ( this.isDefinedAnomericState(t_oBackbone2) )
				t_bHasDefinedAnomericSymbol = true;
		}

//		if ( t_bFullyDefined )
//			return SubsumptionLevel.LV0;

		if ( t_bHasDefinedAcceptorPosition || t_bHasDefinedAnomericSymbol )
			return SubsumptionLevel.LV1;

		return SubsumptionLevel.LV2;
	}

	private boolean checkAlternativeLinkage(Modification a_Modification, int count) throws WURCSFormatException {
		// Check lead in and lead out edge same with backbone count except Type3
		if ( !(a_Modification instanceof ModificationAlternative)){
			//System.out.println("!(a_Modification instanceof ModificationAlternative)");
			return false;
		}		
		String t_MAPCode = a_Modification.getMAPCode();
		MAPGraph t_oMAP = (new MAPGraphImporter()).parseMAP(t_MAPCode);
		MAPGraphAnalyzer t_oMAPAnal = new MAPGraphAnalyzer(t_oMAP);
		int t_LeadIn = ((ModificationAlternative)a_Modification).getLeadInEdges().size();
		int t_LeadOut = ((ModificationAlternative)a_Modification).getLeadOutEdges().size();
		//System.out.println("t_LeadIn:" +t_LeadIn+"& count:" +count);
		if (t_LeadIn != count) return false;
		if (t_oMAPAnal.isTypeIII() && t_MAPCode != "" ){
			//System.out.println("t_LeadOut:" +t_LeadOut+"& count:" +count);
			if (t_LeadOut != 0 && t_LeadOut != count) return false;
		}else{
			//System.out.println("t_LeadOut:" +t_LeadOut+"& count:" +count);
			if (t_LeadOut != count) return false;
		}
		
		for (WURCSEdge t_oEdge : a_Modification.getEdges()){
			// Check All Linkage Position "?"
			LinkedList<LinkagePosition> t_AP = t_oEdge.getLinkages();
			LinkagePosition t_AP_LinkagePosition = t_AP.get(0);
			int t_AP_iBackbonePosition = t_AP_LinkagePosition.getBackbonePosition();
			//System.out.println("BackbonePosition:"+ t_AP_iBackbonePosition);
			if (t_AP_iBackbonePosition != -1) return false;
		}
		return true;
	}

	private boolean hasDefinedAcceptorPosition(LinkedList<WURCSEdge> a_oEdges) {
		// Check acceptor position
		for ( WURCSEdge t_oEdge : a_oEdges ) {
			// Ignore substituent linkage
			if ( !t_oEdge.getModification().isGlycosidic() ) continue;
			// Ignore anomeric edge
			if ( this.isAnomericEdge(t_oEdge) ) continue;

			// true if defined position is found
			for ( LinkagePosition t_oPos : t_oEdge.getLinkages() )
				if ( t_oPos.getBackbonePosition() > 0 ) return true;
		}
		return false;
	}

	private boolean isFullyDefinedAcceptorPosition(LinkedList<WURCSEdge> a_oEdges) {
		// Check acceptor position
		for ( WURCSEdge t_oEdge : a_oEdges ) {
			// Ignore substituent linkage
			if ( !t_oEdge.getModification().isGlycosidic() ) continue;
			// Ignore anomeric edge
			if ( this.isAnomericEdge(t_oEdge) ) continue;

			// false if position is unknown
			if ( t_oEdge.getLinkages().getFirst().getBackbonePosition() == -1 )
				return false;

			// false if alternative position
			if ( t_oEdge.getLinkages().size() > 1 ) return false;

		}
		return true;
	}

	private boolean isAnomericEdge(WURCSEdge a_oEdge) {
		// False for not glycosidic linkeage
		if ( !a_oEdge.getModification().isGlycosidic() ) return false;
		// False for alternative position
		if ( a_oEdge.getLinkages().size() > 1 ) return false;

		int t_iPosition = a_oEdge.getLinkages().getFirst().getBackbonePosition();

		// False for unknown position
		if ( t_iPosition == -1 ) return false;

		// False for anomeric position is not match
		if ( t_iPosition != a_oEdge.getBackbone().getAnomericPosition() ) return false;
		return true;
	}
	
//	private boolean checkAlternativeLinkage(LinkedList<WURCSEdge> a_oEdges, int count) {
//		// Do number of Node same number of Alternative Linkage?	
//		for ( WURCSEdge t_oEdge : a_oEdges ) {
//			Modification t_origModif = t_oEdge.getModification();
//			String t_MAPCode = t_origModif.getMAPCode();
//			MAPGraph t_oMAP = (new MAPGraphImporter()).parseMAP(t_MAPCode);
//			MAPGraphAnalyzer t_oMAPAnal = new MAPGraphAnalyzer(t_oMAP);
//			// For alternative unit
//			if ( !(t_origModif instanceof ModificationAlternative ) ) continue;
//				
//			// Check lead in and lead out edge same with backbone count except Type3
//			int t_LeadIn = ((ModificationAlternative)t_origModif).getLeadInEdges().size();
//			int t_LeadOut = ((ModificationAlternative)t_origModif).getLeadOutEdges().size();			
//			//System.out.println(count+":Leadin"+t_LeadIn);
//			//System.out.println(count+"Leadout"+t_LeadOut);
//			//System.out.println(count+"t_MAPCode"+t_MAPCode);
//			if (!t_oMAPAnal.isTypeIII()){
//				if (t_LeadIn != count) return false;
//				if (t_LeadOut != count) return false;
//			}
//
//			// Check All Linkage Position "?"
//			LinkedList<LinkagePosition> t_AP = t_oEdge.getLinkages();
//			LinkagePosition a_AP_LinkagePosition = t_AP.get(0);
//			int t_AP_iBackbonePosition = a_AP_LinkagePosition.getBackbonePosition();
//			//System.out.println("BackbonePosition:"+ t_AP_iBackbonePosition);
//			if (t_AP_iBackbonePosition != -1) return false;
//		}
//		return true;
//	}
	
/*
	private boolean isFullyDefinedBackbone(Backbone a_oBackbone) {
		// false if unknown backbone "<Q>"
		if ( a_oBackbone instanceof BackboneUnknown_TBD ) return false;

		// false if uncertain anomeric position
		if ( this.hasUncertainAnomericPosition(a_oBackbone) ) return false;

		// false if anomer unknown
		if ( !this.isDefinedAnomericState(a_oBackbone) ) return false;

		return true;
	}
*/
/*
	private boolean isFullyDefinedStereo(Backbone a_oBackbone) {
		for ( BackboneCarbon t_oBC : a_oBackbone.getBackboneCarbons() ) {
			char t_cCD = t_oBC.getDesctriptor().getChar();
			t_cCD
			if ( t_oBC.getDesctriptor().getChar() == 'o' ) return false;
			if ( t_oBC.getDesctriptor().getChar() == 'O' ) return false;
		}
		return false;
	}
*/
	/**
	 * Whether or not Backbone has defined anomer
	 * @param a_oBackbone
	 * @return true if backbone is open chain or anomeric symbol is not 'x'
	 */
	private boolean isDefinedAnomericState(Backbone a_oBackbone) {
		if ( this.hasUncertainAnomericPosition(a_oBackbone) ) return false;
		if ( a_oBackbone.getAnomericSymbol() == 'x' ) return false;
		return true;
	}

	private boolean isOpenChain(Backbone a_oBackbone) {
		if ( a_oBackbone.getAnomericPosition() != 0 ) return false;
		if ( this.hasUncertainAnomericPosition(a_oBackbone) ) return false;
		return true;
	}

	private boolean hasCarbonylGroup(Backbone a_oBackbone) {
		for ( BackboneCarbon t_oBC : a_oBackbone.getBackboneCarbons() ) {
			if ( t_oBC.getDesctriptor().getChar() == 'o' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == 'O' ) return true;
		}
		return false;
	}
	
	private boolean checkCarbonDesctriptor(Backbone a_oBackbone, int a_AP) {
		LinkedList<BackboneCarbon> t_oBC = a_oBackbone.getBackboneCarbons();
		
		if (t_oBC.isEmpty()) return false;

		//TODO m と Aをどうするのか
		int t_APInArray = 0;
		if (a_AP > 1)	t_APInArray = a_AP - 1;
		char t_AnomericBC = t_oBC.get(t_APInArray).getDesctriptor().getChar();
		if ( a_AP == 1 && (t_AnomericBC == 'a' || t_AnomericBC == 'o')) return true;
		if ( a_AP == 2 && (t_AnomericBC == 'a' || t_AnomericBC == 'O')) return true;
		return false;
	}

	private boolean hasUncertainAnomericPosition(Backbone a_oBackbone) {
		for ( BackboneCarbon t_oBC : a_oBackbone.getBackboneCarbons() ) {
			if ( t_oBC.getDesctriptor().getChar() == 'u' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == 'U' ) return true;
		}
		return false;
	}
	
	private boolean has1_8ezEZ_BackboneCarbones(Backbone a_oBackbone) {
		for ( BackboneCarbon t_oBC : a_oBackbone.getBackboneCarbons() ) {
			if ( t_oBC.getDesctriptor().getChar() == '1' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == '2' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == '3' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == '4' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == '5' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == '6' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == '7' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == '8' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == 'e' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == 'z' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == 'E' ) return true;
			if ( t_oBC.getDesctriptor().getChar() == 'Z' ) return true;
			
			//System.out.println(" t_oBC.getDesctriptor().getChar(): "+t_oBC.getDesctriptor().getChar() );
		}
		return false;
	}
}
