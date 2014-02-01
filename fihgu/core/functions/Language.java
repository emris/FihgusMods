package fihgu.core.functions;

import fihgu.core.io.ConfigFile;

public class Language 
{
	
	private static String language;
	private static ConfigFile languageFile;
	
	/**
	 * get the translation of that line.
	 * will create config if the line doesn't exist in the language file.
	 */
	public static String translate(String line)
	{
		String result = line;
		if(languageFile != null) {
			boolean isNewText = !languageFile.containsKey(line);
			result = languageFile.get(line, line);

			if(isNewText)
				save();
		}
		return result;
	}
	
	/**
	 * save old language file, switch to new language.
	 * will create file if it doesn't exist.
	 * @param language
	 */
	public static void setLanguage(String language)
	{
		if(Language.language != null)
			save();
		
		Language.language = language;
		languageFile = new ConfigFile(language + ".txt","./fihgu/core/language/");
		languageFile.load();
	}
	
	public static String getLanguage()
	{
		return language;
	}
	
	public static void save()
	{
		languageFile.save();
	}
}
