package univ.evector.db.dao;

import java.util.ArrayList;
import java.util.HashMap;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.stereotype.Repository;

import eu.itreegroup.spark.application.bean.Classifier;

@Repository
public interface ClassifierDao {

    HashMap<String, ArrayList<Classifier>> findClassifiers() throws SparkBusinessException;

}
