package com.mongodb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.orm.engine.entry.Script;

/**
 * Utils for script language.
 * 
 * @author: xiangping_yu
 * @data : 2015-9-01
 * @since : 1.5
 */
public class ScriptUtils {

  private static final Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}");
  
  private ScriptUtils() {}
  
  public static String fillScriptParams(Script script, Object object) {
    String text = script.getText();
    
    Matcher matcher = pattern.matcher(text);
    while(matcher.find()) {
      text = text.replace(matcher.group(), matcher.group(1));  //FIXME
    }
    return text;
  }
  
  
  
}
