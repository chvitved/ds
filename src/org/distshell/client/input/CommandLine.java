package org.distshell.client.input;

import org.distshell.client.ServiceFactory;

import org.distshell.shared.Command;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CommandLine extends Composite {

	private static CommandLineUiBinder uiBinder = GWT.create(CommandLineUiBinder.class);

	interface CommandLineUiBinder extends UiBinder<Widget, CommandLine> {}

	@UiField
	TextBox commandLine;
	
	public CommandLine() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("commandLine")
	public void onKeyPress(KeyPressEvent event) {
		if (event.getCharCode() == KeyCodes.KEY_ENTER) {
			ServiceFactory.getService().sendCommand(commandLine.getText(), new AsyncCallback<Command>() {
				@Override
				public void onSuccess(Command process) {
					 
				}
				@Override
				public void onFailure(Throwable caught) {
					//TODO
					caught.printStackTrace();
				}
			});
		}
	}
}
