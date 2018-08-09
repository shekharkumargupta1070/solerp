package com.sol.erp;

import java.util.ArrayList;
import java.util.Hashtable;

public class TIMOption {
	/* 6 */ String splstr = null;

	String[] splitedArray;

	public static Hashtable<String, String> optiontb = new Hashtable<String, String>();

	public static String code;
	public static String optionname;

	public void populateList() {
		optiontb.put("I", "No Work,Hardware Problem,Sickness,Others");
		optiontb.put("T", "X-Steel,Auto CAD,Steel Detailing,Meating");
		optiontb.put("M", "Project Management,X-Steel,Auto CAD,Steel Detailing,Others");
	}

	public String[] getOptionArray(String paramString) {
		populateList();
		this.splitedArray = null;
		this.splstr = String.valueOf(optiontb.get(paramString));
		this.splitedArray = this.splstr.split(",");

		return this.splitedArray;
	}

	public ArrayList<String> getOptionList(String paramString) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		localArrayList.clear();

		getOptionArray(paramString);
		for (int i = 0; i < this.splitedArray.length; i++) {
			localArrayList.add(this.splitedArray[i]);
		}

		return localArrayList;
	}

}
