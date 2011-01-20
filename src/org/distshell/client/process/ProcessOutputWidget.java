package org.distshell.client.process;

import org.distshell.shared.CommandReply;
import org.distshell.shared.CommandReply.Error;
import org.distshell.shared.CommandReply.Finished;
import org.distshell.shared.CommandReply.Output;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ProcessOutputWidget extends Composite {

	private static ProcessUiBinder uiBinder = GWT.create(ProcessUiBinder.class);

	interface ProcessUiBinder extends UiBinder<Widget, ProcessOutputWidget> {}

	@UiField DivElement output;
	
	private StringBuilder outputBuilder = new StringBuilder();
	
	public ProcessOutputWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void add(CommandReply pr) {
		if (pr instanceof Finished) {
			output.addClassName("finished");
		} else if (pr instanceof Error) {
			Error error = (Error) pr;
			output.addClassName("error");
			outputBuilder.append(error.getValue());
		} else if (pr instanceof Output) {
			Output o = (Output) pr; 
			String string = o.getValue().replace("\n", "\n<br/>");
			System.out.println(string);
			outputBuilder.append(string);
		}
		output.setInnerHTML(outputBuilder.toString());
	}
}
