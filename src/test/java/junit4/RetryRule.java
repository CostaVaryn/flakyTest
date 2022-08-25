package junit4;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RetryRule implements TestRule {

    private int retryCount;

    public RetryRule(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable error = null;
                for (int i = 0; i<retryCount; i++) {
                    try {
                        base.evaluate();
                        return;
                    } catch (Throwable t) {
                        error = t;
                        System.err.println(description.getDisplayName() + " was launched " + (i+1) + " times");
                    }
                }
                System.err.println(description.getDisplayName() + " test failed after trying " + retryCount);
                throw error;
            }
        };
    }
}
