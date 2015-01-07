package univ.evector.internal.service.impl;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.db.dao.PrivilegesDao;
import univ.evector.internal.service.UserService;
import eu.itreegroup.spark.application.shared.security.DocumentId;
import eu.itreegroup.spark.application.shared.security.DocumentPrivileges;
import eu.itreegroup.spark.application.shared.security.DocumentType;
import eu.itreegroup.spark.gwt.application.client.security.PrivilegesService;

@Service
@Transactional
public class PrivilegesServiceImpl implements PrivilegesService {

    @Autowired
    private PrivilegesDao privilegesDao;

    @Autowired
    private UserService userService;

    @Override
    public DocumentPrivileges findDocumentPrivileges(DocumentType type, Long id) throws SparkBusinessException {
        Long userId = userService.currentUserId();
        DocumentPrivileges retVal = privilegesDao.findDocumentPrivileges(type, id, userId);
        return retVal;
    }

}
