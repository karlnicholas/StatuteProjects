package statutes;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Root element of a Statute Hierarchy.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {
		"depth", "part", "partNumber", "statuteRange", 
		"title", "shortTitle"
	})
@XmlSeeAlso({StatutesNode.class, StatutesLeaf.class})
@SuppressWarnings("serial")
public class StatutesRoot extends StatutesBaseClass implements Comparable<StatutesRoot> {
	//	private static final Logger logger = Logger.getLogger(Code.class.getName());
    private String title;
    private String shortTitle;
    // Always 0 for code, increments for each level of children
    private int depth;
    private StatuteRange codeRange;
    private ArrayList<StatutesBaseClass> references;
    // wtf is this?
//    private ArrayList<CodeReference> comparableList;

    public StatutesRoot() {
    	references = new ArrayList<StatutesBaseClass>();
    }
    public StatutesRoot(String title, String shortTitle) {
    	references = new ArrayList<StatutesBaseClass>();
    	codeRange = new StatuteRange();
        this.title = title;
        this.shortTitle = shortTitle;
    	this.depth = 0;
    }

	public StatutesBaseClass findReference(SectionNumber sectionNumber) {
		Iterator<StatutesBaseClass> rit = references.iterator();
		while ( rit.hasNext() ) {
			StatutesBaseClass reference = rit.next().findReference(sectionNumber);
			if ( reference != null ) return reference;
		}
		return null;
	}

	@Override
	public boolean iterateLeafs(IteratorHandler handler) throws Exception {
		Iterator<StatutesBaseClass> rit = references.iterator();
		while ( rit.hasNext() ) {
			if ( !rit.next().iterateLeafs(handler) ) return false;
		}
		return true;
	}
	/**
	 * Internal function for settings the hierarchy's parent
	 * references after being loaded from XML.
	 * @param parent set null at this level.
	 */
	public void rebuildParentReferences(StatutesBaseClass parent) {
		for ( StatutesBaseClass reference: references ) {
			reference.rebuildParentReferences(this);
		}
	}

	@XmlTransient
    public StatutesBaseClass getParent() {
    	return null;
    }

    public void setParent( StatutesBaseClass parent ) {
    }

	public void addReference(StatutesBaseClass reference) {
		references.add(reference);
    	mergeStatuteRange(reference.getStatuteRange());
	}
	@XmlTransient
	@Override
	public ArrayList<StatutesBaseClass> getReferences() {
		return references;
	}
	public void setReferences(ArrayList<StatutesBaseClass> references) {
		this.references = references;
	}
	@Override
    public String toString() {
    	String cString = title + ": " + references.size() + " references";
        return cString;
    }
	@XmlElement
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
    public String getTitle(boolean showPart) {
        return title;
    }
    public String getFullTitle(String separator) {
        return title;
    }
	@XmlElement
    public String getShortTitle() {
        return shortTitle;
    }
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	@XmlAttribute(required=true)
	public int getDepth() {
		return depth;	// always 0
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public StatutesLeaf getStatutesLeaf() {
		return null;
	}
	@Override
	public StatutesNode getStatutesNode() {
		return null;
	}
	@XmlElement
	public StatuteRange getStatuteRange() {
		return codeRange;
	}
	public void setStatuteRange(StatuteRange codeRange) {
		this.codeRange = codeRange;
	}
	public void mergeStatuteRange(StatuteRange codeRange) {
		this.codeRange.mergeRange(codeRange);
	}
	public void getParents(ArrayList<StatutesBaseClass> returnPath) {}

	@Override
	public StatutesRoot getStatutesRoot() {
		return this;
	}
	@Override
	public int compareTo(StatutesRoot o) {
		return this.title.compareTo(o.getTitle(false));
	}
	@Override
	public String getPart() {
		return null;
	}

	@Override
	public void setPart(String part) {
		// do nothing
		
	}
	@Override
	public String getPartNumber() {
		return null;
	}
	@Override
	public void setPartNumber(String partNumber) {
		// do nothing
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shortTitle == null) ? 0 : shortTitle.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatutesRoot other = (StatutesRoot) obj;
		if (shortTitle == null) {
			if (other.shortTitle != null)
				return false;
		} else if (!shortTitle.equals(other.shortTitle))
			return false;
		return true;
	}

}