package org.jsystem.rqm.tab;

import java.io.File;

import jsystem.framework.FrameworkOptions;
import jsystem.framework.JSystemProperties;
import jsystem.framework.TestProperties;
import jsystem.framework.scenario.Scenario;
import jsystem.framework.scenario.ScenariosManager;
import junit.framework.SystemTestCase4;

import org.junit.Test;

public class TestScenarioApi extends SystemTestCase4 {

	private String scenario1 = "a";
	private String scenario2 = "b";
	private String newScenarioName = "myScenario";

	private boolean scenarioAsTest = false;
	private boolean markAsKnownIssues = false;
	private boolean hideInHtml = false;
	private boolean addComment = false;
	private String comment = "My Comment";

	@Test
	@TestProperties(name = "Set various scenario attributes ", paramsInclude = { "scenarioAsTest", "markAsKnownIssues",
			"hideInHtml", "addComment", "comment", "scenario1" })
	public void changeScenarioAttributes() throws Exception {
		final File scenarioFolder = new File(JSystemProperties.getInstance().getPreference(
				FrameworkOptions.TESTS_CLASS_FOLDER));
		report.report(scenarioFolder.getAbsolutePath());
		ScenariosManager.getInstance().setScenariosDirectoryFiles(new File("C:\\dev\\jsystem-runner-6.1.01\\runner\\projects\\jsystemServices\\src\\main\\resources"));
		final Scenario scenario = new Scenario(new File("C:\\dev\\jsystem-runner-6.1.01\\runner\\projects\\jsystemServices\\src\\main\\resources\\scenarios"), "default");//ScenariosManager.getInstance().getScenario("scenarios/deafult");
		scenario.getTestFromRoot(0);
		scenario.setScenarioAsTest(scenarioAsTest);
		scenario.hideInHTML(hideInHtml);
		scenario.markAsKnownIssue(markAsKnownIssues);
		if (addComment) {
			scenario.setDocumentation(comment);
		}
		JSystemProperties.getInstance().setJsystemRunner(true);
		scenario.save();
		JSystemProperties.getInstance().setJsystemRunner(false);

	}

	@Test
	@TestProperties(name = "Add existing scenarios ${scenario1} and ${scenario2} to a new scenario ${newScenarioName}", paramsInclude = {
			"scenario1", "scenario2", "newScenarioName" })
	public void addExistingScenariosToNewOne() throws Exception {
		final File scenarioFolder = new File(JSystemProperties.getInstance().getPreference(
				FrameworkOptions.TESTS_CLASS_FOLDER));
		JSystemProperties.getInstance().setJsystemRunner(true);

		Scenario root = new Scenario(scenarioFolder, "scenarios/" + newScenarioName);
		Scenario s1 = ScenariosManager.getInstance().getScenario("scenarios/" + scenario1);
		Scenario s2 = ScenariosManager.getInstance().getScenario("scenarios/" + scenario2);
		root.addTest(s1);
		root.addTest(s2);
		root.save();

		Scenario subScenario = (Scenario) root.getTestFromRoot(0);

		subScenario.setScenarioAsTest(true);
		subScenario.hideInHTML(true);
		subScenario.markAsKnownIssue(true);
		subScenario.setDocumentation("My documentation");
		root.save();
		JSystemProperties.getInstance().setJsystemRunner(false);

		report.report("Refresh the runner to see the new scenario");

	}

	public String getScenario1() {
		return scenario1;
	}

	public void setScenario1(String scenario1) {
		this.scenario1 = scenario1;
	}

	public String getScenario2() {
		return scenario2;
	}

	public void setScenario2(String scenario2) {
		this.scenario2 = scenario2;
	}

	public String getNewScenarioName() {
		return newScenarioName;
	}

	public void setNewScenarioName(String newScenarioName) {
		this.newScenarioName = newScenarioName;
	}

	public boolean isScenarioAsTest() {
		return scenarioAsTest;
	}

	public void setScenarioAsTest(boolean markScenarioAsATest) {
		this.scenarioAsTest = markScenarioAsATest;
	}

	public boolean isMarkAsKnownIssues() {
		return markAsKnownIssues;
	}

	public void setMarkAsKnownIssues(boolean markAsKnownIssues) {
		this.markAsKnownIssues = markAsKnownIssues;
	}

	public boolean isHideInHtml() {
		return hideInHtml;
	}

	public void setHideInHtml(boolean markAsHiddenInHTML) {
		this.hideInHtml = markAsHiddenInHTML;
	}

	public boolean isAddComment() {
		return addComment;
	}

	public void setAddComment(boolean addComment) {
		this.addComment = addComment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
