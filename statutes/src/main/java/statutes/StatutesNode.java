package statutes;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {
	"depth", "part", "partNumber", "statuteRange", 
	"title", "parent" 
})
@SuppressWarnings("serial")
public class StatutesNode extends StatutesBaseClass {
	//	private static final Logger logger = Logger.getLogger(Subcode.class.getName());
	private StatutesBaseClass parent;
	private String part;
    private String partNumber;
    private String title;
    private StatuteRange statuteRange;
    // keep track of how deep we are ..
    // Always > 0 for Subcode
    private int depth;
	// and pointers to under Chapters, Parts, Articles, etc
    private ArrayList<StatutesBaseClass> references;
        
    public StatutesNode() {
    	references = new ArrayList<StatutesBaseClass>();
    	statuteRange = new StatuteRange();
    }
    public StatutesNode(StatutesBaseClass parent, String part, String partNumber, String title, int depth) {
    	references = new ArrayList<StatutesBaseClass>();
    	statuteRange = new StatuteRange();
    	
    	this.parent = parent;
    	if ( part != null ) {
    		this.part = Character.toUpperCase(part.charAt(0)) + part.substring(1).toLowerCase();
    	} else {
    		part = null;
    	}
    	this.partNumber = partNumber;
    	this.title = title;
    	this.depth = depth;
    	assert( depth>0 );
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

    public void addReference( StatutesBaseClass reference ) {
    	references.add(reference);
    	mergeStatuteRange(reference.getStatuteRange());
    }
	@XmlTransient
    public ArrayList<StatutesBaseClass> getReferences() {
    	return references;
    }
    public void setReferences(ArrayList<StatutesBaseClass> references) {
    	this.references = references;
    }
	public void rebuildParentReferences(StatutesBaseClass parent) {
		this.parent = parent;
		for ( StatutesBaseClass reference: references ) {
			reference.rebuildParentReferences(this);
		}
	}
/*	
	@XmlAttribute
	public String getCodeBegin() {
	    if ( statuteRange != null ) {
	    	if ( statuteRange.getsNumber() != null ) {
	    		return statuteRange.getsNumber().getSectionNumber();
	    	}
    	}
		return null;
	}
	public void setCodeBegin(String codeBegin) {}
	@XmlAttribute
	public String getCodeEnd() {
	    if ( statuteRange != null ) {
	    	if ( statuteRange.geteNumber() != null ) {
	    		return statuteRange.geteNumber().getSectionNumber();
	    	}
	    }
		return null;
	}
	public void setCodeEnd(String codeEnd) {}
	@XmlAttribute(required=true)
	public int getPosBegin() {
	    if ( statuteRange != null ) {
	    	if ( statuteRange.getsNumber() != null ) {
				return statuteRange.getsNumber().getPosition();
	    	}
	    }
		return -1;
	}
	public void setPosBegin(int posBegin) {}
	@XmlAttribute(required=true)
	public int getPosEnd() {
	    if ( statuteRange != null ) {
	    	if ( statuteRange.geteNumber() != null ) {
	    		return statuteRange.geteNumber().getPosition();
	    	}
	    }
		return -1;
	}
	public void setPosEnd(int codeEnd) {}
*/	
	@XmlElement
    public StatutesBaseClass getParent() {
    	return parent;
    }
    public void setParent( StatutesBaseClass parent ) {
    	this.parent = parent;
    	parent.addReference(this);
    }

	@Override
	@XmlAttribute(required=true)
    public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	@Override
	@XmlAttribute(required=true)
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	@Override
	public String getTitle(boolean showPart) {
        String ret = new String();
        if ( showPart ) {
        	if ( part != null && partNumber != null ) ret = (part+" "+partNumber+". ");
        }
        if ( title != null ) ret = ret + title;
		return ret.toString();
	}

    @Override
    public String getFullTitle(String separator) {
        String ret = new String();
        if ( part != null && partNumber != null ) ret = (part+" "+partNumber+". ");
        if ( title != null ) ret = ret + title;
        return parent.getFullTitle(separator)+separator+ret;
    }
	@XmlElement
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String toString() {
        String indent = new String();
        String ret = indent + part + " " + partNumber + ":" + title;
        return ret;
    }

	@XmlAttribute(required=true)
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	@Override
	public StatutesLeaf getStatutesLeaf() {
		return null;
	}
	// can only assume that they are read in order
	// and therefore 
	public void mergeStatuteRange(StatuteRange statuteRange) {
    	this.statuteRange.mergeRange(statuteRange);
    	parent.mergeStatuteRange(this.statuteRange);		
	}
	@XmlElement
	public StatuteRange getStatuteRange() {
		return statuteRange;
	}
	public void setStatuteRange(StatuteRange statuteRange) {
		this.statuteRange = statuteRange;
	}
	@Override
	public void getParents(ArrayList<StatutesBaseClass> returnPath) {
		returnPath.add(parent);
		parent.getParents(returnPath);
	}

	@Override
	public StatutesRoot getStatutesRoot() {
		return parent.getStatutesRoot();
	}
	@Override
	public StatutesNode getStatutesNode() {
		return this;
	}
	@XmlTransient
	@Override
	public String getShortTitle() {
        StringBuilder ret = new StringBuilder();
        if ( part != null ) {
        	ret.append(part);
        	ret.append(" ");
        }
        if ( partNumber != null ) {
        	ret.append(partNumber);
        }
        return ret.toString();
	}
	@Override
	public void setShortTitle(String shortTitle) {
		// do nothing
	}

}
