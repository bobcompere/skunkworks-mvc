package net.fourstrategery.cloud;


	import java.io.FileOutputStream;
	import java.io.InputStream;
	import java.io.PrintWriter;
	import java.util.Date;
	import java.util.Enumeration;

	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;


	public class DumpServlet extends HttpServlet {

	int fileNum = 0;

	public void service(HttpServletRequest req, HttpServletResponse resp) {
		try {
			InputStream iStream = req.getInputStream();
			String fname = "dump." + fileNum;
			FileOutputStream fout = new FileOutputStream(fname);
			fileNum++;
			byte[] buffer =  new byte[4096];
			int lastRead;
			
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.println("<html>");
			out.println("<h1>DUMPER</h1>");
			
			
			System.out.println("DUMPER Request @ " + new Date() + " from " + req.getRemoteAddr());
			
			String enc = req.getCharacterEncoding();
			System.out.println("Character Encoding [" + enc + "]");
			out.println("Character Encoding [" + enc + "]<br/>");
			
			Enumeration hdrs = req.getHeaderNames();
			while (hdrs.hasMoreElements())  {
				String hkey = (String)hdrs.nextElement();
				String hval = req.getHeader(hkey);
				System.out.println("dumper[header][" + hkey + "][" + hval + "]");
				out.println("Header Value [" + hkey + "]=[" + hval + "]<br/>");
			}
			
			Enumeration pnams = req.getParameterNames();
			while (pnams.hasMoreElements())  {
				String pnam = (String)pnams.nextElement();
				String[] pvals = req.getParameterValues(pnam);
				for (int i1=0;i1<pvals.length;i1++)  {
					System.out.println("dumper[parms][" + pnam + "][" + i1 + "][" + pvals[i1] + "]");
					out.println("Parameters [" + pnam + "][" + i1 + "][" + pvals[i1] + "]<br/>");
				}
			}
				
			long totalBytes = 0;
			
			while ((lastRead = iStream.read(buffer)) > 0)  {
				fout.write(buffer,0,lastRead);
				totalBytes += lastRead;
			}
			
			fout.flush();
			fout.close();
			
			out.println(totalBytes + " data bytes written to : " + fname + "</br>");
			
			out.println("</html>");
			out.close();
			}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	}
