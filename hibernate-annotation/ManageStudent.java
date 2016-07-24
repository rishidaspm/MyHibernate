package hconfig;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


public class ManageStudent {

	private static SessionFactory sf;
	public static void main(String[] args) {
		
		try{
			//sf = new Configuration().configure("hconfig/hib.cfg.xml").buildSessionFactory();
			sf = new Configuration().configure("hconfig/hib.cfg.xml").addAnnotatedClass(hconfig.Student.class).buildSessionFactory();
		}
		catch (Throwable ex) {
			System.err.println("Failed to create session factory");
			throw new ExceptionInInitializerError(ex);
		}
		ManageStudent ms= new ManageStudent();
		
		Integer s1 = ms.addStudent("HRITHI", "ROSH", 42);
		Integer s2 = ms.addStudent("SAL", "MAN", 42);
		Integer s3 = ms.addStudent("SHA", "RUKH", 42);
		
		ms.listStudents();
		
		
		ms.deleteEmployee(s1);
		
		ms.listStudents();
		

	}
	public Integer addStudent(String fname,String lname,int yearLevel){
		Session session = sf.openSession();
		
		Transaction tx = null;
		
		Integer sid = null;
		
		try {
			tx = session.beginTransaction();
			Student st = new Student();
			st.setFirstname(fname);
			st.setLastname(lname);
			st.setYearLevel(yearLevel);
			sid = (Integer)session.save(st);
			tx.commit();
		} catch (HibernateException ex) {
           if(tx!=null)tx.rollback();
           ex.printStackTrace();
		}
		finally {
			session.close();
		}
		return sid;
	}
	public void listStudents(){
		Session session = sf.openSession();
        Transaction tx = null;
		
		Integer sid = null;
		
		try {
			tx = session.beginTransaction();
			List students = session.createQuery("FROM Student").list();
			for(Iterator iterator = students.iterator();iterator.hasNext();){
				
				Student student =(Student) iterator.next();
				System.out.println("First Name:"+student.getFirstname());
				System.out.println("Last Name:"+student.getLastname());
				System.out.println("Year Level:"+student.getYearLevel());
					
			}
			tx.commit();	
			
		} catch (HibernateException ex) {
           if(tx!=null)tx.rollback();
           ex.printStackTrace();
		}
		finally {
			session.close();
		}
		
		
		
	}
	
	public void deleteEmployee(int empId){
		
		
		Session session = sf.openSession();
        Transaction tx = null;		
		try {
			tx = session.beginTransaction();
			Student student = (Student) session.get(Student.class, empId);
			session.delete(student);
			tx.commit();
			
		} catch (HibernateException ex) {
           if(tx!=null)tx.rollback();
           ex.printStackTrace();
		}
		finally {
			session.close();
		}
	}

}
