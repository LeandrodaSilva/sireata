package br.edu.utfpr.dv.sireata.bo.base;

import br.edu.utfpr.dv.sireata.dao.base.IDAO;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BOBase<MODEL, DAO extends IDAO<MODEL>>{
    public MODEL buscarPorId(int id) throws Exception {
        try{
            return getDAO(useDAOClass()).buscarPorId(id);
        }catch(Exception e){
            Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);

            throw new Exception(e.getMessage());
        }
    }

    protected DAO getDAO(Class<DAO> cls) throws Exception {
        return cls.getDeclaredConstructor().newInstance();
    }

    protected abstract Class<DAO> useDAOClass();
}
