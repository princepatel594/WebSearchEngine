package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

import com.google.gson.Gson;

import logic.FrequencyMatcher;

/**
 * Servlet implementation class mainController
 */
@WebServlet("/searchController")
public class searchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public searchController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doOperations(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	private void doOperations(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO code
		String sname = request.getParameter("sname");
		String buttonType = request.getParameter("buttonType");
		if (buttonType.contains("frequency")) {
			FrequencyMatcher frequencyMatcher = new FrequencyMatcher();
			String name = sname.toString();
			request.setAttribute("frequencyList", frequencyMatcher.frequencysearch(name));
			RequestDispatcher rd = request.getRequestDispatcher("/frequencypage.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (buttonType.contains("editdistance")) {
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			String[] matchingword;
			FrequencyMatcher c1 = new FrequencyMatcher();
			String name = sname.toString().toLowerCase();
			ArrayList<String> arrayList = new ArrayList<String>();
			File folder = new File("C:\\Users\\src\\textfile\\");
			File[] listOfFiles = folder.listFiles();

			for (int k = 0; k < listOfFiles.length; k++) {
				if (listOfFiles[k].isFile()) {
					arrayList.add(
							"C:\\Users\\src\\textfile\\" + listOfFiles[k].getName());
				}
			}
			for (int j = 0; j < arrayList.size(); j++) {
				org.jsoup.nodes.Document doc1 = Jsoup.parse(new File(arrayList.get(j)), "UTF-8", "www.w3sfjj.com");
				String text = doc1.text().toLowerCase();
				matchingword = text.split(" ");
				for (int l = 0; l < matchingword.length; l++) {
					// TODO code
					if (matchingword[l].contains(name)) {
						int a = c1.minDistance(name, matchingword[l]);
						hashMap.put(matchingword[l], a);
					}
				}
			}
			Map<Integer, String> map = sortByValues(hashMap);
			Set set2 = map.entrySet();
			Iterator iterator2 = set2.iterator();
			int wordLimit = 0;
			// TODO code
			List<String> sugArr = new ArrayList<>();
			wordLimit = 0;
			while (iterator2.hasNext() && wordLimit < 50) {
				Map.Entry me2 = (Map.Entry) iterator2.next(); 
				sugArr.add(me2.getKey().toString().replaceAll("[^a-zA-Z0-9]", ""));
				System.out.println(me2.getKey());
				wordLimit++;
			}

			// This is to remove duplicate entries so that duplicate suggestions won't
			// appear.
			Set<String> hs = new HashSet<>();
			hs.addAll(sugArr);
			sugArr.clear();
			sugArr.addAll(hs);
			
			// TODO code
			String json = new Gson().toJson(sugArr);
			System.out.println("json==" + json);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}

	private static HashMap sortByValues(HashMap map) {
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}
}
