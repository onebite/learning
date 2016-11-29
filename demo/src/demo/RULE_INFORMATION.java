package demo;

public class RULE_INFORMATION {
	private String rule_id;
	private String ntp_seconds;
	private String authorization_state;
	private String failure_code;
	private String one_time_redirect;
	//TODO:QOS column
	
	public String getAuthorization_state() {
		return authorization_state;
	}

	public void setAuthorization_state(String authorization_state) {
		this.authorization_state = authorization_state;
	}

	public String getFailure_code() {
		return failure_code;
	}

	public void setFailure_code(String failure_code) {
		this.failure_code = failure_code;
	}

	public String getOne_time_redirect() {
		return one_time_redirect;
	}

	public void setOne_time_redirect(String one_time_redirect) {
		this.one_time_redirect = one_time_redirect;
	}

	public String getRule_id(){
		return rule_id;
	}
	
	public void setRule_id(String rule_id){
		this.rule_id = rule_id;
	}

	public String getNtp_seconds() {
		return ntp_seconds;
	}

	public void setNtp_seconds(String ntp_seconds) {
		this.ntp_seconds = ntp_seconds;
	}
	
}
