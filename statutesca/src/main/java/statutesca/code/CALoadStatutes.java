package statutesca.code;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

import parser.ParserInterface;
import statutes.StatutesTitles;
import statutes.SectionNumber;
import statutes.StatutesBaseClass;
import statutes.StatutesRoot;

/**
 * Created with IntelliJ IDEA. User: karl Date: 6/7/12 Time: 5:37 AM To change
 * this template use File | Settings | File Templates.
 */
public class CALoadStatutes implements ParserInterface {
	private final static Logger logger = Logger.getLogger( CALoadStatutes.class.getName() );
    private static final String DEBUGFILE = null; // "bpc";	// "fam";
    
	private ArrayList<StatutesRoot> statutes;
	private HashMap<String, StatutesTitles> mapStatutesToTitles;
	private StatutesParser parser;
//	private Unmarshaller unmarshaller;

    public static final String[] sectionTitles = {
        "title",
        "part",
        "division",
        "chapter",
        "article"
    };

/*
    public static String getShortTitle(String title) {
        if ( title == null ) return title;
        for (int i=0; i < patterns.length; ++i ) {
            if ( title.toLowerCase().contains(patterns[i]) )
                return patternsAbvr[i];
        }
        return title;
    }
*/    


	public CALoadStatutes() {
		mapStatutesToTitles = new HashMap<String, StatutesTitles> ();

		StatutesTitles statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("bpc");
		statutesTitles.setFullTitle("Bus. & Professions");
		statutesTitles.setFacetHead("business and professions code");
		statutesTitles.setAbvrTitles( new String[]{"bus. & prof. code"} ); 		
		mapStatutesToTitles.put( "CALIFORNIA BUSINESS AND PROFESSIONS CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("ccp");
		statutesTitles.setFullTitle("Civ. Procedure");
		statutesTitles.setFacetHead("code of civil procedure");
		statutesTitles.setAbvrTitles( new String[]{"code civ. proc.", "code of civ. pro."} );
		mapStatutesToTitles.put( "CALIFORNIA CODE OF CIVIL PROCEDURE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("civ");
		statutesTitles.setFullTitle("Civil");
		statutesTitles.setFacetHead("civil code");
		statutesTitles.setAbvrTitles( new String[]{"civ. code"} );
		mapStatutesToTitles.put( "CALIFORNIA CIVIL CODE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("com");
		statutesTitles.setFullTitle("Commercial");
		statutesTitles.setFacetHead("commercial code");
		statutesTitles.setAbvrTitles( new String[]{"com. code"} );
		mapStatutesToTitles.put( "CALIFORNIA COMMERCIAL CODE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("corp");
		statutesTitles.setFullTitle("Corporations");
		statutesTitles.setFacetHead("corporations code");
		statutesTitles.setAbvrTitles( new String[]{"corp. code"} );
		mapStatutesToTitles.put( "CALIFORNIA CORPORATIONS CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("edc");
		statutesTitles.setFullTitle("Education");
		statutesTitles.setFacetHead("education code");
		statutesTitles.setAbvrTitles( new String[]{"ed. code"} );
		mapStatutesToTitles.put( "CALIFORNIA EDUCATION CODE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("elec");
		statutesTitles.setFullTitle("Elections");
		statutesTitles.setFacetHead("elections code");
		statutesTitles.setAbvrTitles( new String[]{"elec. code"} );
		mapStatutesToTitles.put( "CALIFORNIA ELECTIONS CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("evid");
		statutesTitles.setFullTitle("Evidence");
		statutesTitles.setFacetHead("evidence code");
		statutesTitles.setAbvrTitles( new String[]{"evid. code"} );
		mapStatutesToTitles.put( "CALIFORNIA EVIDENCE CODE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("fac");
		statutesTitles.setFullTitle("Agriculture");
		statutesTitles.setFacetHead("food and agricultural code");
		statutesTitles.setAbvrTitles( new String[]{"food & agr. code"} );
		mapStatutesToTitles.put( "CALIFORNIA FOOD AND AGRICULTURAL CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("fam");
		statutesTitles.setFullTitle("Family");
		statutesTitles.setFacetHead("family code");
		statutesTitles.setAbvrTitles( new String[]{"fam. code"} );
		mapStatutesToTitles.put( "CALIFORNIA FAMILY CODE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("fgc");
		statutesTitles.setFullTitle("Fish & Game");
		statutesTitles.setFacetHead("fish and game code");
		statutesTitles.setAbvrTitles( new String[]{"fish & game code"} );
		mapStatutesToTitles.put( "CALIFORNIA FISH AND GAME CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("fin");
		statutesTitles.setFullTitle("Financial");
		statutesTitles.setFacetHead("financial code");
		statutesTitles.setAbvrTitles( new String[]{"fin. code"} );
		mapStatutesToTitles.put( "CALIFORNIA FINANCIAL CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("gov");
		statutesTitles.setFullTitle("Government");
		statutesTitles.setFacetHead("government code");
		statutesTitles.setAbvrTitles( new String[]{"gov. code"} );
		mapStatutesToTitles.put( "CALIFORNIA GOVERNMENT CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("hnc");
		statutesTitles.setFullTitle("Harbors & Nav.");
		statutesTitles.setFacetHead("harbors and navigation code");
		statutesTitles.setAbvrTitles( new String[]{"har. & nav. code"} );
		mapStatutesToTitles.put( "CALIFORNIA HARBORS AND NAVIGATION CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("hsc");
		statutesTitles.setFullTitle("Health");
		statutesTitles.setFacetHead("health and safety code");
		statutesTitles.setAbvrTitles( new String[]{"health & saf. code"} );
		mapStatutesToTitles.put( "CALIFORNIA HEALTH AND SAFETY CODE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("ins");
		statutesTitles.setFullTitle("Insurance");
		statutesTitles.setFacetHead("insurance code");
		statutesTitles.setAbvrTitles( new String[]{"ins. code"} );
		mapStatutesToTitles.put( "CALIFORNIA INSURANCE CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("lab");
		statutesTitles.setFullTitle("Labor");
		statutesTitles.setFacetHead("labor code");
		statutesTitles.setAbvrTitles( new String[]{"lab. code"} );
		mapStatutesToTitles.put( "CALIFORNIA LABOR CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("mvc");
		statutesTitles.setFullTitle("Military & Vets.");
		statutesTitles.setFacetHead("military and veterans code");
		statutesTitles.setAbvrTitles( new String[]{"mil. and vet. code"} );
		mapStatutesToTitles.put( "CALIFORNIA MILITARY AND VETERANS CODE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("pcc");
		statutesTitles.setFullTitle("Public Contact");
		statutesTitles.setFacetHead("public contract code");
		statutesTitles.setAbvrTitles( new String[]{"pub. con. code"} );
		mapStatutesToTitles.put( "CALIFORNIA PUBLIC CONTRACT CODE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("pen");
		statutesTitles.setFullTitle("Penal");
		statutesTitles.setFacetHead("penal code");
		statutesTitles.setAbvrTitles( new String[]{"pen. code"} );
		mapStatutesToTitles.put( "CALIFORNIA PENAL CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("prc");
		statutesTitles.setFullTitle("Public Resources");
		statutesTitles.setFacetHead("public resources code");
		statutesTitles.setAbvrTitles( new String[]{"pub. res. code"} );
		mapStatutesToTitles.put( "CALIFORNIA PUBLIC RESOURCES CODE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("prob");
		statutesTitles.setFullTitle("Probate");
		statutesTitles.setFacetHead("probate code");
		statutesTitles.setAbvrTitles( new String[]{"prob. code"} );
		mapStatutesToTitles.put( "CALIFORNIA PROBATE CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("puc");
		statutesTitles.setFullTitle("Public Utilities");
		statutesTitles.setFacetHead("public utilities code");
		statutesTitles.setAbvrTitles( new String[]{"pub. util. code"} );
		mapStatutesToTitles.put( "CALIFORNIA PUBLIC UTILITIES CODE".toLowerCase(), statutesTitles );
		
		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("rtc");
		statutesTitles.setFullTitle("Revenue & Tax.");
		statutesTitles.setFacetHead("revenue and taxation code");
		statutesTitles.setAbvrTitles( new String[]{"rev. & tax. code"} );
		mapStatutesToTitles.put( "CALIFORNIA REVENUE AND TAXATION CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("shc");
		statutesTitles.setFullTitle("Highways");
		statutesTitles.setFacetHead("streets and highways code");
		statutesTitles.setAbvrTitles( new String[]{"st. & high. code"} );
		mapStatutesToTitles.put( "CALIFORNIA STREETS AND HIGHWAYS CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("uic");
		statutesTitles.setFullTitle("Unemployment Ins.");
		statutesTitles.setFacetHead("unemployment insurance code");
		statutesTitles.setAbvrTitles( new String[]{"unemp. ins. code"} );
		mapStatutesToTitles.put( "CALIFORNIA UNEMPLOYMENT INSURANCE CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("veh");
		statutesTitles.setFullTitle("Vehicle");
		statutesTitles.setFacetHead("vehicle code");
		statutesTitles.setAbvrTitles( new String[]{"veh. code"} );
		mapStatutesToTitles.put( "CALIFORNIA VEHICLE CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("wat");
		statutesTitles.setFullTitle("Water");
		statutesTitles.setFacetHead("water code");
		statutesTitles.setAbvrTitles( new String[]{"wat. code"} );
		mapStatutesToTitles.put( "CALIFORNIA WATER CODE".toLowerCase(), statutesTitles );

		statutesTitles = new StatutesTitles();
		statutesTitles.setShortTitle("wic");
		statutesTitles.setFullTitle("Welfare & Inst.");
		statutesTitles.setFacetHead("welfare and institutions code");
		statutesTitles.setAbvrTitles( new String[]{"welf. & inst. code"} );
		mapStatutesToTitles.put( "CALIFORNIA WELFARE AND INSTITUTIONS CODE".toLowerCase(), statutesTitles );
	}

	/*
	 * There is a problem here. When using this method, the section numbers in  
	 * are not in consistent order. e.g.  Penal StatutesRoot 273a is before 273.1
	 * but 270a is after 270.1 -- This makes is difficult, or impossible, to determine
	 * what file a specific section number belongs to. I'm coding it so that
	 * 270a is said to come before 270.1. This is needed because the files
	 * 270-273.5 includes 273a. The file 273.8-273.88 does not include 273a.
	 * I don't know if there are other situations where this is reversed ... 
	 * I should write a utility to check everything. See ConvertToHybridXML in the
	 * opca project.
	 * ...
	 * ok, there's more. The second numerical element of the section number is not ordered numberically, but lexically.
	 * so .. 422.865 comes before 422.88
	 */
	public void loadFromRawPath(Path path) throws IOException {
		// ArrayList<File> files = new ArrayList<File>();

		List<Path> files = new ArrayList<Path>();
	    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
	        for (Path entry : stream) {
	            if (Files.isDirectory(entry)) 
	            	continue;
				if (entry.getFileName().toString().contains("constitution")) 
					continue;
				if ( DEBUGFILE != null ) { 
					if (!entry.getFileName().toString().contains(DEBUGFILE)) 
						continue;
				}
            	files.add(entry);
	        }
	    }
	    Charset encoding = StandardCharsets.ISO_8859_1;
		for ( Path file: files ) {
			logger.info("Processing " + file);
			loadRawFile( encoding, file );
		}

		Collections.sort( statutes );
	}

	@Override
	public boolean loadStatutes() {

		parser = new StatutesParser();
		statutes = new ArrayList<StatutesRoot>();
//		JAXBContext ctx = JAXBContext.newInstance(StatutesRoot.class);
//		unmarshaller = ctx.createUnmarshaller();

		final ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
		final ClassLoader classLoader2 = this.getClass().getClassLoader();
		ClassLoader classLoader = null;
		final String resourcePath = "CaliforniaStatutes/";
		if ( classLoader1 == null ) logger.warning("classLoader1 is null");
		else classLoader = classLoader1;
		if ( classLoader2 == null ) logger.warning("classLoader2 is null");
		else classLoader = classLoader2;
		InputStream listStream = classLoader.getResourceAsStream(resourcePath + "files");
	    final BufferedReader br = new BufferedReader(new InputStreamReader(listStream, StandardCharsets.US_ASCII));
	    String fileName;
	    List<URL> resources = new ArrayList<URL>();
	    try {
			while ( (fileName = br.readLine()) != null ) {
				if ( !fileName.endsWith(".ser")) continue;
				resources.add( classLoader.getResource(resourcePath + fileName) );
			}
		} catch (IOException e) {
			logger.severe(e.getMessage());
			return false;
		}
	    
		for (URL url: resources) {
			logger.info("Processing " + url.toString() );
//			StatutesRoot c = (StatutesRoot) unmarshaller.unmarshal(url.openStream());
			ObjectInputStream ois;
			StatutesRoot c;
			try {
				ois = new ObjectInputStream( url.openStream() );
				c = (StatutesRoot)ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				logger.severe(e.getMessage());
				return false;
			}
			c.rebuildParentReferences(null);
			statutes.add( c );
		}
		Collections.sort( statutes );
		return true;
	}		

	
	public void loadRawFile(Charset encoding, Path file) throws FileNotFoundException {
		statutes.add( parser.parse(this, encoding, file) );
	}

	public StatutesBaseClass findReference(String codeTitle, SectionNumber sectionNumber) {
		return findStatuteRoot(codeTitle).findReference( sectionNumber );
	}

	public StatutesRoot findStatuteRoot(String codeTitle) {
		
		String tempTitle = codeTitle.toLowerCase();
		StatutesTitles statutesTitles = null;
		for ( StatutesTitles t: mapStatutesToTitles.values() ) {
			if ( t.getFullTitle().equals( codeTitle ) ) {
				statutesTitles = t;
				break;
			}
		}
		if ( statutesTitles == null ) {
			throw new RuntimeException("StatutesRoot not found:" + codeTitle);
		}
		tempTitle = statutesTitles.getFacetHead();
		Iterator<StatutesRoot> ci = statutes.iterator();
		while (ci.hasNext()) {
			StatutesRoot code = ci.next();
			if (code.getTitle(false).toLowerCase().contains(tempTitle)) {
				return code;
			}
			if ( tempTitle.contains(code.getTitle(false).toLowerCase())) {
				return code;
			}
		}
/*		
		
		String tempTitle = codeTitle.toLowerCase();
		Iterator<StatutesRoot> ci = statutes.iterator();
		while (ci.hasNext()) {
			StatutesRoot code = ci.next();
			if (code.getTitle(false).toLowerCase().contains(tempTitle)) {
				return code;
			}
			if ( tempTitle.contains(code.getTitle(false).toLowerCase())) {
				return code;
			}
		}
*/		
		throw new RuntimeException("StatutesRoot not found:" + codeTitle);
	}

	public String getShortTitle(String title) {
		return mapStatutesToTitles.get(title.toLowerCase()).getShortTitle(); 
	}

	@Override
	public ArrayList<StatutesRoot> getStatutes() {
		return statutes;
	}
	
	public static void main(String[] args) throws Exception {
//		logger.setLevel(Level.FINE);
		CALoadStatutes codes = new CALoadStatutes();
//		codes.loadFromRawPath(Paths.get("c:/users/karl/code"));
		codes.loadStatutes();
		// CodeParser parser = new CodeParser();
//		Path path = FileSystems.getDefault().getPath("codes/ccp_table_of_contents");
//		Path path = ;		// <--|
//		StatutesRoot c = parser.parse(path);		// <--|
		StatutesBaseClass reference = codes.findReference("California Penal Code", new SectionNumber(0, "625") );
		System.out.println(reference );
//		System.out.println( reference.getFullFacet());
	}

	@Override
	public StatutesTitles[] getStatutesTitles() {
		return mapStatutesToTitles.values().toArray(new StatutesTitles[0]);
	}


}
