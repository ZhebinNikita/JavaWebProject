package by.epam.project.service.impl;

import by.epam.project.service.Service;
import by.epam.project.database.dao.impl.PassportDao;
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
        // associate passport with some user !!!!!!!!!!!!!!!!!!!!!!!!

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


    public Passport takePassport(String identificationNumber) throws ProjectException {
        return passportDao.takePassport(identificationNumber);
    }

}
