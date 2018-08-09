package com.sol.erp.util;

import java.util.ArrayList;
import java.util.Hashtable;

public class CityUtil {

	private static String splstr = null;
	
	private static Hashtable<String, String> citytb = new Hashtable<String, String>();

	public static void populateList() {
		citytb.put("Andhra Pradesh",
				"Anantapur,Guntakal,Guntur,Hyderabad/Secunderabad,Kakinada,Kurnool,Nellore,Nizamabad,Rajahmundry,Tirupati,Vijaywada,Visakhapatanam,Warangal,Other");

		citytb.put("Arunachal Pradesh", "Itanagar,Other");

		citytb.put("Assam", "Guwahati,Silchar,Other");

		citytb.put("Delhi", "Delhi");

		citytb.put("Bihar", "Bhagalpur,Patna,Gaya,Other");

		citytb.put("Chhattisgarh", "Bhilai,Bilaspur,Raipur,Other");

		citytb.put("Goa", "Panjim/Panaji,Vasco Da Gama,Other");

		citytb.put("Gujarat",
				"Ahmedabad,Anand,Ankleshwar,Bharuch,Bhavnagar,Bhuj,Gandhinagar,Gir,Jamnagar,Kandla,Porbandar,Rajkot,Surat,Vadodra/Baroda,Valsad,Vapi,Other");

		citytb.put("Haryana", "Ambala,Chandigarh,Faridabad,Gurgaon,Hisar,Karnal,Kurukshektra,PanipatRohtak,Other");

		citytb.put("Himachal Pradesh", "Dalhousie,Dharmasala,Kulu/Manali,Shimla,Other");

		citytb.put("Jammu & Kashmir", "Jammu,Srinagar,Other");

		citytb.put("Jharkhand", "Bokaro,Dhanbad,Jamshedpur,Ranchi,Other");

		citytb.put("Karnatka",
				"Bengaluru/Banglore,Belgaum,Bellary,Bidar,Dharwad,Gulbarga,Hubli,Kolar,Mangalore,Mysoru/Mysore,Other");

		citytb.put("Kerala",
				"Calicut,Cochin,Ernakulam,Kannur,Kochi,Kollam,Kottayam,Kozhikode,Palakkad,Palghat,Thrissur,Trivandrum,Other");

		citytb.put("Madhya Pradesh", "Bhopal,Gwalior,Indore,JabalpurUjjain,Other");

		citytb.put("Maharastra",
				"Ahmednagar,Aurangabad,Jalgaon,Kolhapur,Mumbai,Mumbai Suburbs,Nagpur,Nasik,Navi Mumbai,Pune,Solapur,Other");

		citytb.put("Manipur", "Imphal,Other");

		citytb.put("Meghalaya", "Shillong,Other");

		citytb.put("Mizoram", "Aizawal,Other");

		citytb.put("Nagaland", "Dimapur,Other");

		citytb.put("Orissa", "Bhubneshwar,Cuttak,Paradeep,Puri,Rourkela,Other");

		citytb.put("Pujab", "Amritsar,Bhatinda,Chandigarh,Jalandhar,Ludhiana,Mohali,Pathankot,Patiala,Other");

		citytb.put("Ajmer", "Ajmer,Jaipur,Jaisalmer,Jodhpur,Kota,Udaypur,Other");

		citytb.put("Sikkim", "Gangtok");

		citytb.put("Tamilnadu",
				"Chennai,Coimbatore,Cuddalore,Erode,Hosure,Madurai,Nagarcoil,Ooty,Salem,Thanjavur,Trunalveli,Trichy,Tuticorin,Vellore,Other");

		citytb.put("Tripura", "Agartala,Other");

		citytb.put("Uttar Pradesh",
				"Agra,Aligarh,Allahabad,Bareilly,Faizabad,Ghaziabad,Gorakhpur,Kanpur,Lucknow,Mathura,Meerut,Moradabad,NoidaVaranasi/Banaras,Other");

		citytb.put("Uttaranchal", "Dehradun,Roorkee,Other");

		citytb.put("West Bengal", "Asansol,Durgapur,Haldia,Kharagpur,Kolkata,Siliguri,Other");

		citytb.put("Union Territories", "Chandigarh,Dadar & Nagar Haveli - Silvasa");
	}

	public static void getCityArray(String paramString) {
		populateList();
		splstr = String.valueOf(citytb.get(paramString));
		splstr.split(",");
	}

	public static ArrayList<String> getCityList(String paramString) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		localArrayList.clear();

		getCityArray(paramString);
		return localArrayList;
	}

}
