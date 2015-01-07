package eu.itreegroup.spark.test.report;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.AssertionFailedError;
import junit.framework.JUnit4TestCaseFacade;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTestRunner;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitVersionHelper;
import org.apache.tools.ant.taskdefs.optional.junit.XMLConstants;
import org.apache.tools.ant.util.DOMElementWriter;
import org.apache.tools.ant.util.DateUtils;
import org.apache.tools.ant.util.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

//XMLJUnitResultFormatter
public class XmlFormatter implements JUnitResultFormatter, XMLConstants {

    private static final double ONE_SECOND = 1000.0;

    /** constant for unnnamed testsuites/cases */
    private static final String UNKNOWN = "unknown";

    private static DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (Exception exc) {
            throw new ExceptionInInitializerError(exc);
        }
    }

    /**
     * The XML document.
     */
    private Document doc;
    /**
     * The wrapper for the whole testsuite.
     */
    private Element rootElement;
    /**
     * Element for the current test.
     */
    private Hashtable testElements = new Hashtable();
    /**
     * tests that failed.
     */
    private Hashtable failedTests = new Hashtable();
    /**
     * Timing helper.
     */
    private Hashtable testStarts = new Hashtable();

    private boolean noRunableMethods;

    /**
     * Where to write the log to.
     */
    private OutputStream out;

    /** No arg constructor. */
    public XmlFormatter() {
    }

    /** {@inheritDoc}. */
    public void setOutput(OutputStream out) {
        this.out = out;
    }

    /** {@inheritDoc}. */
    public void setSystemOutput(String out) {
        formatOutput(SYSTEM_OUT, out);
    }

    /** {@inheritDoc}. */
    public void setSystemError(String out) {
        formatOutput(SYSTEM_ERR, out);
    }

    /**
     * The whole testsuite started.
     * @param suite the testsuite.
     */
    public void startTestSuite(JUnitTest suite) {
        noRunableMethods = false;
        doc = getDocumentBuilder().newDocument();
        rootElement = doc.createElement(TESTSUITE);
        String n = suite.getName();
        rootElement.setAttribute(ATTR_NAME, n == null ? UNKNOWN : n);

        // add the timestamp
        final String timestamp = DateUtils.format(new Date(), DateUtils.ISO8601_DATETIME_PATTERN);
        rootElement.setAttribute(TIMESTAMP, timestamp);
        // and the hostname.
        rootElement.setAttribute(HOSTNAME, getHostname());

        // Output properties
        Element propsElement = doc.createElement(PROPERTIES);
        rootElement.appendChild(propsElement);
        Properties props = suite.getProperties();
        if (props != null) {
            Enumeration e = props.propertyNames();
            while (e.hasMoreElements()) {
                String name = (String) e.nextElement();
                Element propElement = doc.createElement(PROPERTY);
                propElement.setAttribute(ATTR_NAME, name);
                propElement.setAttribute(ATTR_VALUE, props.getProperty(name));
                propsElement.appendChild(propElement);
            }
        }
    }

    /**
     * get the local hostname
     * @return the name of the local host, or "localhost" if we cannot work it out
     */
    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    /**
     * The whole testsuite ended.
     * @param suite the testsuite.
     * @throws BuildException on error.
     */
    public void endTestSuite(JUnitTest suite) throws BuildException {
        if (!noRunableMethods) {
            rootElement.setAttribute(ATTR_TESTS, "" + suite.runCount());
            rootElement.setAttribute(ATTR_FAILURES, "" + suite.failureCount());
            rootElement.setAttribute(ATTR_ERRORS, "" + suite.errorCount());
            rootElement.setAttribute(ATTR_SKIPPED, "0");
        } else {
            rootElement.setAttribute(ATTR_TESTS, "0");
            rootElement.setAttribute(ATTR_FAILURES, "0");
            rootElement.setAttribute(ATTR_ERRORS, "0");
            rootElement.setAttribute(ATTR_SKIPPED, "0");
        }
        rootElement.setAttribute(ATTR_TIME, "" + (suite.getRunTime() / ONE_SECOND));
        if (out != null) {
            Writer wri = null;
            try {
                wri = new BufferedWriter(new OutputStreamWriter(out, "UTF8"));
                wri.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
                (new DOMElementWriter()).write(rootElement, wri, 0, "  ");
                wri.flush();
            } catch (IOException exc) {
                throw new BuildException("Unable to write log file", exc);
            } finally {
                if (out != System.out && out != System.err) {
                    FileUtils.close(wri);
                }
            }
        }
    }

    /**
     * Interface TestListener.
     *
     * <p>A new Test is started.
     * @param t the test.
     */
    public void startTest(Test t) {
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Start test " + t);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
        testStarts.put(t, new Long(System.currentTimeMillis()));
    }

    /**
     * Interface TestListener.
     *
     * <p>A Test is finished.
     * @param test the test.
     */
    public void endTest(Test test) {
        // Fix for bug #5637 - if a junit.extensions.TestSetup is
        // used and throws an exception during setUp then startTest
        // would never have been called
        if (!testStarts.containsKey(test)) {
            startTest(test);
        }

        Element currentTestElement = null;
        if (!failedTests.containsKey(test)) {
            currentTestElement = doc.createElement(TESTCASE);
            String n = JUnitVersionHelper.getTestCaseName(test);
            currentTestElement.setAttribute(ATTR_NAME, n == null ? UNKNOWN : n);
            // a TestSuite can contain Tests from multiple classes,
            // even tests with the same name - disambiguate them.
            currentTestElement.setAttribute(ATTR_CLASSNAME, JUnitVersionHelper.getTestCaseClassName(test));
            addDescripionAttribute(rootElement, currentTestElement, test);
            addAuthorAttribute(rootElement, currentTestElement, test);
            rootElement.appendChild(currentTestElement);
            testElements.put(test, currentTestElement);
        } else {
            currentTestElement = (Element) testElements.get(test);
        }

        Long l = (Long) testStarts.get(test);
        currentTestElement.setAttribute(ATTR_TIME, "" + ((System.currentTimeMillis() - l.longValue()) / ONE_SECOND));
    }

    protected void addDescripionAttribute(Element rootElement, Element currentTestElement, Test test) {
        addAttribute(rootElement, currentTestElement, test, "description", TestDescription.class);
    }

    protected void addAuthorAttribute(Element rootElement, Element currentTestElement, Test test) {
        addAttribute(rootElement, currentTestElement, test, "author", TestAuthor.class);
    }

    private String value(Annotation a) {
        if (a instanceof TestDescription) {
            return ((TestDescription) a).value();
        } else if (a instanceof TestAuthor) {
            return ((TestAuthor) a).value();
        }
        throw new IllegalArgumentException("unknown annotation " + a.getClass());
    }

    private <A extends Annotation> void addAttribute(Element rootElement, Element currentTestElement, Test test, String attributeName, Class<A> annotation) {
        if (test instanceof JUnit4TestCaseFacade) {
            String n = JUnitVersionHelper.getTestCaseName(test);
            JUnit4TestCaseFacade facade = (JUnit4TestCaseFacade) test;
            A a = facade.getDescription().getAnnotation(annotation);
            if (null != a) {
                currentTestElement.setAttribute(attributeName, newLines(value(a)));
            } else {
                String d = TestDescriptionRegistry.getAttribute(n, attributeName);
                if (null != n) {
                    currentTestElement.setAttribute(attributeName, newLines(d));
                }
            }
            if (rootElement.getAttribute(attributeName) == null || rootElement.getAttribute(attributeName).isEmpty()) {
                String className = facade.getDescription().getClassName();
                try {
                    a = Class.forName(className).getAnnotation(annotation);
                    if (null != a) {
                        rootElement.setAttribute(attributeName, newLines(value(a)));
                    }
                } catch (Throwable e) {
                    rootElement.setAttribute(attributeName, "cannot get " + attributeName + " - " + e.toString());
                }
            }
        }
    }

    private String newLines(String s) {
        if (null != s) {
            return s.replaceAll("\\n", "~~");
        } else {
            return null;
        }
    }

    /**
     * Interface TestListener for JUnit &lt;= 3.4.
     *
     * <p>A Test failed.
     * @param test the test.
     * @param t the exception.
     */
    public void addFailure(Test test, Throwable t) {
        formatError(FAILURE, test, t);
    }

    /**
     * Interface TestListener for JUnit &gt; 3.4.
     *
     * <p>A Test failed.
     * @param test the test.
     * @param t the assertion.
     */
    public void addFailure(Test test, AssertionFailedError t) {
        addFailure(test, (Throwable) t);
    }

    /**
     * Interface TestListener.
     *
     * <p>An error occurred while running the test.
     * @param test the test.
     * @param t the error.
     */
    public void addError(Test test, Throwable t) {
        if (null != t && "No runnable methods".equals(t.getMessage())) {
            noRunableMethods = true;
        }
        formatError(ERROR, test, t);
    }

    private void formatError(String type, Test test, Throwable t) {
        if (test != null) {
            endTest(test);
            failedTests.put(test, test);
        }

        Element nested = doc.createElement(type);
        Element currentTest = null;
        if (test != null) {
            currentTest = (Element) testElements.get(test);
        } else {
            currentTest = rootElement;
        }

        currentTest.appendChild(nested);

        String message = t.getMessage();
        if (message != null && message.length() > 0) {
            nested.setAttribute(ATTR_MESSAGE, t.getMessage());
        }
        nested.setAttribute(ATTR_TYPE, t.getClass().getName());

        String strace = JUnitTestRunner.getFilteredTrace(t);
        Text trace = doc.createTextNode(strace);
        nested.appendChild(trace);
    }

    private void formatOutput(String type, String output) {
        Element nested = doc.createElement(type);
        rootElement.appendChild(nested);
        nested.appendChild(doc.createCDATASection(output));
    }

}
