/*
 * SonarQube XML Plugin
 * Copyright (C) 2010-2021 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.xml.checks.tosan.rule;

import org.sonar.check.Rule;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;

/**
 * RSPEC-1134
 */
@Rule(key = LogstashPatternCheck.RULE_KEY)
public class LogstashPatternCheck extends SonarXmlCheck {

  public static final String RULE_KEY = "T1134";
//  private static final String PATTERN = "FIXME";
//  private static final String MESSAGE = "Take the required action to fix the issue indicated by this \"FIXME\" comment.";

//  public FixmeCommentCheckTosan() {
//    super(PATTERN, MESSAGE);
//  }

  @Override
    public void scanFile(XmlFile file) {
//    if (file.getDocument().getDocumentURI().contains("logback.xml")) {
    if (inputFile().absolutePath().contains("logback")) {
        ComparatorLogback comparatorLogback=new ComparatorLogback();
        String message= null;

        try {
          comparatorLogback.comparePattern(inputFile().absolutePath());
        } catch (Exception e) {

          reportIssue(file.getDocument(), e.getMessage());
  //        reportIssue(file.getDocument(), "logstash appender dont exist or logstash pattern not contain {app_name , app_version , rwa_log} "+message );

  //        e.printStackTrace();
        }
//      if(!message.isEmpty()) {
//          reportIssue(file.getDocument(), "logstash appender dont exist or logstash pattern not contain {app_name , app_version , rwa_log} "+message );
//        }
    }

  }

}
