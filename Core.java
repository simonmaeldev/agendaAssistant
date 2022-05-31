import org.jpl7.*;

// Your First Program

class Core {
    public static void main(String[] args) {
		Query q1 = new Query("consult", new Term[] { new Atom("main.pl") });
		System.out.println( "consult " + (q1.hasSolution() ? "succeeded" : "failed"));

		Compound generateHours = (Compound) Term
				.textToTerm("generateHours(time(1,17,0), time(1,23,0), [avoid(time(1, 20, 0), time(1, 22, 0))])");
		Query gh = new Query(generateHours);
		System.out.println( "generateHours " + (gh.hasSolution() ? "succeeded" : "failed"));
		
		Compound borne = (Compound) Term.textToTerm("borne(X)");
		Query q3 = new Query(borne);
		System.out.println( "borne " + (q3.hasSolution() ? "succeeded" : "failed"));
		
		java.util.Map<String,Term>[] solutions = q3.allSolutions();
		for ( int i=0 ; i < solutions.length ; i++ ) { 
		    System.out.println( "X = " + solutions[i].get("X")); 
		}

		Compound soluce = (Compound) Term.textToTerm("affecterAll([coder, vaisselle], X)");
		Query q4 = new Query(soluce);
		System.out.println( "solution " + (q4.hasSolution() ? "succeeded" : "failed"));
		
		java.util.Map<String,Term>[] s= q4.allSolutions();
		System.out.println("nb solutions :" + s.length);
		for ( int i=0 ; i < s.length ; i++ ) { 
			System.out.println(i);
			System.out.println( "X = " + s[i].get("X")); 
		}

		
    }
}
