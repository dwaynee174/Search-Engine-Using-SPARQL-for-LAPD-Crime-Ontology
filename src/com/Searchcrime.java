package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;


import sun.security.tools.keytool.Main;
/**
 * Servlet implementation class Searchcrime
 */
@WebServlet("/Searchcrime")
public class Searchcrime extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Searchcrime() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String a = request.getParameter("serach_bar");
		System.out.println(a);
		//	System.out.println("Zuuutopiaa");

		String SplitBy = " ";
		String[] lapd = a.split(SplitBy);

		//	System.out.println(lapd[1]);
		//	System.out.println(lapd[2]);

		FileSystemView view = FileSystemView.getFileSystemView();
		String Path_to_Desktop = view.getHomeDirectory().toString();
		String rdfFile = Path_to_Desktop+"/LAPD Crime and Collision Raw Data for 2015.rdf";

		System.out.println(rdfFile);

		FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
		Model model = FileManager.get().loadModel(rdfFile);

		// Create a new query

		//	System.out.println("Yes");
		
		

		if(lapd[1].equals("Case"))
		{
			
			String crime = lapd[6];
			String month = lapd[8];
			String juvenile_arrest = lapd[15];
			String adult_arrest = lapd[17];


			System.out.println(crime);
			System.out.println(lapd[1]);
			month = "2015-12-01T00:00:00";


			if(juvenile_arrest.equals("juvenile"))
			{
				juvenile_arrest="Juv Arrest";
			}

			if(adult_arrest.equals("adult"))
			{
				adult_arrest="Adult Arrest";
			}
			
			String queryString = 

					"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
							"PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
							"PREFIX	ds:   <http://data.lacity.org/resource/ttiz-7an8/>"+ 
							"PREFIX	xsd:   <http://www.w3.org/2001/XMLSchema#>"+
							"SELECT DISTINCT ?dr_no ?location  " +
							"WHERE { {" +
							"      ?x         ds:date_occ     \""+month+"\"." +
							"      ?element   ds:crm_cd_desc  \""+crime+"\" . " +
							"      ?element   ds:status_desc  \""+juvenile_arrest+"\" . " +
							"      ?element   ds:dr_no  ?dr_no " +"."+
							"      ?element   ds:location  ?location " +	
							"}"+							
							"UNION{"+
							"      ?x         ds:date_occ     \""+month+"\"." +
							"      ?element   ds:crm_cd_desc  \""+crime+"\" . " +
							"      ?element   ds:status_desc  \""+adult_arrest+"\" . " +
							"      ?element   ds:dr_no  ?dr_no " +"."+
							"      ?element   ds:location  ?location " +	
							"}}"+	
							"Order by ASC(?dr_no) ";

			// Execute the query and obtain results
			org.apache.jena.query.Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();

			// Output query results

			//	ResultSetFormatter.out(System.out, results, query);
			//	PrintWriter writer = response.getWriter();


			//	writer.print("dr_no"+"&nbsp"+"&nbsp"+"&nbsp"+"&nbsp"+"&nbsp"+"&nbsp"+"&nbsp"+"&nbsp"+"&nbsp"+"&nbsp"+"&nbsp"+"area_name"+"<br>");

			ArrayList<String> row = new ArrayList<>();

			while (results.hasNext()){
				//System.out.println("fd");
				QuerySolution sol = results.nextSolution();

				row.add(sol.getLiteral("dr_no").toString());
				row.add(sol.getLiteral("location").toString());



			}


			request.setAttribute("list", row);
			request.setAttribute("query", "query1");

			request.getRequestDispatcher("NewFile.jsp").forward(request,response);

			// Important - free up resources used running the query

			qe.close();
		}
		else if(lapd[2].equals("area"))
		{

			String date1=lapd[12];  // Nov date
			String date2=lapd[14];  // Dec date
			String crime = lapd[7];
			
			if(date1.equals("Nov"))
				date1="2015-11-30T00:00:00";
			if(date2.equals("Dec"))
				date2="2015-12-02T00:00:00";


			String queryString_1 = 

					"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
							"PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
							"PREFIX	ds:   <http://data.lacity.org/resource/ttiz-7an8/>"+ 
							"SELECT DISTINCT ?area_name ?crm_cd_desc ?date_occ " +
							//	"FROM        <http://data.lacity.org/resource/ttiz-7an8>" +
							"WHERE {" +
							//			"      ?x ds:status \"IC\"." +
							//    "      ?element   ds:area_name  ?area_name" + "."+
							"      ?element   ds:area_name  ?area_name." +
							"      ?element   ds:crm_cd_desc ?crm_cd_desc."+
							"      ?date1     ds:date_occ    ?date_occ."+
							"      FILTER (!regex(?crm_cd_desc, \""+crime+"\"))."+
							"      FILTER (?date_occ > \""+date1+"\")."+
									"FILTER (?date_occ < \""+date2+"\")."+
							"      }"+
							"ORDER by ASC(?area_name)";

			org.apache.jena.query.Query query_1 = QueryFactory.create(queryString_1);
			QueryExecution qe_1 = QueryExecutionFactory.create(query_1, model);
			org.apache.jena.query.ResultSet results_1 = qe_1.execSelect();
			//	ResultSetFormatter.out(System.out, results_1, query_1);

			ArrayList<String> row = new ArrayList<>();

			while (results_1.hasNext()){
				//System.out.println("fd");
				QuerySolution sol = results_1.nextSolution();

				row.add(sol.getLiteral("area_name").toString());
				row.add(sol.getLiteral("crm_cd_desc").toString());
				row.add(sol.getLiteral("date_occ").toString());


			}


			request.setAttribute("list", row);
			request.setAttribute("query", "query2");

			request.getRequestDispatcher("NewFile.jsp").forward(request,response);

			qe_1.close();
		}
		
		else if(lapd[0].equals("Classify"))
		{
		
			
			String assault=lapd[5];
			
			
			
			String queryString_1 = 

					"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
							"PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
							"PREFIX	ds:   <http://data.lacity.org/resource/ttiz-7an8/>"+ 
							"PREFIX	xsd:   <http://www.w3.org/2001/XMLSchema#>"+
							  "SELECT ?crime (count(?crime) as ?Count)"+
						    	"WHERE {"+
							"?element ds:crm_cd_desc ?crime."+
						    	"FILTER regex(?crime,\""+assault+"\")."+
						    		"    }"
						    	+ "GROUP BY ?crime "+
						    	  "ORDER by ASC(?crime)";
			org.apache.jena.query.Query query_1 = QueryFactory.create(queryString_1);
			QueryExecution qe_1 = QueryExecutionFactory.create(query_1, model);
			org.apache.jena.query.ResultSet results_1 = qe_1.execSelect();
			//	ResultSetFormatter.out(System.out, results_1, query_1);

			ArrayList<String> row = new ArrayList<>();

			while (results_1.hasNext()){
				//System.out.println("fd");
				QuerySolution sol = results_1.nextSolution();
                String r = sol.get("Count").toString();
                
				row.add(sol.getLiteral("crime").toString());
				row.add(r.substring(0,2));
				

			}


			request.setAttribute("list", row);
			request.setAttribute("query", "query3");

			request.getRequestDispatcher("NewFile.jsp").forward(request,response);

			qe_1.close();
		}
		
		else if(lapd[8].equals("more"))
		{
			
			String burglary=lapd[6];
			String assault=lapd[12];
		
			String queryString_1 = 

					"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
							"PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
							"PREFIX	ds:   <http://data.lacity.org/resource/ttiz-7an8/>"+ 
							"SELECT DISTINCT ?area_name " +
							"WHERE {" +
							"?element ds:area_name ?area_name." +
							"{"+
							"SELECT (count(?c1) as ?Count_Burglaries)"+
							"WHERE{"+
							"?c1 ds:crm_cd_desc \""+burglary+"\"."+
							"}"+
							"}UNION{"+
							"SELECT (count(?crm) as ?Count_Assault)"+
							"WHERE {"+
							"?c2 ds:crm_cd_desc \""+assault+"\"."+
							"    }"
							+ "GROUP BY ?area_name "+
							"ORDER by ASC(?area_name)"+
							"}}";


			org.apache.jena.query.Query query_1 = QueryFactory.create(queryString_1);
			QueryExecution qe_1 = QueryExecutionFactory.create(query_1, model);
			org.apache.jena.query.ResultSet results_1 = qe_1.execSelect();
			//	ResultSetFormatter.out(System.out, results_1, query_1);

			ArrayList<String> row = new ArrayList<>();

			while (results_1.hasNext()){
				//System.out.println("fd");
				QuerySolution sol = results_1.nextSolution();

				row.add(sol.getLiteral("area_name").toString());

			}


			request.setAttribute("list", row);
			request.setAttribute("query", "query4");

			request.getRequestDispatcher("NewFile.jsp").forward(request,response);

			qe_1.close();
		}

	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
