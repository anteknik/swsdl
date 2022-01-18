/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.servlet;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.StandaloneSoapUICore;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.support.SoapUIException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.xmlbeans.XmlException;

@WebServlet(
    name = "AnnotatedServlet",
    description = "A sample wsdl Jsp",
    urlPatterns = {"/wsdl"}
)
public class WsdlServlet extends HttpServlet{
 
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
 
        try {
            PrintWriter writer = response.getWriter();
            
            
            
            File projectFile = new File("SoapUIProjects/TestProjectA-soapui-project.xml");
            
            SoapUI.setSoapUICore(new StandaloneSoapUICore(true));
            
            WsdlProject project =  project = new WsdlProject("TestProjectA");
            
            
            WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, "http://www.dneonline.com/calculator.asmx?wsdl");
            //WsdlInterface[] wsdls= WsdlImporter.importWsdl(project, "http://localhost:8080/Calculator?wsdl");
            
            
// http://www.dneonline.com/calculator.asmx?wsdl
            //http://rawebd01/RADev02/Pricing/PriceService.svc?wsdl
            System.out.println("The wsdl count =" + wsdls.length); //To get the number of  wsdl interfaces
            
            for (int j = 0; j < wsdls.length; j++) {
                
                WsdlInterface wsdl = wsdls[j];
                
                String soapVersion = wsdl.getSoapVersion().toString();
                
                System.out.println("The SOAP version =" + soapVersion);
                
                System.out.println("The binding name = " + wsdl.getBindingName());
                
                System.out.println("The binding type =" + wsdl.getBinding());
                
                int c = wsdl.getOperationCount();
                
                System.out.println("The total number of operations =" + c);
                
                String reqContent = "";
                
                String resContent = "";
                
                String resContent2 = "";
                
                String result = "";
                
                for (int i = 0; i < c; i++) {
                    
                    WsdlOperation op = wsdl.getOperationAt(i);
                    
                    String opName = op.getName();
                    
                    System.out.println("OP:" + opName);
                    
                    reqContent = op.createRequest(true);
                    
                    resContent = op.createRequest(true);
                    
                    resContent2 = op.createResponse(false);
                    
                    //System.out.println(reqContent);
                    writer.println(opName + "********************* Request *********************");
                    System.out.println(opName + "********************* Request *********************");
                    writer.println(resContent);
                    System.out.println(resContent);
                    
                    writer.println(opName + "********************* Response *********************");
                    System.out.println(opName + "********************* Response *********************");
                    
                    System.out.println(resContent2);
                    
                    WsdlRequest req = op.addNewRequest("Req_" + soapVersion + "_" + opName);
                    
                    System.out.println("Req_" + soapVersion + "_" + opName);
                    
                    req.setRequestContent(reqContent);
                    
                }
                
            }
            
            writer.flush();
        } catch (XmlException ex) {
            Logger.getLogger(WsdlServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SoapUIException ex) {
            Logger.getLogger(WsdlServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(WsdlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        PrintWriter writer = response.getWriter();
        writer.println("------doPost-----------------");
        writer.flush();
 
    }
}