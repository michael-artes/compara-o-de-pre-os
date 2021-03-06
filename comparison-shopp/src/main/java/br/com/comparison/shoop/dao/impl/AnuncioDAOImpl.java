package br.com.comparison.shoop.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.comparison.shoop.dao.AnuncioDAO;
import br.com.comparison.shoop.entity.Anuncio;

public class AnuncioDAOImpl extends GenericDAOImpl<Anuncio> implements AnuncioDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Anuncio> findAnunciosByEmpresa(int idEmpresa) {
		
		Session session = (Session) entityManager.getDelegate();
		
		Criteria crit = session.createCriteria(Anuncio.class, "a");
		
		crit.add(Restrictions.eq("a.empresa.id", idEmpresa));
		
		return crit.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Anuncio> findAnunciosByPesquisa(String nomePesquisa, BigDecimal maiorQue, BigDecimal menorQue) {
		
		Session session = (Session) entityManager.getDelegate();
		
		Criteria crit = session.createCriteria(Anuncio.class, "a");
		
		crit.add(Restrictions.like("a.nome", nomePesquisa, MatchMode.ANYWHERE).ignoreCase());
		
		crit.createAlias("a.empresa", "e");
		
		crit.add(Restrictions.eq("e.ativo", true));
		
		if (maiorQue.doubleValue() > 0 && menorQue.doubleValue() > 0) {
			
			crit.add(Restrictions.between("a.valor", maiorQue, menorQue));
			
		} else {
			
			if (maiorQue.doubleValue() > 0) {
				crit.add(Restrictions.ge("a.valor", maiorQue));
			}
			
			if (menorQue.doubleValue() > 0) {
				crit.add(Restrictions.le("a.valor", menorQue));
			}
			
		}
		
		return crit.list();
	}
	
}
