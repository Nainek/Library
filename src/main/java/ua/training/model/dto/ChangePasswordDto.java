package ua.training.model.dto;

public class ChangePasswordDto {

	private Long userId;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

	public ChangePasswordDto() {

	}
	
	public static class Builder{
		
		private ChangePasswordDto changePasswordDto = new ChangePasswordDto();
		
		public Builder setUserId(long userId){
			changePasswordDto.userId = userId;
			return this;
		}
		
		public Builder setOldPassword(String oldPassword){
			changePasswordDto.oldPassword = oldPassword;
			return this;
		}
		
		public Builder setNewPassword(String newPassword){
			changePasswordDto.newPassword = newPassword;
			return this;
		}
		
		public Builder setconfirmPassword(String confirmPassword){
			changePasswordDto.confirmPassword = confirmPassword;
			return this;
		}
		
		
		public ChangePasswordDto build(){
			return changePasswordDto;
		}
		
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "ChangePasswordDto [userId=" + userId + ", oldPassword=" + oldPassword + ", newPassword=" + newPassword
				+ ", confirmPassword=" + confirmPassword + "]";
	}
	
	
	
}
