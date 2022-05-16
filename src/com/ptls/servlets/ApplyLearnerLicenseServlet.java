package com.ptls.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.ptls.daos.PersonDao;
import com.ptls.models.AadharInfoModel;
import com.ptls.models.LicenseHolderModel;

/**
 * Servlet implementation class ApplyLearnerLicenseServlet
 */
@WebServlet("/applyLL")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5, 
maxRequestSize = 1024 * 1024 * 5 * 5)
public class ApplyLearnerLicenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyLearnerLicenseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		//String UPLOAD_DIRECTORY = "C:\\Users\\mir\\Documents\\WS\\PTLS\\WebContent\\docs";
		String UPLOAD_DIRECTORY = "C:\\Users\\Tahir Hussain\\Documents\\samwisews\\PTLS\\WebContent\\docs";
		boolean isFilesSaved = false; 
		String typeOfLicenses = "";
		//System.out.println("HEREEEEEEEEEE1");
		LicenseHolderModel lm = new LicenseHolderModel();
		//AadharInfoModel aam = (AadharInfoModel) request.getSession().getAttribute("aam");
		/*System.out.println("HEREEEEEEEEEE2");
		lm.setAadhar(Long.parseLong((String) request.getSession().getAttribute("aadhar")));
		System.out.println("HEREEEEEEEEEE3");
		lm.setAddress(aam.getAddress());
		lm.setCountry(aam.getCountry());
		lm.setDistrict(aam.getDistrict());
		lm.setDob(aam.getDob());
		lm.setBloodGroup(request.getParameter("bloodGroup"));
		//lm.setEmailId(aam.getEmailAddress());
		lm.setEmergencyMobNo(request.getParameter("emergencyMobNo"));
		lm.setFathers_name(aam.getFathers_name());
		lm.setFull_name(aam.getFull_name());
		lm.setGender(aam.getGender());
		lm.setIdentificationMark(request.getParameter("identificationMark"));
		lm.setPh_n0(aam.getPh_n0());
		lm.setPhoto_url(aam.getPhoto_url());
		lm.setPlaceOfBirth(request.getParameter("placeOfBirth"));
		lm.setPostal_code(aam.getPostal_code());
		lm.setState(aam.getState());
		System.out.println("HEREEEEEEEEEE4");
		System.out.println(request.getParameter("identificationMark"));*/
		try {
			List<FileItem> multiparts = new ServletFileUpload(
			        new DiskFileItemFactory()).parseRequest(new ServletRequestContext(request));
			//System.out.println("HEREEEEEEEEEE5");
			System.out.println(multiparts.toString());
			for (FileItem item : multiparts) {
				String fieldName2 = item.getFieldName(); 
				//System.out.println(fieldName2);
				if (item.isFormField()) {
					if(fieldName2.equals("bloodGroup")){
						lm.setBloodGroup(item.getString());
					}
					else if(fieldName2.equals("placeOfBirth")){
						lm.setPlaceOfBirth(item.getString());
					}
					else if(fieldName2.equals("emergencyMobNo")){
						lm.setEmergencyMobNo(item.getString());
					}
					else if(fieldName2.equals("identificationMark")){
						lm.setIdentificationMark(item.getString());
					}
					else if(fieldName2.equals("typeOfLic")){
						typeOfLicenses = typeOfLicenses + item.getString()+",";
					}
				}
                if (!item.isFormField()) {
                   String fileName = item.getName();
                   System.out.println(fileName);
                   String fieldName = item.getFieldName();
                   System.out.println(fieldName);
                   
                   File file = new File(UPLOAD_DIRECTORY + File.separator + fieldName+"-"+lm.getAadhar()+fileName);
                   System.out.println(UPLOAD_DIRECTORY + File.separator + fieldName+"-"+lm.getAadhar()+fileName);
                   item.write(file);
                   isFilesSaved = true;
                }
             }
			//System.out.println("HEREEEEEEEEEE6");
		} catch (Exception e) {
			//System.out.println("HEREEEEEEEEEE7");
			e.printStackTrace();
			isFilesSaved = false;
		}
		//System.out.println("HEREEEEEEEEEE8");
		lm.setAadhar(Long.parseLong((String) request.getSession().getAttribute("aadhar")));
		System.out.println(lm.getAadhar());
		if(isFilesSaved){
			//System.out.println("HEREEEEEEEEEE9");
			PersonDao pd = new PersonDao();
			try {
				//System.out.println("HEREEEEEEEEEE11");
				pd.updateLicenseHolder(lm);
				//System.out.println("HEREEEEEEEEEE12");
			} catch (Exception e) {
				//System.out.println("HEREEEEEEEEEE13");
				e.printStackTrace();
			}
			
		}
		
		String[] licenses = typeOfLicenses.split(",");
		
		request.getSession().setAttribute("typeOfLicenses", licenses);
		request.getSession().setAttribute("totalAmount", licenses.length*150);
		
		try {
			response.sendRedirect(request.getContextPath() + "/views/payment.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
