package com.hackerthon.service;

import org.xml.sax.SAXException;
import java.sql.Connection;
import java.util.logging.Logger;
import java.sql.DriverManager;
import javax.xml.parsers.ParserConfigurationException;
import java.sql.PreparedStatement;
import javax.xml.xpath.XPathExpressionException;
import com.hackerthon.common.UtilTRANSFORM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.sql.Statement;
import com.hackerthon.common.UtilQ;
import java.io.IOException;
import com.hackerthon.model.E;
import java.util.ArrayList;
import java.util.Map;
import com.hackerthon.common.UtilC;

public class getEmpService extends UtilC {

	private final ArrayList<E> el = new ArrayList<E>();

	private static Connection c;

	private static Statement s;

	private PreparedStatement ps;

	public getEmpService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
					p.getProperty("password"));
		} catch (Exception e) {
		} 
	}

	public void EMPLOEESFROMXML() {

		try {
			int s = UtilTRANSFORM.XMLXPATHS().size();
			for (int i = 0; i < s; i++) {
				Map<String, String> l = UtilTRANSFORM.XMLXPATHS().get(i);
				E EMPLOYEE = new E();
				EMPLOYEE.eMPLOYEEiD(l.get("XpathEmployeeIDKey"));
				EMPLOYEE.fULLnAME(l.get("XpathEmployeeNameKey"));
				EMPLOYEE.aDDRESS(l.get("XpathEmployeeAddressKey"));
				EMPLOYEE.fACULTYNAME(l.get("XpathFacultyNameKey"));
				EMPLOYEE.dEPARTMENT(l.get("XpathDepartmentKey"));
				EMPLOYEE.dESIGNATION(l.get("XpathDesignationKey"));
				el.add(EMPLOYEE);
				System.out.println(EMPLOYEE.toString() + "\n");
			}
		} catch (Exception e) {
		}
	}

	public void eMPLOYEEtABLEcREATE() {
		try {
			s = c.createStatement();
			s.executeUpdate(UtilQ.Q("q2"));
			s.executeUpdate(UtilQ.Q("q1"));
		} catch (Exception e) {
		}
	}

	public void eMPLOYEESaDD() {
		try {
			ps = c.prepareStatement(UtilQ.Q("q3"));
			c.setAutoCommit(false);
			for(int i = 0; i < el.size(); i++){
				E e = el.get(i);
				ps.setString(1, e.EMPLOYEEiDgET());
				ps.setString(2, e.fULLnAMEgET());
				ps.setString(3, e.aDDRESSgET());
				ps.setString(4, e.fACULTYnAMEgET());
				ps.setString(5, e.dEPARTMENTgET());
				ps.setString(6, e.dESIGNATIONgET());
				ps.addBatch();
			}
			ps.executeBatch();
			c.commit();
		} catch (Exception e) {
		}
	}

	public void eMPLOYEEGETBYID(String eid) {

		E e = new E();
		try {
			ps = c.prepareStatement(UtilQ.Q("q4"));
			ps.setString(1, eid);
			ResultSet R = ps.executeQuery();
			while (R.next()) {
				e.eMPLOYEEiD(R.getString(1));
				e.fULLnAME(R.getString(2));
				e.aDDRESS(R.getString(3));
				e.fACULTYNAME(R.getString(4));
				e.dEPARTMENT(R.getString(5));
				e.dESIGNATION(R.getString(6));
			}
			ArrayList<E> l = new ArrayList<E>();
			l.add(e);
			eMPLOYEEoUTPUT(l);
		} catch (Exception ex) {
		}
	}

	public void EMPLOYEEDELETE(String eid) {

		try {
			ps = c.prepareStatement(UtilQ.Q("q6"));
			ps.setString(1, eid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eMPLOYEEdISPLAY() {

		ArrayList<E> l = new ArrayList<E>();
		try {
			ps = c.prepareStatement(UtilQ.Q("q5"));
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				E e = new E();
				e.eMPLOYEEiD(r.getString(1));
				e.fULLnAME(r.getString(2));
				e.aDDRESS(r.getString(3));
				e.fACULTYNAME(r.getString(4));
				e.dEPARTMENT(r.getString(5));
				e.dESIGNATION(r.getString(6));
				l.add(e);
			}
		} catch (Exception e) {
		}
		eMPLOYEEoUTPUT(l);
	}
	
	public void eMPLOYEEoUTPUT(ArrayList<E> l){
		
		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out
				.println("================================================================================================================");
		for(int i = 0; i < l.size(); i++){
			E e = l.get(i);
			System.out.println(e.EMPLOYEEiDgET() + "\t" + e.fULLnAMEgET() + "\t\t"
					+ e.aDDRESSgET() + "\t" + e.fACULTYnAMEgET() + "\t" + e.dEPARTMENTgET() + "\t"
					+ e.dESIGNATIONgET() + "\n");
			System.out
			.println("----------------------------------------------------------------------------------------------------------------");
		}
		
	}
}
