package univ.evector.db.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.stereotype.Repository;

import eu.itreegroup.spark.application.bean.Classifier;
import univ.evector.db.dao.ClassifierDao;

@Repository
public class ClassifierDaoImpl implements ClassifierDao {

    @Override
    public HashMap<String, ArrayList<Classifier>> findClassifiers() throws SparkBusinessException {
        // TODO Auto-generated method stub
        return null;
    }

}
