package org.paradigms;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class TestRunner {
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(selectPackage("org.paradigms"))
            .build();
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
        TestExecutionSummary summary = listener.getSummary();
        System.out.println("Tests found: " + summary.getTestsFoundCount() +
                           ", Succeeded: " + summary.getTestsSucceededCount() +
                           ", Failed: " + summary.getTestsFailedCount());
        if (summary.getTestsFailedCount() > 0) {
            summary.printFailuresTo(new java.io.PrintWriter(System.err));
            System.exit(1);
        }
    }
}
