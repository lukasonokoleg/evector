package univ.evector.gwt.service;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import univ.evector.db.dao.NavaParagraphDao;
import univ.evector.gwt.client.service.NavaParagraphService;

@Service
@Transactional
public class NavaParagraphServiceImpl implements NavaParagraphService {

    @Autowired
    private NavaParagraphDao navaParagraphDao;

    @Override
    public void updateBookNavaParagraph(Long prgId) throws SparkBusinessException {
        navaParagraphDao.updateBookNavaParagraph(prgId);
    }

}