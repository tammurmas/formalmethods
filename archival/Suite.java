package formalmethods.archival;

import junit.framework.Test;
import junit.framework.TestSuite;

public class Suite {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(TC1.class);
    suite.addTestSuite(TC2.class);
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
