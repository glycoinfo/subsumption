package org.glycoinfo.subsumption.util.graph.analysis;

public enum SubsumptionLevel {
	/** Glycan has all linkage position and anomeric symbol */
	LV0( 1, "FullyDefinedGlycan"),
	/** Glycan has at least one acceptor position. */
	LV1( 1, "LinkageDefinendGlycan"),
	/** Glycan has no acceptor position and anomeric symbol of donor (anomeric position is defined),<br>
	 *  and has at least one linkage relation. */
	LV2( 2, "GlycanTopology"),
	/** Glycan has no linkage relation between all monosaccharides,<br>
	 *  and has at least one monosaccharide kind (stereochemistry). */
	LV3( 3, "MonosaccharideCompositionWithLinkage"),
	/**
	 * */
	//LV4( 4, "BaseComposition"),
	LV4A( 4, "MonosaccharideComposition"),
	/**
	 * */
	LV4B( 4, "BaseCompositionWithLinkage"),
	/** Glycan has no linkage relation between all monosaccharides,<br>
	 *  and has no monosaccharide kind (stereochemistry). */
	LV5( 5, "BaseComposition"),
	/** Undefined level. TODO: Now monosaccharide is categorized this level. */
	LVX(-1, "Undefined");

	private int m_iLevel;
	private String m_strClassName;

	private SubsumptionLevel(int a_iLV, String a_strName ) {
		this.m_iLevel = a_iLV;
		this.m_strClassName = a_strName;
	}

	public int getLevel() {
		return this.m_iLevel;
	}

	public String getClassName() {
		return this.m_strClassName;
	}
}
