package roma3;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
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
		
		final String UPLOAD_DIRECTORY = "upload";
		final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
		int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
		final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
		
		if(!ServletFileUpload.isMultipartContent(request)){
			PrintWriter writer = response.getWriter();
			writer.println("Request doesn't contain upload data");
			writer.flush();
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		//create directory path to store the uploaded file
		String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
		
		File uploadDir = new File(uploadPath);
			if(!uploadDir.exists()){
				uploadDir.mkdir();
			}
		
		try {
			List formItems = upload.parseRequest(request);
			Iterator iter = formItems.iterator();
			while(iter.hasNext()){
				FileItem item = (FileItem) iter.next();
				if(!item.isFormField()){
					String fileName = new File(item.getName()).getName();
					String filePath1 = uploadPath + File.separator + "file.csv";
					//String filePath2 = uploadPath + File.separator + fileName;
					File storeFile1 = new File(filePath1);
					//File storeFile2 = new File(filePath2);
					item.write(storeFile1);
					//item.write(storeFile2);
					
				}
			}
			request.setAttribute("message", "Upload has been done succesfully!");
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher("/fileUploaded.jsp").forward(request, response);
		
		
		
		
	}

}
