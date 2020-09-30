package br.edu.utfpr.dv.sireata.dao.base;

import java.sql.SQLException;

public interface IDAO<MODEL> {
    public abstract MODEL buscarPorId(int id) throws SQLException;
}
