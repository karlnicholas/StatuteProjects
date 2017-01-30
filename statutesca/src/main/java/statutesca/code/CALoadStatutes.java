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
		mapStatutesToTitles.put( "CALIFORNIA BUSINESS AND PROFESSIONS CODE".toLowerCase(), 
				new StatutesTitles( "bpc", "Bus. & Professions", "business and professions code", new String[]{"bus. & prof. code"} )
		);
		mapStatutesToTitles.put( "CALIFORNIA CODE OF CIVIL PROCEDURE".toLowerCase(), 
				new StatutesTitles( "ccp", "Civ. Procedure", "code of civil procedure", new String[]{"code civ. proc.", "code of civ. pro."}) 
		);
		mapStatutesToTitles.put( "CALIFORNIA CIVIL CODE".toLowerCase(), 
				new StatutesTitles("civ", "Civil", "civil code", new String[]{"civ. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA COMMERCIAL CODE".toLowerCase(), 
				new StatutesTitles( "com", "Commercial", "commercial code",new String[]{"com. code"}) 
		);
		mapStatutesToTitles.put( "CALIFORNIA CORPORATIONS CODE".toLowerCase(),
				new StatutesTitles( "corp", "Corporations",  "corporations code", new String[]{"corp. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA EDUCATION CODE".toLowerCase(),
				new StatutesTitles( "edc", "Education", "education code", new String[]{"ed. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA ELECTIONS CODE".toLowerCase(), 
				new StatutesTitles( "elec", "Elections", "elections code", new String[]{"elec. code"}) 
		);
		mapStatutesToTitles.put( "CALIFORNIA EVIDENCE CODE".toLowerCase(), 
				new StatutesTitles( "evid", "Evidence",  "evidence code", new String[]{"evid. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA FOOD AND AGRICULTURAL CODE".toLowerCase(),
				new StatutesTitles( "fac", "Agriculture","food and agricultural code", new String[]{"food & agr. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA FAMILY CODE".toLowerCase(), 
				new StatutesTitles( "fam", "Family", "family code", new String[]{"fam. code"}) 
		);
		mapStatutesToTitles.put( "CALIFORNIA FISH AND GAME CODE".toLowerCase(), 
				new StatutesTitles( "fgc", "Fish & Game",  "fish and game code", new String[]{"fish & game code"}) 
		);
		mapStatutesToTitles.put( "CALIFORNIA FINANCIAL CODE".toLowerCase(), 
				new StatutesTitles( "fin", "Financial",  "financial code", new String[]{"fin. code"}) 
		);
		mapStatutesToTitles.put( "CALIFORNIA GOVERNMENT CODE".toLowerCase(), 
				new StatutesTitles( "gov", "Government", "government code", new String[]{"gov. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA HARBORS AND NAVIGATION CODE".toLowerCase(), 
				new StatutesTitles( "hnc", "Harbors & Nav.",  "harbors and navigation code", new String[]{"har. & nav. code"}) 
		);
		mapStatutesToTitles.put( "CALIFORNIA HEALTH AND SAFETY CODE".toLowerCase(), 
				new StatutesTitles( "hsc", "Health", "health and safety code", new String[]{"health & saf. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA INSURANCE CODE".toLowerCase(),
				new StatutesTitles( "ins", "Insurance", "insurance code", new String[]{"ins. code"}) 
		);
		mapStatutesToTitles.put( "CALIFORNIA LABOR CODE".toLowerCase(), 
				new StatutesTitles( "lab", "Labor", "labor code", new String[]{"lab. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA MILITARY AND VETERANS CODE".toLowerCase(), 
				new StatutesTitles( "mvc", "Military & Vets.","military and veterans code",new String[]{"mil. and vet. code"}) 
		);
		mapStatutesToTitles.put( "CALIFORNIA PUBLIC CONTRACT CODE".toLowerCase(), 
				new StatutesTitles( "pcc", "Public Contact", "public contract code", new String[]{"pub. con. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA PENAL CODE".toLowerCase(), 
				new StatutesTitles( "pen", "Penal", "penal code", new String[]{"pen. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA PUBLIC RESOURCES CODE".toLowerCase(),
				new StatutesTitles( "prc", "Public Resources", "public resources code", new String[]{"pub. res. code"} )
				);
		mapStatutesToTitles.put( "CALIFORNIA PROBATE CODE".toLowerCase(), 
				new StatutesTitles( "prob", "Probate", "probate code", new String[]{"prob. code"})
		);
		mapStatutesToTitles.put( "CALIFORNIA PUBLIC UTILITIES CODE".toLowerCase(), 
				new StatutesTitles( "puc", "Public Utilities", "public utilities code", new String[]{"pub. util. code"} )
		);
		mapStatutesToTitles.put( "CALIFORNIA REVENUE AND TAXATION CODE".toLowerCase(), 
				new StatutesTitles( "rtc", "Revenue & Tax.",  "revenue and taxation code", new String[]{"rev. & tax. code"} )
		);
		mapStatutesToTitles.put( "CALIFORNIA STREETS AND HIGHWAYS CODE".toLowerCase(),
				new StatutesTitles( "shc", "Highways",  "streets and highways code", new String[]{"st. & high. code"} )
		);
		mapStatutesToTitles.put( "CALIFORNIA UNEMPLOYMENT INSURANCE CODE".toLowerCase(), 
				new StatutesTitles( "uic", "Unemployment Ins.", "unemployment insurance code", new String[]{"unemp. ins. code"} )
		);
		mapStatutesToTitles.put( "CALIFORNIA VEHICLE CODE".toLowerCase(),
				new StatutesTitles( "veh", "Vehicle", "vehicle code", new String[]{"veh. code"} )
		);
		mapStatutesToTitles.put( "CALIFORNIA WATER CODE".toLowerCase(),
				new StatutesTitles( "wat", "Water", "water code", new String[]{"wat. code"} )
		);
		mapStatutesToTitles.put( "CALIFORNIA WELFARE AND INSTITUTIONS CODE".toLowerCase(),
				new StatutesTitles( "wic", "Welfare & Inst.", "welfare and institutions code", new String[]{"welf. & inst. code"} )
		);
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
		return findReference(codeTitle).findReference( sectionNumber );
	}

	public StatutesRoot findReference(String codeTitle) {
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
