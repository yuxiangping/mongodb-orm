package org.yy.mongodb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yy.mongodb.orm.engine.entry.Script;

/**
 * Utils for script language.
 * @author yy
 */
public class ScriptUtils {

  private static final Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}");
  
  private ScriptUtils() {}
  
  public static String fillScriptParams(Script script, Object object) {
    String text = script.getText();
    
    Matcher matcher = pattern.matcher(text);
    while(matcher.find()) {
      text = text.replace(matcher.group(), script.getValue(matcher.group(1), object));
    }
    return text;
  }
  
}
