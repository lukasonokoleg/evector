package lt.jmsys.spark.gwt.application.common.server.service;

import lt.jmsys.spark.gwt.application.common.client.service.CommonServiceProxy;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.RebindResult;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.rebind.rpc.ProxyCreator;

public class CommonServiceGenerator extends Generator {

    @Override
    public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {

        TypeOracle typeOracle = context.getTypeOracle();
        assert (typeOracle != null);

        JClassType remoteService = typeOracle.findType(typeName);
        if (remoteService == null) {
            logger.log(TreeLogger.ERROR, "Unable to find metadata for type '" + typeName + "'", null);
            throw new UnableToCompleteException();
        }

        if (remoteService.isInterface() == null) {
            logger.log(TreeLogger.ERROR, remoteService.getQualifiedSourceName() + " is not an interface", null);
            throw new UnableToCompleteException();
        }

        ProxyCreator proxyCreator = new ProxyCreator(remoteService) {

            @Override
            protected Class<? extends RemoteServiceProxy> getProxySupertype() {
                return CommonServiceProxy.class;
            }
        };

        TreeLogger proxyLogger = logger.branch(TreeLogger.DEBUG, "Generating client proxy for remote service interface '" + remoteService.getQualifiedSourceName() + "'", null);

        RebindResult result = proxyCreator.create(proxyLogger, context);
        return result.getResultTypeName();
    }
}
