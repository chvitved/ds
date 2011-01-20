package org.distshell.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class CommandReply implements Serializable{
	private Machine machine;
	
	//gwt
	public CommandReply() {}
	
	public CommandReply(Machine m) {
		this.machine = m;
	}
	
	public Machine getMachine() {
		return machine;
	}
	
	public static class CommandReplyValue<T> extends CommandReply{

		private T value;

		//gwt
		public CommandReplyValue() {}
		
		public CommandReplyValue(Machine m, T value) {
			super(m);
			this.value = value;
		}
		
		public T getValue() {
			return value;
		}
	}

	
	public static class Output extends CommandReplyValue<String> {
		
		//gwt
		public Output() {}
		
		public Output(Machine m, String value) {
			super(m, value);
		}
	}
	
	public static class Error extends CommandReplyValue<String> {
		
		//gwt
		public Error() {}
		
		public Error(Machine m, String value) {
			super(m, value);
		}
	}
	
	public static class Finished extends CommandReply {
		
		//gwt
		public Finished() {}
		
		public Finished(Machine m) {
			super(m);
		}
	}

}


