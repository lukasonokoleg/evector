package eu.itreegroup.spark.application.shared.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class DocumentPrivileges implements Serializable {

    private Long documentId;

    private DocumentType documentType;

    private Map<String, DocumentPrivilege> privileges = new HashMap<String, DocumentPrivilege>();

    public DocumentPrivileges() {

    }

    public DocumentPrivileges(Long documentId, DocumentType documentType) {
        this.documentId = documentId;
        this.documentType = documentType;
    }

    public DocumentPrivileges(Long documentId, DocumentType documentType, Map<String, DocumentPrivilege> privileges) {
        this.documentId = documentId;
        this.documentType = documentType;
        this.privileges = privileges;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Map<String, DocumentPrivilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Map<String, DocumentPrivilege> privileges) {
        this.privileges = privileges;
    }

    public void add(DocumentPrivilege privilege) {
        if (privilege == null) {
            throw new IllegalArgumentException("Input variable privilege has NULL value.");
        }
        if (privilege.getFunction() == null) {
            throw new IllegalArgumentException("Input variable privilege.function has NULL value.");
        }
        privileges.put(privilege.getFunction().getCode(), privilege);
    }

    public boolean hasFunction(DocumentFunction function) {
        validateDocumentFunctionInputVariable(function);
        DocumentPrivilege privilege = privileges.get(function.getCode());
        boolean retVal = privilege != null;
        return retVal;
    }

    private static void validateDocumentFunctionInputVariable(DocumentFunction function) {
        if (function == null) {
            throw new IllegalArgumentException("Input variable function has NULL value.");
        }
    }

    public boolean isFunctionEnabled(DocumentFunction function) {
        validateDocumentFunctionInputVariable(function);
        DocumentPrivilege privilege = privileges.get(function.getCode());
        boolean retVal = privilege != null && privilege.isEnabled();
        return retVal;
    }

    public boolean isFunctionDisabled(DocumentFunction function) {
        validateDocumentFunctionInputVariable(function);
        DocumentPrivilege privilege = privileges.get(function.getCode());
        boolean retVal = privilege != null && !privilege.isEnabled();
        return retVal;
    }

    @Override
    public String toString() {
        return "DocumentPrivileges [documentId=" + documentId + ", documentType=" + documentType + ", privileges=" + privileges + "]";
    }

}
