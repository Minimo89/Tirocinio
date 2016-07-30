package roma3;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SQLServlet2
 */
public class SQLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SQLServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Class.forName("org.relique.jdbc.csv.CsvDriver");
			Connection conn = DriverManager.getConnection("jdbc:relique:csv:C:\\Program Files\\apache-tomcat-8.0.33\\webapps\\Tirocinio-prova1\\upload");
			Statement state = conn.createStatement();
			String query = request.getParameter("query");
			System.out.println(query);
			ResultSet results = state.executeQuery(query);
			
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Program Files\\apache-tomcat-8.0.33\\webapps\\Tirocinio-prova1\\upload\\file.csv"), "utf-8"));
			
			int numeroColonne = results.getMetaData().getColumnCount();
			
			for(int i=1; i < (numeroColonne + 1); i++){
				writer.write(results.getMetaData().getColumnName(i));
				if(i < numeroColonne)
					writer.write(",");
				else
					writer.write("\r\n");
			}
			
			while(results.next()){
				
				for(int j=1; j < (numeroColonne + 1); j++){
					writer.write(results.getString(j));
					if(j < numeroColonne)
						writer.write(",");
					else
						writer.write("\r\n");
				}
				
			}
			
			writer.flush();
			writer.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getServletContext().getRequestDispatcher("/fileUploaded.jsp").forward(request, response);
	}

}
