package by.epam.project.services.impl;

import by.epam.project.services.Service;
import by.epam.project.dao.impl.PassportDao;
import by.epam.project.entity.Passport;
import by.epam.project.exception.ProjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class PassportService implements Service {

    private final static Logger LOG = LogManager.getRootLogger();
    private PassportDao passportDao = new PassportDao();


    public boolean add(Passport p) throws ProjectException {
        boolean pAdded;
        pAdded = passportDao.insert(p);
        return pAdded;
    }


    public boolean deleteByID(Passport p) throws ProjectException {
        boolean pDeleted;
        pDeleted = passportDao.delete(p);
        return pDeleted;
    }


    public boolean updateByID(Passport oldP, Passport newP) throws ProjectException {
        boolean pUpdated;
        pUpdated = passportDao.update(oldP, newP);
        return pUpdated;
    }


    public List<Passport> getAllPassports() throws ProjectException {
        List<Passport> passports = passportDao.takeAll();
        return passports;
    }

}
