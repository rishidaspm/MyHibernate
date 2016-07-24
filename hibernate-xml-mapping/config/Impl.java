package config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import config.Student;

public class Impl {

	public static void main(String[] args) {
		
		Configuration cfg = new Configuration();
		cfg.configure("config/hconfig.cfg.xml");
		SessionFactory sf = cfg.buildSessionFactory();
		Session s= sf.openSession();
		
		Transaction t = s.beginTransaction();
		
	Student student = new Student();
		
		student.setStudentid(25);
		student.setFirstname("ri");
		student.setLastname("l");
		student.setYearLevel(5);
		
		s.save(student);
		s.flush();
		t.commit();
		s.close();
		
		

	}

}
