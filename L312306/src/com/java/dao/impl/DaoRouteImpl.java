package com.java.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.java.dao.idao.IRouteDao;

import hibernate.Route;
@SuppressWarnings("unchecked")
@Repository("routeDao")
public class DaoRouteImpl implements IRouteDao {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void save(Route t) throws Exception {
		sessionFactory.getCurrentSession().save(t);

	}

	@Override
	public void delete(Route t) throws Exception {
		sessionFactory.getCurrentSession().delete(t);

	}

	@Override
	public void update(Route newT) throws Exception {
		sessionFactory.getCurrentSession().update(newT);

	}

	@Override
	public List<Route> findAll() throws Exception {
		return sessionFactory.getCurrentSession().createQuery("From Route").list();
	}

	@Override
	public Route findById(Integer k) throws Exception {
		Session session = sessionFactory.openSession();
		Route entity = (Route) session.get(Route.class, k);
		session.close();
		return entity;
	}

	@Override
	public List<Route> findByProperty(String propName, Object propVal) throws Exception{
		Session session = sessionFactory.openSession();
		List<Route> routeList = session.createQuery("from Route d where d."+ propName + "=?")
				.setParameter(0, propVal)
				.list();
		session.close();
		return routeList;
	}
	
	@Override
	public List<Object[]> findByStationInfo(String fromStation, String toStation) throws Exception{		
		Session session = sessionFactory.openSession();
		List<Object[]> objs  =  session.createSQLQuery("select r.tid tid,"
				+ "r.site fromStation,"
				+ "r1.site toStation,"
				+ "to_char(r.starttim,'hh24:mi') startime,"
				+ "to_char(r1.endtime,'hh24:mi') endtime,"
				+ "r.siteorder fromsiteorder,"
				+ "r1.siteorder tositeorder,"
				+ "r.mile frommile, "
				+ "r1.mile tomile "
				+ "from route r left join route r1 "
				+ "on r.tid = r1.tid "
				+ "where r.site =? and r1.site =?")
				.setParameter(0, fromStation)
				.setParameter(1, toStation).list();
		session.clear();
		return objs;
	}
	
	@Override
	public List<Object[]> findByIdStationInfo(String fromStation, String toStation,String tid) throws Exception{		
		Session session = sessionFactory.openSession();
		List<Object[]> objs  =  session.createSQLQuery("select r.tid tid,"
				+ "r.site fromStation,"
				+ "r1.site toStation,"
				+ "to_char(r.starttim,'hh24:mi') startime,"
				+ "to_char(r1.endtime,'hh24:mi') endtime,"
				+ "r.siteorder fromsiteorder,"
				+ "r1.siteorder tositeorder,"
				+ "r.mile frommile, "
				+ "r1.mile tomile "
				+ "from route r left join route r1 "
				+ "on r.tid = r1.tid "
				+ "where r.site =? and r1.site =? and r.tid = ?")
				.setParameter(0, fromStation)
				.setParameter(1, toStation)
				.setParameter(2, tid).list();
		session.clear();
		return objs;
	}
	
}
