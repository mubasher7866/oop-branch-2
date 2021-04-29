package vgc_Mubasher_Zeb_Khan_21694;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaWindowsFormValidations {

	public static boolean validateOnlyTextBoxLetters(String controlName, String tbText, int minRange, int maxRange) {
		if (tbText.equals("")) {
			JavaWindowsFormUserInformers.showMsgWithJPane("Please enter " + controlName.toLowerCase() + ".");
			return false;
		}  else if (tbText.trim().length() < minRange) {
			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " is too small.");
			return false;
		} else if (tbText.trim().length() > maxRange) {
			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " is too long.");
			return false;
		} 
		else if (tbText.chars().allMatch(Character::isDigit)) {

			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " should not contain digits.");
			return false;

		}
		else
			return true;
	}

	public static boolean validateOnlyTextBoxDigits(String controlName, String tbText, int minRange, int maxRange) {
		if (tbText.equals("")) {
			JavaWindowsFormUserInformers.showMsgWithJPane("Please enter " + controlName.toLowerCase() + ".");
			return false;
		} else if (tbText.trim().length() < minRange) {
			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " is too small.");
			return false;
		} else if (tbText.trim().length() > maxRange) {
			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " is too long.");
			return false;
		}
		else if (tbText.chars().allMatch(Character::isLetter)) {

			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " should not contain letters.");
			return false;

		}
		else
			return true;
	}
	public static boolean validateOnlyEmail(String controlName, String tbText, int minRange, int maxRange) {

		if (tbText == "" || tbText == null) {
			JavaWindowsFormUserInformers.showMsgWithJPane("Please enter " + controlName.toLowerCase() + ".");
			return false;
		} else if (!tbText.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {

			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " is not correct.");
			return false;

		} else if (tbText.trim().length() < minRange) {
			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " is too small.");
			return false;
		} else if (tbText.trim().length() > maxRange) {
			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " is too long.");
			return false;
		} else
			return true;
	}

	public static boolean validateOnlyLength(String controlName, String tbText, int minRange, int maxRange) {
		if (tbText.equals("")) {
			JavaWindowsFormUserInformers.showMsgWithJPane("Please enter " + controlName.toLowerCase() + ".");
			return false;
		} else if (tbText.trim().length() < minRange) {
			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " is too small.");
			return false;
		} else if (tbText.trim().length() > maxRange) {
			JavaWindowsFormUserInformers.showMsgWithJPane(controlName + " is too long.");
			return false;
		} else
			return true;
	}

	public static boolean validateOnly12HoursFormatTime(String controlName,String tbText)
	{
		if (tbText.equals("")) {
			JavaWindowsFormUserInformers.showMsgWithJPane("Please enter " + controlName.toLowerCase() + ".");
			return false;
		}
		else{
		        String pattern = "(1[012]|[1-9]):" + "[0-5][0-9](\\s)" + "?(?i)(am|pm)";
		 
		        Pattern compiledPattern = Pattern.compile(pattern);
		        
		        Matcher m = compiledPattern.matcher(tbText);
		 
		        // Return according to the match, if the time match then true otherwise false
		        return m.matches();
	        }
	}
	public static boolean validateComboBox(String controlName, int selectedIndex) {

		if (selectedIndex<0) {
			JavaWindowsFormUserInformers.showMsgWithJPane("Please select a " + controlName.toLowerCase() + ".");
			return false;
		} else
			return true;
	}

	public static boolean compareTwoStrings(String str1, String str2, String errorMsg) {

		if (!str1.equals(str2)) {
			JavaWindowsFormUserInformers.showMsgWithJPane(errorMsg);
			return false;
		} else
			return true;
	}
	
}
