package com.sol.erp.test;

import com.sol.erp.dao.ProjectDAO;
import com.sol.erp.dto.ProjectDTO;

public class ProjectDAOTest {

	public static void testProjectDetails() {
		ProjectDAO.chacheInMemory();

		//System.out.println("Project Name: " + ProjectDAO.getProjectName("2015032"));
		//System.out.println("Client Name: " + ProjectDAO.getClientName("2015032"));
		//System.out.println("Team Leader: " + ProjectDAO.getTeamLeader("2015032"));
	}
	
	public static void testProjectNumberGeneration(){
		String generatedProjectNumber = ProjectDAO.generateNewProjectNumber(2016);
		long generatedProjectNumber1 = Long.parseLong(ProjectDAO.generateNewProjectNumber(2016));
		System.out.println("New Project Number: "+generatedProjectNumber);
		System.out.println("New Project Number: "+generatedProjectNumber1);
	}
	
	public static void testFindByProjectNumber(){
		ProjectDTO dto = ProjectDAO.findByProjectNumber(2015102);
		System.out.println(dto);
	}

	public static void main(String args[]) {
		//testFindByProjectNumber();
		testProjectDetails();
	}
}
