package br.edu.utfpr.dv.sireata.dao.base;

import br.edu.utfpr.dv.sireata.model.Ata;

import java.sql.SQLException;

public interface IDAO<D> {
    public abstract D buscarPorId(int id) throws SQLException;
}
