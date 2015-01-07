package univ.evector.db.dao.impl;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import univ.evector.beans.privileges.EvectorDocumentType;
import univ.evector.db.dao.PrivilegesDao;
import univ.evector.db.dao.UserDao;
import eu.itreegroup.spark.application.shared.security.DocumentId;
import eu.itreegroup.spark.application.shared.security.DocumentPrivileges;
import eu.itreegroup.spark.application.shared.security.DocumentType;

@Repository
public class PrivilegesDaoImpl implements PrivilegesDao {

    @Autowired
    private UserDao userDao;

    @Override
    public DocumentPrivileges findDocumentPrivileges(DocumentType type, Long id, Long userId) throws SparkBusinessException {
        DocumentPrivileges retVal = null;
        if (type instanceof EvectorDocumentType) {
            EvectorDocumentType evectorDocumentType = (EvectorDocumentType) type;
            switch (evectorDocumentType) {
                case USER:
                    retVal = userDao.findUserPrivileges(id, userId);
                    break;
                default:
                    break;
            }
        }
        if (retVal == null) {
            throw new IllegalStateException("Output variable retVal has NULL value.");
        } else {
            retVal.setDocumentId(id);
            retVal.setDocumentType(type);
        }

        return retVal;
    }

}
