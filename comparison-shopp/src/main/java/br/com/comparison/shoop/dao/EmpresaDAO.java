package br.com.comparison.shoop.dao;

import br.com.comparison.shoop.entity.Empresa;

public interface EmpresaDAO extends GenericDAO<Empresa> {

	public Empresa findEmpresaByIdUser(int idUser);
	
}
