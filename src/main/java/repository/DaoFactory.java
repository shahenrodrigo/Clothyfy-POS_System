package repository;

import repository.custom.impl.EmployeeDaoImpl;
import repository.custom.impl.LoginDaoImpl;
import repository.custom.impl.ProductDaoImpl;
import repository.custom.impl.SupplierDaoImpl;
import util.DaoType;

public class DaoFactory {
    private static  DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance == null ? instance = new DaoFactory() : instance;
    }

    public <T extends SuperDao>T getServiceType(DaoType type){
        switch (type){
            case EMPLOYEE: return (T) new EmployeeDaoImpl();
            case PRODUCT: return (T) new ProductDaoImpl();
            case SUPPLIER: return (T) new SupplierDaoImpl();
            case LOGIN: return (T) new LoginDaoImpl();
        }
        return null;
    }
}
