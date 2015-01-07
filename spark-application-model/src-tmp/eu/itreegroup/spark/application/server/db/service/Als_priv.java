package eu.itreegroup.spark.application.server.db.service;

import eu.itreegroup.spark.application.shared.db.bean.Spr_doc_priv_ot;
import lt.jmsys.spark.bind.annotations.Generated;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.service.Service;

@Generated
public interface Als_priv extends Service {

    Spr_doc_priv_ot find_doc_priv(String p_doc_type, Double p_doc_id) throws SparkBusinessException;

}
