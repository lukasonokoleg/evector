package univ.evector.db.dao;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.stereotype.Repository;

import eu.itreegroup.spark.application.shared.security.DocumentPrivileges;
import eu.itreegroup.spark.application.shared.security.DocumentType;

@Repository
public interface PrivilegesDao {

    DocumentPrivileges findDocumentPrivileges(DocumentType type, Long id, Long userId) throws SparkBusinessException;

}
