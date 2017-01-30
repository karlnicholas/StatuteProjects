package parser;

import java.util.ArrayList;

import statutes.StatutesTitles;
import statutes.SectionNumber;
import statutes.StatutesBaseClass;
import statutes.StatutesRoot;

public interface ParserInterface {
	public ArrayList<StatutesRoot> getStatutes();
	
    public StatutesBaseClass findReference(String title, SectionNumber sectionNumber);

    public StatutesTitles[] getStatutesTitles();
    public String getShortTitle(String title);

    public boolean loadStatutes();	// no exceptions allowed

}