package com.river.app.util;

/**
 * Created by Administrator on 2017/6/5.
 */

public class StringUtils {
  /**
   * 处理Html文件
   */
  /**
   * 通过递归删除html标签
   * @param content - 包含HTML标签的内容
   * @author Jack, 2014-05-15.
   * @return 不带HTML标签的文本内容
   */
  public static String removeHtmlTag(String content) {
   // Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>");
   //// Pattern p1 = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
   // Matcher m = p.matcher(content);
   // if (m.find()) {
   //
   //   content = removeHtmlTag(content);
   // }

    return content;
  }
}
