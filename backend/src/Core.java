import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Array;
import org.jpl7.*;

class Core {
    @SuppressWarnings("unchecked")
    private static final HashMap<String, Term>[] NO_SOLUTION = (HashMap<String, Term>[]) Array.newInstance(HashMap.class, 0);

    public static Query plQuery(String q) {
		Compound c = (Compound) Term.textToTerm(q);
		Query query = new Query(c);
		return query;
    }

    public static boolean execPlQuery(String req) {
    	System.out.println("execPlQuery : " + req);
    	Query q = plQuery(req);
    	boolean s = q.hasSolution();
    	System.out.println(s);
    	return s;
    }
    
    public static void loadFile() {
		Query q1 = new Query("consult", new Term[] { new Atom("main.pl") });
		System.out.println("consult " + (q1.hasSolution() ? "succeeded" : "failed"));
    }
    
    public static void setUserPreferences() {
		execPlQuery("assertz(timeUnit(5))");
    }

    public static void setUserConstraint() {
    	execPlQuery("assertz(tpsNecessaire(coder, 120))");
    	execPlQuery("assertz(tpsNecessaire(vaisselle, 60))");
    }
    
    public static void generateTime() {
    	execPlQuery("generateHours(time(1,17,0), time(1,23,0), [avoid(time(1, 20, 0), time(1, 22, 0))])");
		
    }

    public static Map<String,Term>[] getAllSolutions(String req) {
    	System.out.println(req);
		Query q = plQuery(req);
		if (q.hasSolution()) {
			System.out.println("has solution");
			Map<String,Term>[] s= q.allSolutions();
			return s;
		} else {
			System.out.println("do not have solution");
			return NO_SOLUTION;
		}
    }

    public static void main(String[] args) {
		loadFile();
		setUserPreferences();
		setUserConstraint();
		generateTime();
		
		Map<String, Term>[] s = getAllSolutions("affecterAll([coder, vaisselle], X)");
		for (int i=0; i < s.length; i++) { 
			System.out.println(i);
			System.out.println( "X = " + s[i].get("X")); 
		}

		
    }
}
