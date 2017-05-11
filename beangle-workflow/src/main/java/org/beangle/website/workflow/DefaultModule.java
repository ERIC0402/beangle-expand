package org.beangle.website.workflow;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.workflow.action.NodeConstraintAction;
import org.beangle.website.workflow.action.NodeDecisionAction;
import org.beangle.website.workflow.action.StepNodeAction;
import org.beangle.website.workflow.action.TaskAction;
import org.beangle.website.workflow.action.TaskNodeAction;
import org.beangle.website.workflow.action.TaskNodeConstraintAction;
import org.beangle.website.workflow.action.TaskNodeDecisionAction;
import org.beangle.website.workflow.action.WorkflowAction;
import org.beangle.website.workflow.service.impl.WorkflowServiceImpl;


/**
 * 
 * @author GOKU
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// TODO Auto-generated method stub
		bind(WorkflowAction.class,StepNodeAction.class,NodeConstraintAction.class,NodeDecisionAction.class,
				TaskAction.class,TaskNodeAction.class,TaskNodeConstraintAction.class,TaskNodeDecisionAction.class
				).in(Scope.PROTOTYPE);
		
		bind("workflowService", WorkflowServiceImpl.class);
		bind("ws", WorkflowServiceImpl.class);
	}

}
