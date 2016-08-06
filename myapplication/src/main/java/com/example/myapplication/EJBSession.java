package com.example.myapplication;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session Bean implementation class EJBSession
 */
@Stateless
@LocalBean
public class EJBSession {
	private static Logger log = LoggerFactory.getLogger(EJBSession.class);

	@PersistenceContext(unitName="licht")
	EntityManager em;

	public List<LDZustand> findAll() {
		return em.createQuery("select o from LDZustand o", LDZustand.class).getResultList();
	}
	
	public LDZustand findbyId(int id) {
		return em.find(LDZustand.class, id);
	}

	
	public void saveOrUpdate(LDZustand o) {
		log.info(o+"");
		em.merge(o);
	}
}
